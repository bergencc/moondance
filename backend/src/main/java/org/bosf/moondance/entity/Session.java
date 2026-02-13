package org.bosf.moondance.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionType type;

    @Column(nullable = false)
    private Integer year;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private Set<CourseSession> courseSessions = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum SessionType {
        FALL, SPRING, SUMMER, WINTER, YEAR_ROUND
    }

    public Session() {}

    public Session(
        Long id,
        String name,
        SessionType type,
        Integer year,
        LocalDate startDate,
        LocalDate endDate,
        School school,
        Set<CourseSession> courseSessions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.school = school;
        this.courseSessions = (courseSessions != null) ? courseSessions : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private SessionType type;
        private Integer year;
        private LocalDate startDate;
        private LocalDate endDate;
        private School school;
        private Set<CourseSession> courseSessions;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder type(SessionType type) { this.type = type; return this; }
        public Builder year(Integer year) { this.year = year; return this; }
        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder school(School school) { this.school = school; return this; }
        public Builder courseSessions(Set<CourseSession> courseSessions) { this.courseSessions = courseSessions; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Session build() {
            return new Session(id, name, type, year, startDate, endDate, school, courseSessions, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public SessionType getType() { return type; }
    public void setType(SessionType type) { this.type = type; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    public Set<CourseSession> getCourseSessions() { return courseSessions; }
    public void setCourseSessions(Set<CourseSession> courseSessions) { this.courseSessions = (courseSessions != null) ? courseSessions : new HashSet<>(); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
