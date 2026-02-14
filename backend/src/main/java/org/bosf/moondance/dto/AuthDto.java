package org.bosf.moondance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDto {

    public static class RegisterRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        private String password;

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        private String name;

        private String major;
        private Integer graduationYear;
        private String inviteCode;

        public RegisterRequest() {}

        public RegisterRequest(String email, String password, String name, String major, Integer graduationYear, String inviteCode) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.major = major;
            this.graduationYear = graduationYear;
            this.inviteCode = inviteCode;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String email;
            private String password;
            private String name;
            private String major;
            private Integer graduationYear;
            private String inviteCode;

            public Builder email(String email) { this.email = email; return this; }
            public Builder password(String password) { this.password = password; return this; }
            public Builder name(String name) { this.name = name; return this; }
            public Builder major(String major) { this.major = major; return this; }
            public Builder graduationYear(Integer graduationYear) { this.graduationYear = graduationYear; return this; }
            public Builder inviteCode(String inviteCode) { this.inviteCode = inviteCode; return this; }
            public RegisterRequest build() { return new RegisterRequest(email, password, name, major, graduationYear, inviteCode); }
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getMajor() { return major; }
        public void setMajor(String major) { this.major = major; }
        public Integer getGraduationYear() { return graduationYear; }
        public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }
        public String getInviteCode() { return inviteCode; }
        public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    }

    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        public LoginRequest() {}

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String email;
            private String password;

            public Builder email(String email) { this.email = email; return this; }
            public Builder password(String password) { this.password = password; return this; }
            public LoginRequest build() { return new LoginRequest(email, password); }
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
        private UserDto user;

        public AuthResponse() {}

        public AuthResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn, UserDto user) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
            this.user = user;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String accessToken;
            private String refreshToken;
            private String tokenType;
            private Long expiresIn;
            private UserDto user;

            public Builder accessToken(String accessToken) { this.accessToken = accessToken; return this; }
            public Builder refreshToken(String refreshToken) { this.refreshToken = refreshToken; return this; }
            public Builder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
            public Builder expiresIn(Long expiresIn) { this.expiresIn = expiresIn; return this; }
            public Builder user(UserDto user) { this.user = user; return this; }
            public AuthResponse build() { return new AuthResponse(accessToken, refreshToken, tokenType, expiresIn, user); }
        }

        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }
        public Long getExpiresIn() { return expiresIn; }
        public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
        public UserDto getUser() { return user; }
        public void setUser(UserDto user) { this.user = user; }
    }

    public static class RefreshTokenRequest {
        @NotBlank(message = "Refresh token is required")
        private String refreshToken;

        public RefreshTokenRequest() {}

        public RefreshTokenRequest(String refreshToken) { this.refreshToken = refreshToken; }

        public static Builder builder() { return new Builder(); }

        public static class Builder { 
            private String refreshToken; 

            public Builder refreshToken(String refreshToken) { 
                this.refreshToken = refreshToken; 
                
                return this; 
            } 
            
            public RefreshTokenRequest build() { 
                return new RefreshTokenRequest(refreshToken); 
            } 
        }

        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }

    public static class ChangePasswordRequest {
        @NotBlank(message = "Current password is required")
        private String currentPassword;

        @NotBlank(message = "New password is required")
        @Size(min = 8, message = "New password must be at least 8 characters")
        private String newPassword;

        public ChangePasswordRequest() {}

        public ChangePasswordRequest(String currentPassword, String newPassword) {
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder { 
            private String currentPassword; 
            private String newPassword; 
            
            public Builder currentPassword(String currentPassword) { 
                this.currentPassword = currentPassword; 
                
                return this; 
            } 
            
            public Builder newPassword(String newPassword) { 
                this.newPassword = newPassword; 
                
                return this; 
            } 
            
            public ChangePasswordRequest build() { 
                return new ChangePasswordRequest(currentPassword, newPassword); 
            } 
        }

        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}
