package org.bosf.moondance.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import org.bosf.moondance.entity.Report;
import org.bosf.moondance.entity.Vote;

public class InteractionDto {

    public static class VoteRequest {
        @NotNull(message = "Vote value is required")
        @Min(value = -1, message = "Vote must be -1, 0, or 1")
        @Max(value = 1, message = "Vote must be -1, 0, or 1")
        private Integer value;

        @Min(value = 1, message = "Rating must be between 1 and 5")
        @Max(value = 5, message = "Rating must be between 1 and 5")
        private Integer rating;

        public VoteRequest() {}

        public VoteRequest(Integer value, Integer rating) {
            this.value = value;
            this.rating = rating;
        }

        public static Builder builder() { 
            return new Builder(); 
        }

        public static class Builder {
            private Integer value;
            private Integer rating;

            public Builder value(Integer value) { 
                this.value = value; 

                return this; 
            }

            public Builder rating(Integer rating) { 
                this.rating = rating;

                return this;
            }

            public VoteRequest build() { 
                return new VoteRequest(value, rating); 
            }
        }

        public Integer getValue() { 
            return value; 
        }

        public void setValue(Integer value) { 
            this.value = value; 
        }

        public Integer getRating() { 
            return rating; 
        }

        public void setRating(Integer rating) { 
            this.rating = rating;
        }
    }

    public static class VoteResponse {
        private Long id;
        private Long noteId;
        private Long userId;
        private Integer value;
        private Integer rating;
        private LocalDateTime createdAt;

        public VoteResponse() {}

        public VoteResponse(Long id, Long noteId, Long userId, Integer value, Integer rating, LocalDateTime createdAt) {
            this.id = id;
            this.noteId = noteId;
            this.userId = userId;
            this.value = value;
            this.rating = rating;
            this.createdAt = createdAt;
        }

        public static Builder builder() { 
            return new Builder(); 
        }

        public static class Builder {
            private Long id;
            private Long noteId;
            private Long userId;
            private Integer value;
            private Integer rating;
            private LocalDateTime createdAt;

            public Builder id(Long id) { 
                this.id = id; 
                
                return this;
            }

            public Builder noteId(Long noteId) { 
                this.noteId = noteId; 
                
                return this; 
            }

            public Builder userId(Long userId) { 
                this.userId = userId; 
                
                return this; 
            }

            public Builder value(Integer value) { 
                this.value = value; 
                
                return this; 
            }

            public Builder rating(Integer rating) { 
                this.rating = rating; 
                
                return this; 
            }

            public Builder createdAt(LocalDateTime createdAt) { 
                this.createdAt = createdAt; 
                
                return this; 
            }

            public VoteResponse build() { 
                return new VoteResponse(id, noteId, userId, value, rating, createdAt); 
            }
        }

        public static VoteResponse fromEntity(Vote vote) {
            return VoteResponse.builder()
                    .id(vote.getId())
                    .noteId(vote.getNote().getId())
                    .userId(vote.getUser().getId())
                    .value(vote.getValue())
                    .rating(vote.getRating())
                    .createdAt(vote.getCreatedAt())
                    .build();
        }

        public Long getId() { 
            return id; 
        }

        public void setId(Long id) { 
            this.id = id; 
        }

        public Long getNoteId() { 
            return noteId; 
        }

        public void setNoteId(Long noteId) { 
            this.noteId = noteId; 
        }

        public Long getUserId() { 
            return userId; 
        }

        public void setUserId(Long userId) { 
            this.userId = userId; 
        }

        public Integer getValue() { 
            return value; 
        }

        public void setValue(Integer value) { 
            this.value = value; 
        }

        public Integer getRating() { 
            return rating; 
        }

        public void setRating(Integer rating) { 
            this.rating = rating; 
        }

        public LocalDateTime getCreatedAt() {
            return createdAt; 
        }

        public void setCreatedAt(LocalDateTime createdAt) { 
            this.createdAt = createdAt; 
        }
    }

    public static class ReportRequest {
        @NotNull(message = "Report reason is required")
        private Report.ReportReason reason;

        private String description;

        public ReportRequest() {}

        public ReportRequest(Report.ReportReason reason, String description) {
            this.reason = reason;
            this.description = description;
        }

        public static Builder builder() { 
            return new Builder();
        }

        public static class Builder {
            private Report.ReportReason reason;
            private String description;

            public Builder reason(Report.ReportReason reason) { 
                this.reason = reason; 
                
                return this; 
            }

            public Builder description(String description) { 
                this.description = description; 
                
                return this; 
            }

            public ReportRequest build() { 
                return new ReportRequest(reason, description); 
            }
        }

        public Report.ReportReason getReason() { 
            return reason; 
        }

        public void setReason(Report.ReportReason reason) { 
            this.reason = reason; 
        }

        public String getDescription() { 
            return description; 
        }

        public void setDescription(String description) { 
            this.description = description; 
        }
    }

    public static class ReportResponse {
        private Long id;
        private Long noteId;
        private String noteTitle;
        private Long reporterId;
        private String reporterName;
        private Report.ReportReason reason;
        private String description;
        private Report.ReportStatus status;
        private String moderatorNotes;
        private LocalDateTime reviewedAt;
        private LocalDateTime createdAt;

        public ReportResponse() {}

        public ReportResponse(
            Long id, 
            Long noteId, 
            String noteTitle, 
            Long reporterId, 
            String reporterName, 
            Report.ReportReason reason, 
            String description, 
            Report.ReportStatus status, 
            String moderatorNotes, 
            LocalDateTime reviewedAt, 
            LocalDateTime createdAt
        ) {
            this.id = id;
            this.noteId = noteId;
            this.noteTitle = noteTitle;
            this.reporterId = reporterId;
            this.reporterName = reporterName;
            this.reason = reason;
            this.description = description;
            this.status = status;
            this.moderatorNotes = moderatorNotes;
            this.reviewedAt = reviewedAt;
            this.createdAt = createdAt;
        }

        public static Builder builder() { 
            return new Builder(); 
        }

        public static class Builder {
            private Long id;
            private Long noteId;
            private String noteTitle;
            private Long reporterId;
            private String reporterName;
            private Report.ReportReason reason;
            private String description;
            private Report.ReportStatus status;
            private String moderatorNotes;
            private LocalDateTime reviewedAt;
            private LocalDateTime createdAt;

            public Builder id(Long id) { 
                this.id = id; 
                
                return this; 
            }

            public Builder noteId(Long noteId) { 
                this.noteId = noteId; 
                
                return this; 
            }

            public Builder noteTitle(String noteTitle) { 
                this.noteTitle = noteTitle; 
                
                return this; 
            }

            public Builder reporterId(Long reporterId) { 
                this.reporterId = reporterId; 
                
                return this; 
            }

            public Builder reporterName(String reporterName) { 
                this.reporterName = reporterName; 
                
                return this; 
            }

            public Builder reason(Report.ReportReason reason) { 
                this.reason = reason; 
                
                return this; 
            }

            public Builder description(String description) { 
                this.description = description; 
                
                return this; 
            }

            public Builder status(Report.ReportStatus status) { 
                this.status = status; 
                
                return this; 
            }

            public Builder moderatorNotes(String moderatorNotes) { 
                this.moderatorNotes = moderatorNotes;
                
                return this; 
            }

            public Builder reviewedAt(LocalDateTime reviewedAt) { 
                this.reviewedAt = reviewedAt; 
                
                return this; 
            }

            public Builder createdAt(LocalDateTime createdAt) { 
                this.createdAt = createdAt; 
                
                return this; 
            }

            public ReportResponse build() { 
                return new ReportResponse(
                    id, 
                    noteId, 
                    noteTitle, 
                    reporterId, 
                    reporterName, 
                    reason, 
                    description, 
                    status, 
                    moderatorNotes, 
                    reviewedAt, 
                    createdAt); 
                }
        }

        public static ReportResponse fromEntity(Report report) {
            return ReportResponse.builder()
                    .id(report.getId())
                    .noteId(report.getNote().getId())
                    .noteTitle(report.getNote().getTitle())
                    .reporterId(report.getReporter().getId())
                    .reporterName(report.getReporter().getName())
                    .reason(report.getReason())
                    .description(report.getDescription())
                    .status(report.getStatus())
                    .moderatorNotes(report.getModeratorNotes())
                    .reviewedAt(report.getReviewedAt())
                    .createdAt(report.getCreatedAt())
                    .build();
        }

        public Long getId() { 
            return id; 
        }

        public void setId(Long id) { 
            this.id = id; 
        }

        public Long getNoteId() { 
            return noteId; 
        }

        public void setNoteId(Long noteId) { 
            this.noteId = noteId; 
        }

        public String getNoteTitle() { 
            return noteTitle; 
        }

        public void setNoteTitle(String noteTitle) { 
            this.noteTitle = noteTitle; 
        }

        public Long getReporterId() { 
            return reporterId; 
        }

        public void setReporterId(Long reporterId) { 
            this.reporterId = reporterId; 
        }

        public String getReporterName() { 
            return reporterName; 
        }

        public void setReporterName(String reporterName) { 
            this.reporterName = reporterName; 
        }

        public Report.ReportReason getReason() { 
            return reason; 
        }

        public void setReason(Report.ReportReason reason) { 
            this.reason = reason;
        }

        public String getDescription() { 
            return description; 
        }

        public void setDescription(String description) { 
            this.description = description; 
        }

        public Report.ReportStatus getStatus() { 
            return status; 
        }

        public void setStatus(Report.ReportStatus status) { 
            this.status = status; 
        }

        public String getModeratorNotes() { 
            return moderatorNotes; 
        }

        public void setModeratorNotes(String moderatorNotes) { 
            this.moderatorNotes = moderatorNotes; 
        }

        public LocalDateTime getReviewedAt() { 
            return reviewedAt; 
        }

        public void setReviewedAt(LocalDateTime reviewedAt) { 
            this.reviewedAt = reviewedAt; 
        }

        public LocalDateTime getCreatedAt() { 
            return createdAt; 
        }

        public void setCreatedAt(LocalDateTime createdAt) { 
            this.createdAt = createdAt; 
        }
    }

    public static class ReportReviewRequest {
        @NotNull(message = "Status is required")
        private Report.ReportStatus status;

        private String moderatorNotes;

        public ReportReviewRequest() {}

        public ReportReviewRequest(Report.ReportStatus status, String moderatorNotes) {
            this.status = status;
            this.moderatorNotes = moderatorNotes;
        }

        public static Builder builder() { 
            return new Builder(); 
        }

        public static class Builder {
            private Report.ReportStatus status;
            private String moderatorNotes;

            public Builder status(Report.ReportStatus status) { 
                this.status = status; 
                
                return this; 
            }

            public Builder moderatorNotes(String moderatorNotes) { 
                this.moderatorNotes = moderatorNotes; 
                
                return this; 
            }

            public ReportReviewRequest build() { 
                return new ReportReviewRequest(status, moderatorNotes); 
            }
        }

        public Report.ReportStatus getStatus() { 
            return status; 
        }

        public void setStatus(Report.ReportStatus status) { 
            this.status = status; 
        }

        public String getModeratorNotes() { 
            return moderatorNotes; 
        }

        public void setModeratorNotes(String moderatorNotes) { 
            this.moderatorNotes = moderatorNotes; 
        }
    }
}
