package org.bosf.moondance.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.bosf.moondance.entity.Note;
import org.bosf.moondance.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Service
public class TextExtractionService {

    private static final Logger log = LoggerFactory.getLogger(TextExtractionService.class);

    private final NoteRepository noteRepository;
    private final StorageService storageService;
    
    private final Tika tika = new Tika();

    public TextExtractionService(NoteRepository noteRepository, StorageService storageService) {
        this.noteRepository = noteRepository;
        this.storageService = storageService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        List<Note> pendingNotes = noteRepository.findByProcessingStatus(Note.ProcessingStatus.PROCESSING);

        for (Note note : pendingNotes) {
            log.info("Re-queuing note {} for text extraction", note.getId());

            note.setProcessingStatus(Note.ProcessingStatus.PENDING);
            noteRepository.save(note);

            extractTextAsync(note.getId());
        }

        List<Note> newNotes = noteRepository.findByProcessingStatus(Note.ProcessingStatus.PENDING);

        for (Note note : newNotes) {
            extractTextAsync(note.getId());
        }
    }

    @Async("textExtractionExecutor")
    @Transactional
    public void extractTextAsync(Long noteId) {
        log.info("Starting text extraction for note: {}", noteId);

        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            log.warn("Note not found for text extraction: {}", noteId);

            return;
        }

        try {
            note.setProcessingStatus(Note.ProcessingStatus.PROCESSING);
            noteRepository.save(note);

            String content = extractText(note.getFileKey(), note.getMimeType());

            if (content != null && content.length() > 100000) {
                content = content.substring(0, 100000);
            }

            note.setExtractedContent(content);
            note.setProcessingStatus(Note.ProcessingStatus.READY);

            noteRepository.save(note);

            log.info("Text extraction completed for note: {}", noteId);

        } catch (Exception e) {
            log.error("Text extraction failed for note: {}", noteId, e);

            note.setProcessingStatus(Note.ProcessingStatus.FAILED);

            noteRepository.save(note);
        }
    }

    private String extractText(String fileKey, String mimeType) {
        if (!isExtractable(mimeType)) {
            return null;
        }

        try (InputStream inputStream = storageService.downloadFile(fileKey)) {
            String content = tika.parseToString(inputStream);

            return content.trim();
        } catch (TikaException | java.io.IOException e) {
            log.error("Failed to extract text from file: {}", fileKey, e);

            return null;
        }
    }

    private boolean isExtractable(String mimeType) {
        if (mimeType == null) return false;
        
        return mimeType.equals("application/pdf")
                || mimeType.startsWith("text/")
                || mimeType.equals("application/msword")
                || mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || mimeType.equals("application/vnd.ms-powerpoint")
                || mimeType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }
}
