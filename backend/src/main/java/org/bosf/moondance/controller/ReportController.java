package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.InteractionDto;
import org.bosf.moondance.security.UserPrincipal;
import org.bosf.moondance.service.ReportService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Reports", description = "Content reporting endpoints")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/notes/{noteId}/reports")
    @Operation(summary = "Report a note")
    public ResponseEntity<ApiResponse<InteractionDto.ReportResponse>> createReport(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.ReportRequest request
    ) {
        InteractionDto.ReportResponse response = reportService.createReport(noteId, principal.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Report submitted", response));
    }

    @GetMapping("/admin/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Operation(summary = "Get pending reports (admin/moderator only)")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<InteractionDto.ReportResponse>>> getPendingReports(
        @AuthenticationPrincipal UserPrincipal principal,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getPendingReports(principal.getSchoolId(), pageable)));
    }

    @PatchMapping("/admin/reports/{reportId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Operation(summary = "Review a report (admin/moderator only)")
    public ResponseEntity<ApiResponse<InteractionDto.ReportResponse>> reviewReport(
        @PathVariable Long reportId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.ReportReviewRequest request
    ) {
        InteractionDto.ReportResponse response = reportService.reviewReport(reportId, principal.getId(), request);
        
        return ResponseEntity.ok(ApiResponse.success("Report reviewed", response));
    }
}
