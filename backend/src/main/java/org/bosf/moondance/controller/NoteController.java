package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.NoteDto;
import org.bosf.moondance.entity.Note;
import org.bosf.moondance.entity.User.UserRole;
import org.bosf.moondance.security.UserPrincipal;
import org.bosf.moondance.service.NoteService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notes")
@Tag(name = "Notes", description = "Note management endpoints")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a new note")
    public ResponseEntity<ApiResponse<NoteDto>> uploadNote(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestPart("data") NoteDto.CreateRequest request,
        @RequestPart("file") MultipartFile file
    ) throws IOException {
        NoteDto note = noteService.uploadNote(principal.getId(), request, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Note uploaded successfully", note));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by ID")
    public ResponseEntity<ApiResponse<NoteDto>> getNoteById(@PathVariable Long id) {
        NoteDto note = noteService.getNoteByIdWithView(id);

        return ResponseEntity.ok(ApiResponse.success(note));
    }

    @GetMapping("/course-session/{courseSessionId}")
    @Operation(summary = "Get notes by course session")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> getNotesByCourseSession(
        @PathVariable Long courseSessionId,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.getNotesByCourseSession(courseSessionId, pageable)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search notes")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> searchNotes(
        @RequestParam Long schoolId,
        @RequestParam String query,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.searchNotes(schoolId, query, pageable)));
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending notes")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> getTrendingNotes(
        @RequestParam Long schoolId,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.getTrendingNotes(schoolId, pageable)));
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent notes")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> getRecentNotes(
        @RequestParam Long schoolId,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.getRecentNotes(schoolId, pageable)));
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get notes by type")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> getNotesByType(
        @RequestParam Long schoolId,
        @RequestParam Note.NoteType type,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.getNotesByType(schoolId, type, pageable)));
    }

    @GetMapping("/my-notes")
    @Operation(summary = "Get current user's notes")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<NoteDto>>> getMyNotes(
        @AuthenticationPrincipal UserPrincipal principal,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(noteService.getUserNotes(principal.getId(), pageable)));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a note")
    public ResponseEntity<ApiResponse<NoteDto>> updateNote(
        @PathVariable Long id,
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody NoteDto.UpdateRequest request
    ) {
        NoteDto note = noteService.updateNote(id, principal.getId(), request);

        return ResponseEntity.ok(ApiResponse.success("Note updated successfully", note));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a note")
    public ResponseEntity<ApiResponse<Void>> deleteNote(
        @PathVariable Long id,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        boolean isAdmin = principal.getRole() == UserRole.ADMIN;

        noteService.deleteNote(id, principal.getId(), isAdmin);

        return ResponseEntity.ok(ApiResponse.success("Note deleted successfully", null));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Get download URL for a note")
    public ResponseEntity<ApiResponse<String>> getDownloadUrl(@PathVariable Long id) {
        String url = noteService.getDownloadUrl(id);

        return ResponseEntity.ok(ApiResponse.success(url));
    }

    @GetMapping("/{id}/view")
    @Operation(summary = "Get view URL for a note")
    public ResponseEntity<ApiResponse<String>> getViewUrl(@PathVariable Long id) {
        String url = noteService.getViewUrl(id);
        
        return ResponseEntity.ok(ApiResponse.success(url));
    }
}
