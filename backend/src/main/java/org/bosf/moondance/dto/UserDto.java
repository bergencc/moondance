package org.bosf.moondance.dto;

import java.time.LocalDateTime;

import org.bosf.moondance.entity.User;

public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String major;
    private Integer graduationYear;
    private String avatarUrl;
    private User.UserRole role;
    private Integer reputationPoints;
    private Boolean emailVerified;
    private Long schoolId;
    private String schoolName;
    private LocalDateTime createdAt;

    public UserDto() {}

    public UserDto(
        Long id, 
        String email, 
        String name, 
        String major, 
        Integer graduationYear, 
        String avatarUrl, 
        User.UserRole role, 
        Integer reputationPoints, 
        Boolean emailVerified, 
        Long schoolId, 
        String schoolName, 
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.major = major;
        this.graduationYear = graduationYear;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.reputationPoints = reputationPoints;
        this.emailVerified = emailVerified;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.createdAt = createdAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String email;
        private String name;
        private String major;
        private Integer graduationYear;
        private String avatarUrl;
        private User.UserRole role;
        private Integer reputationPoints;
        private Boolean emailVerified;
        private Long schoolId;
        private String schoolName;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder major(String major) { this.major = major; return this; }
        public Builder graduationYear(Integer graduationYear) { this.graduationYear = graduationYear; return this; }
        public Builder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }
        public Builder role(User.UserRole role) { this.role = role; return this; }
        public Builder reputationPoints(Integer reputationPoints) { this.reputationPoints = reputationPoints; return this; }
        public Builder emailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; return this; }
        public Builder schoolId(Long schoolId) { this.schoolId = schoolId; return this; }
        public Builder schoolName(String schoolName) { this.schoolName = schoolName; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserDto build() { 
            return new UserDto(
                id, 
                email, 
                name, 
                major, 
                graduationYear, 
                avatarUrl, 
                role, 
                reputationPoints, 
                emailVerified, 
                schoolId,
                schoolName, 
                createdAt
            ); 
        }
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .major(user.getMajor())
                .graduationYear(user.getGraduationYear())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .reputationPoints(user.getReputationPoints())
                .emailVerified(user.getEmailVerified())
                .schoolId(user.getSchool() != null ? user.getSchool().getId() : null)
                .schoolName(user.getSchool() != null ? user.getSchool().getName() : null)
                .createdAt(user.getCreatedAt())
                .build();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }
    public Integer getReputationPoints() { return reputationPoints; }
    public void setReputationPoints(Integer reputationPoints) { this.reputationPoints = reputationPoints; }
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    public Long getSchoolId() { return schoolId; }
    public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
