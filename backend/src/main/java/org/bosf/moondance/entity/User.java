package org.bosf.moondance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String major;

    @Column
    private Integer graduationYear;

    @Column(length = 500)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.STUDENT;

    @Column(nullable = false)
    private Integer reputationPoints = 0;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(length = 100)
    private String verificationToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Vote> votes = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime bannedAt;

    @Column
    private LocalDateTime deletedAt;

    public enum UserRole {
        STUDENT, MODERATOR, ADMIN
    }

    public User() {}

    public User(
        Long id,
        String email,
        String passwordHash,
        String name,
        String major,
        Integer graduationYear,
        String avatarUrl,
        UserRole role,
        Integer reputationPoints,
        Boolean emailVerified,
        String verificationToken,
        School school,
        Set<Note> notes,
        Set<Vote> votes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime bannedAt,
        LocalDateTime deletedAt
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.major = major;
        this.graduationYear = graduationYear;
        this.avatarUrl = avatarUrl;
        this.role = (role != null) ? role : UserRole.STUDENT;
        this.reputationPoints = (reputationPoints != null) ? reputationPoints : 0;
        this.emailVerified = (emailVerified != null) ? emailVerified : false;
        this.verificationToken = verificationToken;
        this.school = school;
        this.notes = (notes != null) ? notes : new HashSet<>();
        this.votes = (votes != null) ? votes : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bannedAt = bannedAt;
        this.deletedAt = deletedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String email;
        private String passwordHash;
        private String name;
        private String major;
        private Integer graduationYear;
        private String avatarUrl;
        private UserRole role;
        private Integer reputationPoints;
        private Boolean emailVerified;
        private String verificationToken;
        private School school;
        private Set<Note> notes;
        private Set<Vote> votes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime bannedAt;
        private LocalDateTime deletedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder passwordHash(String passwordHash) { this.passwordHash = passwordHash; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder major(String major) { this.major = major; return this; }
        public Builder graduationYear(Integer graduationYear) { this.graduationYear = graduationYear; return this; }
        public Builder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }
        public Builder role(UserRole role) { this.role = role; return this; }
        public Builder reputationPoints(Integer reputationPoints) { this.reputationPoints = reputationPoints; return this; }
        public Builder emailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; return this; }
        public Builder verificationToken(String verificationToken) { this.verificationToken = verificationToken; return this; }
        public Builder school(School school) { this.school = school; return this; }
        public Builder notes(Set<Note> notes) { this.notes = notes; return this; }
        public Builder votes(Set<Vote> votes) { this.votes = votes; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder bannedAt(LocalDateTime bannedAt) { this.bannedAt = bannedAt; return this; }
        public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

        public User build() {
            return new User(id, email, passwordHash, name, major, graduationYear, avatarUrl, role, reputationPoints, emailVerified, verificationToken, school, notes, votes, createdAt, updatedAt, bannedAt, deletedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Integer getGraduationYear() { return graduationYear; }
    public void setGraduationYear(Integer graduationYear) { this.graduationYear = graduationYear; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = (role != null) ? role : UserRole.STUDENT; }
    public Integer getReputationPoints() { return reputationPoints; }
    public void setReputationPoints(Integer reputationPoints) { this.reputationPoints = (reputationPoints != null) ? reputationPoints : 0; }
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = (emailVerified != null) ? emailVerified : false; }
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    public Set<Note> getNotes() { return notes; }
    public void setNotes(Set<Note> notes) { this.notes = (notes != null) ? notes : new HashSet<>(); }
    public Set<Vote> getVotes() { return votes; }
    public void setVotes(Set<Vote> votes) { this.votes = (votes != null) ? votes : new HashSet<>(); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getBannedAt() { return bannedAt; }
    public void setBannedAt(LocalDateTime bannedAt) { this.bannedAt = bannedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
