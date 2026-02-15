package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.AuthDto;
import org.bosf.moondance.security.UserPrincipal;
import org.bosf.moondance.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> register(
        @Valid @RequestBody AuthDto.RegisterRequest request
    ) {
        AuthDto.AuthResponse response = authService.register(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> login(
        @Valid @RequestBody AuthDto.LoginRequest request
    ) {
        AuthDto.AuthResponse response = authService.login(request);

        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> refresh(
        @Valid @RequestBody AuthDto.RefreshTokenRequest request
    ) {
        AuthDto.AuthResponse response = authService.refreshToken(request);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/change-password")
    @Operation(summary = "Change password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody AuthDto.ChangePasswordRequest request
    ) {
        authService.changePassword(principal.getId(), request);

        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Verify email address")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);

        return ResponseEntity.ok(ApiResponse.success("Email verified successfully", null));
    }
}
