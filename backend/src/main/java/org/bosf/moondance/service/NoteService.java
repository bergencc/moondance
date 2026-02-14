package org.bosf.moondance.service;

import org.bosf.moondance.dto.NoteDto;
import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.entity.Note;
import org.bosf.moondance.entity.CourseSession;
import org.bosf.moondance.entity.Tag;
import org.bosf.moondance.entity.User;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.CourseSessionRepository;
import org.bosf.moondance.repository.NoteRepository;
import org.bosf.moondance.repository.TagRepository;
import org.bosf.moondance.repository.UserRepository;
import org.bosf.moondance.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NoteService {

    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepository noteRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final VoteRepository voteRepository;
    private final StorageService storageService;
    private final TextExtractionService textExtractionService;

    public NoteService(
        NoteRepository noteRepository, 
        CourseSessionRepository courseSessionRepository, 
        UserRepository userRepository, 
        TagRepository tagRepository, 
        VoteRepository voteRepository, 
        StorageService storageService, 
        TextExtractionService textExtractionService
    ) {
        this.noteRepository = noteRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.voteRepository = voteRepository;
        this.storageService = storageService;
        this.textExtractionService = textExtractionService;
    }

    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/gif",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    );

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

    @Transactional
    public NoteDto uploadNote(Long userId, NoteDto.CreateRequest request, MultipartFile file) throws IOException {
        validateFile(file);

        User uploader = userRepository.findActiveById(userId)
                .orElseThrow(() -> new ApiException.NotFoundException("User", userId));

        CourseSession courseSession = courseSessionRepository.findById(request.getCourseSessionId())
                .orElseThrow(() -> new ApiException.NotFoundException("CourseSession", request.getCourseSessionId()));

        StorageService.UploadResult uploadResult = storageService.uploadFile(file, "notes");

        Set<Tag> tags = getOrCreateTags(request.getTags());

        Note note = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .fileKey(uploadResult.key())
                .fileHash(uploadResult.hash())
                .fileSize(uploadResult.size())
                .mimeType(uploadResult.contentType())
                .originalFileName(file.getOriginalFilename())
                .weekLabel(request.getWeekLabel())
                .courseSession(courseSession)
                .uploader(uploader)
                .tags(tags)
                .processingStatus(Note.ProcessingStatus.PENDING)
                .build();

        note = noteRepository.save(note);

        log.info("Note uploaded: {} by user {}", note.getId(), userId);

        textExtractionService.extractTextAsync(note.getId());

        return NoteDto.fromEntity(note);
    }

    @Transactional(readOnly = true)
    public NoteDto getNoteById(Long id) {
        Note note = noteRepository.findActiveById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", id));

        return NoteDto.fromEntity(note);
    }

    @Transactional
    public NoteDto getNoteByIdWithView(Long id) {
        Note note = noteRepository.findActiveById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", id));

        noteRepository.incrementViewCount(id);

        return NoteDto.fromEntity(note);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> getNotesByCourseSession(Long courseSessionId, Pageable pageable) {
        Page<Note> page = noteRepository.findByCourseSessionId(courseSessionId, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> searchNotes(Long schoolId, String query, Pageable pageable) {
        Page<Note> page = noteRepository.searchBySchoolId(schoolId, query, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> getTrendingNotes(Long schoolId, Pageable pageable) {
        Page<Note> page = noteRepository.findTrendingBySchoolId(schoolId, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> getRecentNotes(Long schoolId, Pageable pageable) {
        Page<Note> page = noteRepository.findRecentBySchoolId(schoolId, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> getNotesByType(Long schoolId, Note.NoteType type, Pageable pageable) {
        Page<Note> page = noteRepository.findByTypeAndSchoolId(type, schoolId, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<NoteDto> getUserNotes(Long userId, Pageable pageable) {
        Page<Note> page = noteRepository.findByUploaderId(userId, pageable);

        return ApiResponse.PageResponse.from(page, NoteDto::fromEntity);
    }

    @Transactional
    public NoteDto updateNote(Long noteId, Long userId, NoteDto.UpdateRequest request) {
        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        if (!note.getUploader().getId().equals(userId)) {
            throw new ApiException.ForbiddenException("You can only update your own notes");
        }

        if (request.getTitle() != null) {
            note.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            note.setDescription(request.getDescription());
        }

        if (request.getType() != null) {
            note.setType(request.getType());
        }

        if (request.getWeekLabel() != null) {
            note.setWeekLabel(request.getWeekLabel());
        }

        if (request.getTags() != null) {
            note.setTags(getOrCreateTags(request.getTags()));
        }

        note = noteRepository.save(note);

        return NoteDto.fromEntity(note);
    }

    @Transactional
    public void deleteNote(Long noteId, Long userId, boolean isAdmin) {
        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        if (!isAdmin && !note.getUploader().getId().equals(userId)) {
            throw new ApiException.ForbiddenException("You can only delete your own notes");
        }

        note.setDeletedAt(LocalDateTime.now());

        noteRepository.save(note);

        log.info("Note deleted: {} by user {}", noteId, userId);
    }

    @Transactional
    public String getDownloadUrl(Long noteId) {
        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        noteRepository.incrementDownloadCount(noteId);

        return storageService.generateDownloadUrl(note.getFileKey(), note.getOriginalFileName());
    }

    @Transactional
    public String getViewUrl(Long noteId) {
        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        return storageService.generatePresignedUrl(note.getFileKey());
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ApiException.BadRequestException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ApiException.BadRequestException("File size exceeds maximum allowed size of 50MB");
        }

        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new ApiException.BadRequestException("File type not allowed. Allowed types: PDF, images, Word, PowerPoint");
        }
    }

    private Set<Tag> getOrCreateTags(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }

        Set<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        Set<String> existingNames = new HashSet<>();

        existingTags.forEach(t -> existingNames.add(t.getName()));

        Set<Tag> result = new HashSet<>(existingTags);

        for (String name : tagNames) {
            if (!existingNames.contains(name)) {
                Tag newTag = Tag.builder().name(name).build();

                result.add(tagRepository.save(newTag));
            }
        }

        return result;
    }
}
