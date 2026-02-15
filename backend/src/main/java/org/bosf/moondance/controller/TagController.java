package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Tag endpoints")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    @Operation(summary = "Get all tags")
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> tags = tagRepository.findAll().stream()
            .map(t -> new TagResponse(t.getId(), t.getName(), t.getColor()))
            .toList();

        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    @GetMapping("/search")
    @Operation(summary = "Search tags by name")
    public ResponseEntity<ApiResponse<List<TagResponse>>> searchTags(@RequestParam String query) {
        List<TagResponse> tags = tagRepository.searchByName(query).stream()
            .map(t -> new TagResponse(t.getId(), t.getName(), t.getColor()))
            .toList();

        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    @GetMapping("/popular")
    @Operation(summary = "Get popular tags for a school")
    public ResponseEntity<ApiResponse<List<TagResponse>>> getPopularTags(@RequestParam Long schoolId) {
        List<TagResponse> tags = tagRepository.findPopularBySchoolId(schoolId).stream()
            .limit(20)
            .map(t -> new TagResponse(t.getId(), t.getName(), t.getColor()))
            .toList();
            
        return ResponseEntity.ok(ApiResponse.success(tags));
    }

    public record TagResponse(Long id, String name, String color) {}
}
