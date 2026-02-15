package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.UserDto;
import org.bosf.moondance.entity.User;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.UserRepository;
import org.bosf.moondance.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User profile endpoints")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        User user = userRepository.findActiveById(principal.getId())
            .orElseThrow(() -> new ApiException.NotFoundException("User", principal.getId()));

        return ResponseEntity.ok(ApiResponse.success(UserDto.fromEntity(user)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID (public profile)")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        User user = userRepository.findActiveById(id)
            .orElseThrow(() -> new ApiException.NotFoundException("User", id));
        
        // Return limited public info
        UserDto dto = UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .major(user.getMajor())
            .graduationYear(user.getGraduationYear())
            .avatarUrl(user.getAvatarUrl())
            .reputationPoints(user.getReputationPoints())
            .schoolName(user.getSchool() != null ? user.getSchool().getName() : null)
            .createdAt(user.getCreatedAt())
            .build();
        
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PatchMapping("/me")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody UpdateProfileRequest request
    ) {
        User user = userRepository.findActiveById(principal.getId())
            .orElseThrow(() -> new ApiException.NotFoundException("User", principal.getId()));
        
        if (request.name != null) {
            user.setName(request.name);
        }

        if (request.major != null) {
            user.setMajor(request.major);
        }

        if (request.graduationYear != null) {
            user.setGraduationYear(request.graduationYear);
        }

        if (request.avatarUrl != null) {
            user.setAvatarUrl(request.avatarUrl);
        }
        
        user = userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("Profile updated", UserDto.fromEntity(user)));
    }

    public record UpdateProfileRequest(
        String name,
        String major,
        Integer graduationYear,
        String avatarUrl
    ) {}
}
