package org.bosf.moondance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoteType type = NoteType.LECTURE_NOTES;

    @Column(nullable = false, length = 500)
    private String fileKey;

    @Column(length = 64)
    private String fileHash;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, length = 100)
    private String mimeType;

    @Column(length = 255)
    private String originalFileName;

    @Column(length = 500)
    private String thumbnailKey;

    @Column(columnDefinition = "TEXT")
    private String extractedContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessingStatus processingStatus = ProcessingStatus.PENDING;

    @Column(length = 50)
    private String weekLabel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_session_id", nullable = false)
    private CourseSession courseSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    @ManyToMany
    @JoinTable(
        name = "note_tags",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private Set<Vote> votes = new HashSet<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private Set<Report> reports = new HashSet<>();

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer downloadCount = 0;

    @Column(nullable = false)
    private Double averageRating = 0.0;

    @Column(nullable = false)
    private Integer voteCount = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    public enum NoteType {
        LECTURE_NOTES, EXAM_PREP, CHEAT_SHEET, SUMMARY, LAB_GUIDE, CODING_EXAMPLES, PAST_EXAM, OTHER
    }

    public enum ProcessingStatus {
        PENDING, PROCESSING, READY, FAILED
    }

    public Note() {}

    public Note(
        Long id,
        String title,
        String description,
        NoteType type,
        String fileKey,
        String fileHash,
        Long fileSize,
        String mimeType,
        String originalFileName,
        String thumbnailKey,
        String extractedContent,
        ProcessingStatus processingStatus,
        String weekLabel,
        CourseSession courseSession,
        User uploader,
        Set<Tag> tags,
        Set<Vote> votes,
        Set<Report> reports,
        Integer viewCount,
        Integer downloadCount,
        Double averageRating,
        Integer voteCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = (type != null) ? type : NoteType.LECTURE_NOTES;
        this.fileKey = fileKey;
        this.fileHash = fileHash;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.originalFileName = originalFileName;
        this.thumbnailKey = thumbnailKey;
        this.extractedContent = extractedContent;
        this.processingStatus = (processingStatus != null) ? processingStatus : ProcessingStatus.PENDING;
        this.weekLabel = weekLabel;
        this.courseSession = courseSession;
        this.uploader = uploader;
        this.tags = (tags != null) ? tags : new HashSet<>();
        this.votes = (votes != null) ? votes : new HashSet<>();
        this.reports = (reports != null) ? reports : new HashSet<>();
        this.viewCount = (viewCount != null) ? viewCount : 0;
        this.downloadCount = (downloadCount != null) ? downloadCount : 0;
        this.averageRating = (averageRating != null) ? averageRating : 0.0;
        this.voteCount = (voteCount != null) ? voteCount : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private NoteType type;
        private String fileKey;
        private String fileHash;
        private Long fileSize;
        private String mimeType;
        private String originalFileName;
        private String thumbnailKey;
        private String extractedContent;
        private ProcessingStatus processingStatus;
        private String weekLabel;
        private CourseSession courseSession;
        private User uploader;
        private Set<Tag> tags;
        private Set<Vote> votes;
        private Set<Report> reports;
        private Integer viewCount;
        private Integer downloadCount;
        private Double averageRating;
        private Integer voteCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder type(NoteType type) { this.type = type; return this; }
        public Builder fileKey(String fileKey) { this.fileKey = fileKey; return this; }
        public Builder fileHash(String fileHash) { this.fileHash = fileHash; return this; }
        public Builder fileSize(Long fileSize) { this.fileSize = fileSize; return this; }
        public Builder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public Builder originalFileName(String originalFileName) { this.originalFileName = originalFileName; return this; }
        public Builder thumbnailKey(String thumbnailKey) { this.thumbnailKey = thumbnailKey; return this; }
        public Builder extractedContent(String extractedContent) { this.extractedContent = extractedContent; return this; }
        public Builder processingStatus(ProcessingStatus processingStatus) { this.processingStatus = processingStatus; return this; }
        public Builder weekLabel(String weekLabel) { this.weekLabel = weekLabel; return this; }
        public Builder courseSession(CourseSession courseSession) { this.courseSession = courseSession; return this; }
        public Builder uploader(User uploader) { this.uploader = uploader; return this; }
        public Builder tags(Set<Tag> tags) { this.tags = tags; return this; }
        public Builder votes(Set<Vote> votes) { this.votes = votes; return this; }
        public Builder reports(Set<Report> reports) { this.reports = reports; return this; }
        public Builder viewCount(Integer viewCount) { this.viewCount = viewCount; return this; }
        public Builder downloadCount(Integer downloadCount) { this.downloadCount = downloadCount; return this; }
        public Builder averageRating(Double averageRating) { this.averageRating = averageRating; return this; }
        public Builder voteCount(Integer voteCount) { this.voteCount = voteCount; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder deletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; return this; }

        public Note build() {
            return new Note(
                id, 
                title, 
                description, 
                type, 
                fileKey, 
                fileHash, 
                fileSize, 
                mimeType, 
                originalFileName, 
                thumbnailKey, 
                extractedContent, 
                processingStatus, 
                weekLabel, 
                courseSession, 
                uploader, 
                tags, 
                votes, 
                reports, 
                viewCount, 
                downloadCount, 
                averageRating, 
                voteCount, 
                createdAt, 
                updatedAt, 
                deletedAt
            );
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public NoteType getType() { return type; }
    public void setType(NoteType type) { this.type = (type != null) ? type : NoteType.LECTURE_NOTES; }
    public String getFileKey() { return fileKey; }
    public void setFileKey(String fileKey) { this.fileKey = fileKey; }
    public String getFileHash() { return fileHash; }
    public void setFileHash(String fileHash) { this.fileHash = fileHash; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }
    public String getThumbnailKey() { return thumbnailKey; }
    public void setThumbnailKey(String thumbnailKey) { this.thumbnailKey = thumbnailKey; }
    public String getExtractedContent() { return extractedContent; }
    public void setExtractedContent(String extractedContent) { this.extractedContent = extractedContent; }
    public ProcessingStatus getProcessingStatus() { return processingStatus; }
    public void setProcessingStatus(ProcessingStatus processingStatus) { this.processingStatus = (processingStatus != null) ? processingStatus : ProcessingStatus.PENDING; }
    public String getWeekLabel() { return weekLabel; }
    public void setWeekLabel(String weekLabel) { this.weekLabel = weekLabel; }
    public CourseSession getCourseSession() { return courseSession; }
    public void setCourseSession(CourseSession courseSession) { this.courseSession = courseSession; }
    public User getUploader() { return uploader; }
    public void setUploader(User uploader) { this.uploader = uploader; }
    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = (tags != null) ? tags : new HashSet<>(); }
    public Set<Vote> getVotes() { return votes; }
    public void setVotes(Set<Vote> votes) { this.votes = (votes != null) ? votes : new HashSet<>(); }
    public Set<Report> getReports() { return reports; }
    public void setReports(Set<Report> reports) { this.reports = (reports != null) ? reports : new HashSet<>(); }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = (viewCount != null) ? viewCount : 0; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = (downloadCount != null) ? downloadCount : 0; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = (averageRating != null) ? averageRating : 0.0; }
    public Integer getVoteCount() { return voteCount; }
    public void setVoteCount(Integer voteCount) { this.voteCount = (voteCount != null) ? voteCount : 0; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
