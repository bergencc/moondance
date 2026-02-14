package org.bosf.moondance.service;

import org.bosf.moondance.dto.AuthDto;
import org.bosf.moondance.dto.UserDto;
import org.bosf.moondance.entity.School;
import org.bosf.moondance.entity.User;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.SchoolRepository;
import org.bosf.moondance.repository.UserRepository;
import org.bosf.moondance.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
        UserRepository userRepository, 
        SchoolRepository schoolRepository, 
        PasswordEncoder passwordEncoder, 
        JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException.ConflictException("Email already registered");
        }

        String emailDomain = request.getEmail().substring(request.getEmail().indexOf('@') + 1);
        School school = schoolRepository.findByDomain(emailDomain).orElse(null);

        if (school == null && request.getInviteCode() != null) {
            school = schoolRepository.findByInviteCode(request.getInviteCode())
                    .orElseThrow(() -> new ApiException.BadRequestException("Invalid invite code"));
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .major(request.getMajor())
                .graduationYear(request.getGraduationYear())
                .school(school)
                .verificationToken(UUID.randomUUID().toString())
                .build();

        user = userRepository.save(user);

        log.info("User registered: {}", user.getEmail());

        return generateAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        User user = userRepository.findActiveByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException.UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ApiException.UnauthorizedException("Invalid email or password");
        }

        log.info("User logged in: {}", user.getEmail());

        return generateAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthDto.AuthResponse refreshToken(AuthDto.RefreshTokenRequest request) {
        String token = request.getRefreshToken();

        if (!jwtTokenProvider.validateToken(token) || !jwtTokenProvider.isRefreshToken(token)) {
            throw new ApiException.UnauthorizedException("Invalid refresh token");
        }

        Long userId = Long.parseLong(jwtTokenProvider.getUserIdFromToken(token));
        User user = userRepository.findActiveById(userId)
                .orElseThrow(() -> new ApiException.UnauthorizedException("User not found"));

        return generateAuthResponse(user);
    }

    @Transactional
    public void changePassword(Long userId, AuthDto.ChangePasswordRequest request) {
        User user = userRepository.findActiveById(userId)
                .orElseThrow(() -> new ApiException.NotFoundException("User", userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new ApiException.BadRequestException("Current password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed for user: {}", user.getEmail());
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ApiException.BadRequestException("Invalid verification token"));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        
        log.info("Email verified for user: {}", user.getEmail());
    }

    private AuthDto.AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return AuthDto.AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration())
                .user(UserDto.fromEntity(user))
                .build();
    }
}
