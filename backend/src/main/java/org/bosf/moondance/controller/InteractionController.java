package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.InteractionDto;
import org.bosf.moondance.security.UserPrincipal;
import org.bosf.moondance.service.ReportService;
import org.bosf.moondance.service.VoteService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Interactions", description = "Voting and reporting endpoints")
public class InteractionController {

    private final VoteService voteService;
    private final ReportService reportService;

    public InteractionController(VoteService voteService, ReportService reportService) {
        this.voteService = voteService;
        this.reportService = reportService;
    }

    @PostMapping("/notes/{noteId}/vote")
    @Operation(summary = "Vote on a note")
    public ResponseEntity<ApiResponse<InteractionDto.VoteResponse>> vote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.VoteRequest request
    ) {
        InteractionDto.VoteResponse response = voteService.vote(noteId, principal.getId(), request);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/notes/{noteId}/vote")
    @Operation(summary = "Remove vote from a note")
    public ResponseEntity<ApiResponse<Void>> removeVote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        voteService.removeVote(noteId, principal.getId());

        return ResponseEntity.ok(ApiResponse.success("Vote removed", null));
    }

    @GetMapping("/notes/{noteId}/my-vote")
    @Operation(summary = "Get current user's vote on a note")
    public ResponseEntity<ApiResponse<InteractionDto.VoteResponse>> getMyVote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        InteractionDto.VoteResponse response = voteService.getUserVote(noteId, principal.getId());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/notes/{noteId}/report")
    @Operation(summary = "Report a note")
    public ResponseEntity<ApiResponse<InteractionDto.ReportResponse>> reportNote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.ReportRequest request
    ) {
        InteractionDto.ReportResponse response = reportService.createReport(noteId, principal.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Report submitted", response));
    }

    @GetMapping("/reports/pending")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Get pending reports for moderation")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<InteractionDto.ReportResponse>>> getPendingReports(
        @RequestParam Long schoolId,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getPendingReports(schoolId, pageable)));
    }

    @PatchMapping("/reports/{reportId}/review")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Operation(summary = "Review a report")
    public ResponseEntity<ApiResponse<InteractionDto.ReportResponse>> reviewReport(
        @PathVariable Long reportId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.ReportReviewRequest request
    ) {
        InteractionDto.ReportResponse response = reportService.reviewReport(reportId, principal.getId(), request);
        
        return ResponseEntity.ok(ApiResponse.success("Report reviewed", response));
    }
}
