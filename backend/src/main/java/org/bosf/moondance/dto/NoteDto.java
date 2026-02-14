package org.bosf.moondance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.bosf.moondance.entity.Note;

public class NoteDto {
    private Long id;
    private String title;
    private String description;
    private Note.NoteType type;
    private String fileKey;
    private Long fileSize;
    private String mimeType;
    private String originalFileName;
    private String thumbnailUrl;
    private Note.ProcessingStatus processingStatus;
    private String weekLabel;
    private Long courseSessionId;
    private String courseCode;
    private String courseTitle;
    private String sessionName;
    private String instructorName;
    private Long uploaderId;
    private String uploaderName;
    private String uploaderAvatarUrl;
    private Set<String> tags;
    private Integer viewCount;
    private Integer downloadCount;
    private Double averageRating;
    private Integer voteCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoteDto() {}

    public NoteDto(
        Long id, 
        String title, 
        String description, 
        Note.NoteType type, 
        String fileKey, 
        Long fileSize, 
        String mimeType, 
        String originalFileName, 
        String thumbnailUrl, 
        Note.ProcessingStatus processingStatus, 
        String weekLabel, 
        Long courseSessionId, 
        String courseCode, 
        String courseTitle, 
        String sessionName, 
        String instructorName, 
        Long uploaderId, 
        String uploaderName, 
        String uploaderAvatarUrl, 
        Set<String> tags, 
        Integer viewCount, 
        Integer downloadCount, 
        Double averageRating, 
        Integer voteCount, 
        LocalDateTime createdAt, 
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.fileKey = fileKey;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.originalFileName = originalFileName;
        this.thumbnailUrl = thumbnailUrl;
        this.processingStatus = processingStatus;
        this.weekLabel = weekLabel;
        this.courseSessionId = courseSessionId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.sessionName = sessionName;
        this.instructorName = instructorName;
        this.uploaderId = uploaderId;
        this.uploaderName = uploaderName;
        this.uploaderAvatarUrl = uploaderAvatarUrl;
        this.tags = tags;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
        this.averageRating = averageRating;
        this.voteCount = voteCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { 
        return new Builder(); 
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Note.NoteType type;
        private String fileKey;
        private Long fileSize;
        private String mimeType;
        private String originalFileName;
        private String thumbnailUrl;
        private Note.ProcessingStatus processingStatus;
        private String weekLabel;
        private Long courseSessionId;
        private String courseCode;
        private String courseTitle;
        private String sessionName;
        private String instructorName;
        private Long uploaderId;
        private String uploaderName;
        private String uploaderAvatarUrl;
        private Set<String> tags;
        private Integer viewCount;
        private Integer downloadCount;
        private Double averageRating;
        private Integer voteCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { 
            this.id = id; 
            
            return this; 
        }

        public Builder title(String title) { 
            this.title = title; 
            
            return this; 
        }

        public Builder description(String description) { 
            this.description = description; 
            
            return this; 
        }

        public Builder type(Note.NoteType type) { 
            this.type = type; 
            
            return this; 
        }

        public Builder fileKey(String fileKey) { 
            this.fileKey = fileKey; 
            
            return this; 
        }

        public Builder fileSize(Long fileSize) { 
            this.fileSize = fileSize; 
            
            return this; 
        }

        public Builder mimeType(String mimeType) { 
            this.mimeType = mimeType; 
            
            return this; 
        }

        public Builder originalFileName(String originalFileName) { 
            this.originalFileName = originalFileName; 
            
            return this; 
        }

        public Builder thumbnailUrl(String thumbnailUrl) { 
            this.thumbnailUrl = thumbnailUrl; 
            
            return this; 
        }

        public Builder processingStatus(Note.ProcessingStatus processingStatus) { 
            this.processingStatus = processingStatus; 
            
            return this; 
        }

        public Builder weekLabel(String weekLabel) { 
            this.weekLabel = weekLabel; 
            
            return this; 
        }

        public Builder courseSessionId(Long courseSessionId) { 
            this.courseSessionId = courseSessionId; 
            
            return this; 
        }

        public Builder courseCode(String courseCode) { 
            this.courseCode = courseCode; 
            
            return this; 
        }

        public Builder courseTitle(String courseTitle) { 
            this.courseTitle = courseTitle; 
            
            return this; 
        }

        public Builder sessionName(String sessionName) { 
            this.sessionName = sessionName; 
            
            return this; 
        }

        public Builder instructorName(String instructorName) { 
            this.instructorName = instructorName; 
            
            return this; 
        }

        public Builder uploaderId(Long uploaderId) { 
            this.uploaderId = uploaderId; 
            
            return this; 
        }

        public Builder uploaderName(String uploaderName) { 
            this.uploaderName = uploaderName; 
            
            return this; 
        }

        public Builder uploaderAvatarUrl(String uploaderAvatarUrl) { 
            this.uploaderAvatarUrl = uploaderAvatarUrl; 
            
            return this; 
        }

        public Builder tags(Set<String> tags) { 
            this.tags = tags; 
            
            return this; 
        }

        public Builder viewCount(Integer viewCount) { 
            this.viewCount = viewCount; 
            
            return this; 
        }

        public Builder downloadCount(Integer downloadCount) { 
            this.downloadCount = downloadCount; 
            
            return this; 
        }

        public Builder averageRating(Double averageRating) { 
            this.averageRating = averageRating; 
            
            return this; 
        }

        public Builder voteCount(Integer voteCount) { 
            this.voteCount = voteCount; 
            
            return this; 
        }

        public Builder createdAt(LocalDateTime createdAt) { 
            this.createdAt = createdAt; 
            
            return this; 
        }

        public Builder updatedAt(LocalDateTime updatedAt) { 
            this.updatedAt = updatedAt; 
            
            return this; 
        }

        public NoteDto build() {
            return new NoteDto(
                id, 
                title, 
                description, 
                type, 
                fileKey, 
                fileSize, 
                mimeType, 
                originalFileName, 
                thumbnailUrl, 
                processingStatus, 
                weekLabel, 
                courseSessionId, 
                courseCode, 
                courseTitle, 
                sessionName, 
                instructorName, 
                uploaderId, 
                uploaderName, 
                uploaderAvatarUrl, 
                tags, 
                viewCount, 
                downloadCount, 
                averageRating, 
                voteCount, 
                createdAt, 
                updatedAt
            );
        }
    }

    public static NoteDto fromEntity(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .description(note.getDescription())
                .type(note.getType())
                .fileKey(note.getFileKey())
                .fileSize(note.getFileSize())
                .mimeType(note.getMimeType())
                .originalFileName(note.getOriginalFileName())
                .thumbnailUrl(note.getThumbnailKey())
                .processingStatus(note.getProcessingStatus())
                .weekLabel(note.getWeekLabel())
                .courseSessionId(note.getCourseSession().getId())
                .courseCode(note.getCourseSession().getCourse().getCode())
                .courseTitle(note.getCourseSession().getCourse().getTitle())
                .sessionName(note.getCourseSession().getSession().getName())
                .instructorName(note.getCourseSession().getInstructor() != null ? note.getCourseSession().getInstructor().getName() : null)
                .uploaderId(note.getUploader().getId())
                .uploaderName(note.getUploader().getName())
                .uploaderAvatarUrl(note.getUploader().getAvatarUrl())
                .tags(note.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet()))
                .viewCount(note.getViewCount())
                .downloadCount(note.getDownloadCount())
                .averageRating(note.getAverageRating())
                .voteCount(note.getVoteCount())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public Note.NoteType getType() { 
        return type; 
    }

    public void setType(Note.NoteType type) { 
        this.type = type; 
    }

    public String getFileKey() { 
        return fileKey; 
    }

    public void setFileKey(String fileKey) { 
        this.fileKey = fileKey; 
    }

    public Long getFileSize() { 
        return fileSize; 
    }

    public void setFileSize(Long fileSize) { 
        this.fileSize = fileSize; 
    }

    public String getMimeType() { 
        return mimeType; 
    }

    public void setMimeType(String mimeType) { 
        this.mimeType = mimeType; 
    }

    public String getOriginalFileName() { 
        return originalFileName; 
    }

    public void setOriginalFileName(String originalFileName) { 
        this.originalFileName = originalFileName; 
    }

    public String getThumbnailUrl() { 
        return thumbnailUrl; 
    }

    public void setThumbnailUrl(String thumbnailUrl) { 
        this.thumbnailUrl = thumbnailUrl; 
    }

    public Note.ProcessingStatus getProcessingStatus() { 
        return processingStatus; 
    }

    public void setProcessingStatus(Note.ProcessingStatus processingStatus) { 
        this.processingStatus = processingStatus; 
    }

    public String getWeekLabel() { 
        return weekLabel; 
    }

    public void setWeekLabel(String weekLabel) { 
        this.weekLabel = weekLabel; 
    }

    public Long getCourseSessionId() { 
        return courseSessionId; 
    }
    public void setCourseSessionId(Long courseSessionId) { 
        this.courseSessionId = courseSessionId; 
    }

    public String getCourseCode() { 
        return courseCode; 
    }

    public void setCourseCode(String courseCode) { 
        this.courseCode = courseCode; 
    }

    public String getCourseTitle() { 
        return courseTitle; 
    }

    public void setCourseTitle(String courseTitle) { 
        this.courseTitle = courseTitle; 
    }

    public String getSessionName() { 
        return sessionName; 
    }

    public void setSessionName(String sessionName) { 
        this.sessionName = sessionName; 
    }

    public String getInstructorName() { 
        return instructorName; 
    }

    public void setInstructorName(String instructorName) { 
        this.instructorName = instructorName; 
    }

    public Long getUploaderId() { 
        return uploaderId; 
    }

    public void setUploaderId(Long uploaderId) { 
        this.uploaderId = uploaderId; 
    }

    public String getUploaderName() { 
        return uploaderName; 
    }

    public void setUploaderName(String uploaderName) { 
        this.uploaderName = uploaderName; 
    }

    public String getUploaderAvatarUrl() { 
        return uploaderAvatarUrl;
    }

    public void setUploaderAvatarUrl(String uploaderAvatarUrl) { 
        this.uploaderAvatarUrl = uploaderAvatarUrl; 
    }

    public Set<String> getTags() { 
        return tags; 
    }

    public void setTags(Set<String> tags) { 
        this.tags = tags; 
    }

    public Integer getViewCount() { 
        return viewCount; 
    }

    public void setViewCount(Integer viewCount) { 
        this.viewCount = viewCount; 
    }

    public Integer getDownloadCount() { 
        return downloadCount; 
    }

    public void setDownloadCount(Integer downloadCount) { 
        this.downloadCount = downloadCount; 
    }

    public Double getAverageRating() { 
        return averageRating; 
    }

    public void setAverageRating(Double averageRating) { 
        this.averageRating = averageRating; 
    }

    public Integer getVoteCount() { 
        return voteCount; 
    }

    public void setVoteCount(Integer voteCount) { 
        this.voteCount = voteCount; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }

    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }

    public static class CreateRequest {
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be less than 255 characters")
        private String title;

        private String description;

        @NotNull(message = "Note type is required")
        private Note.NoteType type;

        @NotNull(message = "Course session is required")
        private Long courseSessionId;

        private String weekLabel;
        private Set<String> tags;

        public CreateRequest() {}

        public CreateRequest(String title, String description, Note.NoteType type, Long courseSessionId, String weekLabel, Set<String> tags) {
            this.title = title;
            this.description = description;
            this.type = type;
            this.courseSessionId = courseSessionId;
            this.weekLabel = weekLabel;
            this.tags = tags;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String title;
            private String description;
            private Note.NoteType type;
            private Long courseSessionId;
            private String weekLabel;
            private Set<String> tags;

            public Builder title(String title) { 
                this.title = title; 
                
                return this; 
            }

            public Builder description(String description) { 
                this.description = description; 
                
                return this; 
            }

            public Builder type(Note.NoteType type) { 
                this.type = type; 
                
                return this; 
            }

            public Builder courseSessionId(Long courseSessionId) { 
                this.courseSessionId = courseSessionId; 
                
                return this; 
            }

            public Builder weekLabel(String weekLabel) { 
                this.weekLabel = weekLabel; 
                
                return this; 
            }

            public Builder tags(Set<String> tags) { 
                this.tags = tags; 
                
                return this; 
            }

            public CreateRequest build() { 
                return new CreateRequest(title, description, type, courseSessionId, weekLabel, tags); 
            }
        }

        public String getTitle() { 
            return title; 
        }

        public void setTitle(String title) { 
            this.title = title; 
        }

        public String getDescription() { 
            return description; 
        }

        public void setDescription(String description) { 
            this.description = description; 
        }

        public Note.NoteType getType() { 
            return type; 
        }

        public void setType(Note.NoteType type) { 
            this.type = type; 
        }

        public Long getCourseSessionId() { 
            return courseSessionId; 
        }

        public void setCourseSessionId(Long courseSessionId) { 
            this.courseSessionId = courseSessionId; 
        }

        public String getWeekLabel() { 
            return weekLabel; 
        }

        public void setWeekLabel(String weekLabel) { 
            this.weekLabel = weekLabel; 
        }

        public Set<String> getTags() { 
            return tags; 
        }

        public void setTags(Set<String> tags) { 
            this.tags = tags; 
        }
    }

    public static class UpdateRequest {
        @Size(max = 255, message = "Title must be less than 255 characters")
        private String title;

        private String description;
        private Note.NoteType type;
        private String weekLabel;
        private Set<String> tags;

        public UpdateRequest() {}

        public UpdateRequest(String title, String description, Note.NoteType type, String weekLabel, Set<String> tags) {
            this.title = title;
            this.description = description;
            this.type = type;
            this.weekLabel = weekLabel;
            this.tags = tags;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String title;
            private String description;
            private Note.NoteType type;
            private String weekLabel;
            private Set<String> tags;

            public Builder title(String title) { 
                this.title = title; 
                
                return this; 
            }

            public Builder description(String description) { 
                this.description = description; 
                
                return this; 
            }

            public Builder type(Note.NoteType type) { 
                this.type = type; 
                
                return this; 
            }

            public Builder weekLabel(String weekLabel) { 
                this.weekLabel = weekLabel; 
                
                return this; 
            }

            public Builder tags(Set<String> tags) { 
                this.tags = tags; 
                
                return this; 
            }

            public UpdateRequest build() { 
                return new UpdateRequest(title, description, type, weekLabel, tags); 
            }
        }

        public String getTitle() { 
            return title; 
        }

        public void setTitle(String title) { 
            this.title = title; 
        }

        public String getDescription() { 
            return description; 
        }

        public void setDescription(String description) { 
            this.description = description; 
        }

        public Note.NoteType getType() { 
            return type; 
        }

        public void setType(Note.NoteType type) { 
            this.type = type; 
        }

        public String getWeekLabel() { 
            return weekLabel; 
        }

        public void setWeekLabel(String weekLabel) { 
            this.weekLabel = weekLabel; 
        }

        public Set<String> getTags() { 
            return tags; 
        }

        public void setTags(Set<String> tags) { 
            this.tags = tags; 
        }
    }
}
