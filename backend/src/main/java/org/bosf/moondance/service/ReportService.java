package org.bosf.moondance.service;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.InteractionDto;
import org.bosf.moondance.entity.Note;
import org.bosf.moondance.entity.Report;
import org.bosf.moondance.entity.User;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.NoteRepository;
import org.bosf.moondance.repository.ReportRepository;
import org.bosf.moondance.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, NoteRepository noteRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public InteractionDto.ReportResponse createReport(Long noteId, Long reporterId, InteractionDto.ReportRequest request) {
        if (reportRepository.existsByNoteIdAndReporterId(noteId, reporterId)) {
            throw new ApiException.ConflictException("You have already reported this note");
        }

        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        User reporter = userRepository.findActiveById(reporterId)
                .orElseThrow(() -> new ApiException.NotFoundException("User", reporterId));

        Report report = Report.builder()
                .note(note)
                .reporter(reporter)
                .reason(request.getReason())
                .description(request.getDescription())
                .build();

        report = reportRepository.save(report);

        log.info("Report created: note={}, reporter={}, reason={}", noteId, reporterId, request.getReason());

        return InteractionDto.ReportResponse.fromEntity(report);
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<InteractionDto.ReportResponse> getPendingReports(Long schoolId, Pageable pageable) {
        Page<Report> page = reportRepository.findBySchoolIdAndStatus(schoolId, Report.ReportStatus.PENDING, pageable);

        return ApiResponse.PageResponse.from(page, InteractionDto.ReportResponse::fromEntity);
    }

    @Transactional
    public InteractionDto.ReportResponse reviewReport(Long reportId, Long reviewerId, InteractionDto.ReportReviewRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ApiException.NotFoundException("Report", reportId));

        User reviewer = userRepository.findActiveById(reviewerId)
                .orElseThrow(() -> new ApiException.NotFoundException("User", reviewerId));

        report.setStatus(request.getStatus());
        report.setModeratorNotes(request.getModeratorNotes());
        report.setReviewedBy(reviewer);
        report.setReviewedAt(LocalDateTime.now());

        if (request.getStatus() == Report.ReportStatus.RESOLVED) {
            Note note = report.getNote();

            note.setDeletedAt(LocalDateTime.now());
            noteRepository.save(note);

            log.info("Note removed due to report: noteId={}, reportId={}", note.getId(), reportId);
        }

        report = reportRepository.save(report);
        
        log.info("Report reviewed: reportId={}, status={}", reportId, request.getStatus());

        return InteractionDto.ReportResponse.fromEntity(report);
    }
}
