package org.bosf.moondance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(columnDefinition = "TEXT")
    private String moderatorNotes;

    @Column
    private LocalDateTime reviewedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum ReportReason {
        SPAM, COPYRIGHT, WRONG_COURSE, INAPPROPRIATE, LOW_QUALITY, OTHER
    }

    public enum ReportStatus {
        PENDING, REVIEWED, RESOLVED, DISMISSED
    }

    public Report() {}

    public Report(
        Long id,
        Note note,
        User reporter,
        ReportReason reason,
        String description,
        ReportStatus status,
        User reviewedBy,
        String moderatorNotes,
        LocalDateTime reviewedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.note = note;
        this.reporter = reporter;
        this.reason = reason;
        this.description = description;
        this.status = (status != null) ? status : ReportStatus.PENDING;
        this.reviewedBy = reviewedBy;
        this.moderatorNotes = moderatorNotes;
        this.reviewedAt = reviewedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Note note;
        private User reporter;
        private ReportReason reason;
        private String description;
        private ReportStatus status;
        private User reviewedBy;
        private String moderatorNotes;
        private LocalDateTime reviewedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder note(Note note) { this.note = note; return this; }
        public Builder reporter(User reporter) { this.reporter = reporter; return this; }
        public Builder reason(ReportReason reason) { this.reason = reason; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder status(ReportStatus status) { this.status = status; return this; }
        public Builder reviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; return this; }
        public Builder moderatorNotes(String moderatorNotes) { this.moderatorNotes = moderatorNotes; return this; }
        public Builder reviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Report build() {
            return new Report(id, note, reporter, reason, description, status, reviewedBy, moderatorNotes, reviewedAt, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Note getNote() { return note; }
    public void setNote(Note note) { this.note = note; }
    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }
    public ReportReason getReason() { return reason; }
    public void setReason(ReportReason reason) { this.reason = reason; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ReportStatus getStatus() { return status; }
    public void setStatus(ReportStatus status) { this.status = (status != null) ? status : ReportStatus.PENDING; }
    public User getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; }
    public String getModeratorNotes() { return moderatorNotes; }
    public void setModeratorNotes(String moderatorNotes) { this.moderatorNotes = moderatorNotes; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
