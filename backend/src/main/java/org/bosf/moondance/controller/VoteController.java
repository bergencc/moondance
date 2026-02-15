package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.InteractionDto;
import org.bosf.moondance.security.UserPrincipal;
import org.bosf.moondance.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes/{noteId}/votes")
@Tag(name = "Votes", description = "Note voting endpoints")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @Operation(summary = "Vote on a note")
    public ResponseEntity<ApiResponse<InteractionDto.VoteResponse>> vote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody InteractionDto.VoteRequest request
    ) {
        InteractionDto.VoteResponse response = voteService.vote(noteId, principal.getId(), request);

        return ResponseEntity.ok(ApiResponse.success("Vote recorded", response));
    }

    @DeleteMapping
    @Operation(summary = "Remove vote from a note")
    public ResponseEntity<ApiResponse<Void>> removeVote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        voteService.removeVote(noteId, principal.getId());

        return ResponseEntity.ok(ApiResponse.success("Vote removed", null));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user's vote on a note")
    public ResponseEntity<ApiResponse<InteractionDto.VoteResponse>> getMyVote(
        @PathVariable Long noteId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        InteractionDto.VoteResponse response = voteService.getUserVote(noteId, principal.getId());
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
