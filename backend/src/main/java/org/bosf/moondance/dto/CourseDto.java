package org.bosf.moondance.dto;

import java.time.LocalDate;

import org.bosf.moondance.entity.Course;
import org.bosf.moondance.entity.CourseSession;
import org.bosf.moondance.entity.Department;
import org.bosf.moondance.entity.Instructor;
import org.bosf.moondance.entity.School;
import org.bosf.moondance.entity.Session;

public class CourseDto {

    public static class SchoolResponse {
        private Long id;
        private String name;
        private String domain;
        private String logoUrl;
        private String city;
        private String state;
        private String country;

        public SchoolResponse() {}

        public SchoolResponse(Long id, String name, String domain, String logoUrl, String city, String state, String country) {
            this.id = id;
            this.name = name;
            this.domain = domain;
            this.logoUrl = logoUrl;
            this.city = city;
            this.state = state;
            this.country = country;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private String name;
            private String domain;
            private String logoUrl;
            private String city;
            private String state;
            private String country;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder name(String name) { this.name = name; return this; }
            public Builder domain(String domain) { this.domain = domain; return this; }
            public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
            public Builder city(String city) { this.city = city; return this; }
            public Builder state(String state) { this.state = state; return this; }
            public Builder country(String country) { this.country = country; return this; }

            public SchoolResponse build() { 
                return new SchoolResponse(id, name, domain, logoUrl, city, state, country); 
            }
        }

        public static SchoolResponse fromEntity(School school) {
            return SchoolResponse.builder()
                    .id(school.getId())
                    .name(school.getName())
                    .domain(school.getDomain())
                    .logoUrl(school.getLogoUrl())
                    .city(school.getCity())
                    .state(school.getState())
                    .country(school.getCountry())
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDomain() { return domain; }
        public void setDomain(String domain) { this.domain = domain; }
        public String getLogoUrl() { return logoUrl; }
        public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    public static class DepartmentResponse {
        private Long id;
        private String code;
        private String name;
        private String description;
        private Long schoolId;
        private Integer courseCount;

        public DepartmentResponse() {}

        public DepartmentResponse(Long id, String code, String name, String description, Long schoolId, Integer courseCount) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.description = description;
            this.schoolId = schoolId;
            this.courseCount = courseCount;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private String code;
            private String name;
            private String description;
            private Long schoolId;
            private Integer courseCount;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder code(String code) { this.code = code; return this; }
            public Builder name(String name) { this.name = name; return this; }
            public Builder description(String description) { this.description = description; return this; }
            public Builder schoolId(Long schoolId) { this.schoolId = schoolId; return this; }
            public Builder courseCount(Integer courseCount) { this.courseCount = courseCount; return this; }
            public DepartmentResponse build() { return new DepartmentResponse(id, code, name, description, schoolId, courseCount); }
        }

        public static DepartmentResponse fromEntity(Department department) {
            return DepartmentResponse.builder()
                    .id(department.getId())
                    .code(department.getCode())
                    .name(department.getName())
                    .description(department.getDescription())
                    .schoolId(department.getSchool().getId())
                    .courseCount(department.getCourses() != null ? department.getCourses().size() : 0)
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Long getSchoolId() { return schoolId; }
        public void setSchoolId(Long schoolId) { this.schoolId = schoolId; }
        public Integer getCourseCount() { return courseCount; }
        public void setCourseCount(Integer courseCount) { this.courseCount = courseCount; }
    }

    public static class CourseResponse {
        private Long id;
        private String code;
        private String title;
        private String description;
        private Integer credits;
        private Long departmentId;
        private String departmentName;

        public CourseResponse() {}

        public CourseResponse(Long id, String code, String title, String description, Integer credits, Long departmentId, String departmentName) {
            this.id = id;
            this.code = code;
            this.title = title;
            this.description = description;
            this.credits = credits;
            this.departmentId = departmentId;
            this.departmentName = departmentName;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private String code;
            private String title;
            private String description;
            private Integer credits;
            private Long departmentId;
            private String departmentName;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder code(String code) { this.code = code; return this; }
            public Builder title(String title) { this.title = title; return this; }
            public Builder description(String description) { this.description = description; return this; }
            public Builder credits(Integer credits) { this.credits = credits; return this; }
            public Builder departmentId(Long departmentId) { this.departmentId = departmentId; return this; }
            public Builder departmentName(String departmentName) { this.departmentName = departmentName; return this; }

            public CourseResponse build() { 
                return new CourseResponse(id, code, title, description, credits, departmentId, departmentName); 
            }
        }

        public static CourseResponse fromEntity(Course course) {
            return CourseResponse.builder()
                    .id(course.getId())
                    .code(course.getCode())
                    .title(course.getTitle())
                    .description(course.getDescription())
                    .credits(course.getCredits())
                    .departmentId(course.getDepartment().getId())
                    .departmentName(course.getDepartment().getName())
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public Integer getCredits() { return credits; }
        public void setCredits(Integer credits) { this.credits = credits; }
        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    }

    public static class SessionResponse {
        private Long id;
        private String name;
        private Session.SessionType type;
        private Integer year;
        private LocalDate startDate;
        private LocalDate endDate;

        public SessionResponse() {}

        public SessionResponse(Long id, String name, Session.SessionType type, Integer year, LocalDate startDate, LocalDate endDate) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.year = year;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private String name;
            private Session.SessionType type;
            private Integer year;
            private LocalDate startDate;
            private LocalDate endDate;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder name(String name) { this.name = name; return this; }
            public Builder type(Session.SessionType type) { this.type = type; return this; }
            public Builder year(Integer year) { this.year = year; return this; }
            public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
            public Builder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
            public SessionResponse build() { return new SessionResponse(id, name, type, year, startDate, endDate); }
        }

        public static SessionResponse fromEntity(Session session) {
            return SessionResponse.builder()
                    .id(session.getId())
                    .name(session.getName())
                    .type(session.getType())
                    .year(session.getYear())
                    .startDate(session.getStartDate())
                    .endDate(session.getEndDate())
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Session.SessionType getType() { return type; }
        public void setType(Session.SessionType type) { this.type = type; }
        public Integer getYear() { return year; }
        public void setYear(Integer year) { this.year = year; }
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    }

    public static class InstructorResponse {
        private Long id;
        private String name;
        private String email;
        private String title;
        private Long departmentId;
        private String departmentName;

        public InstructorResponse() {}

        public InstructorResponse(Long id, String name, String email, String title, Long departmentId, String departmentName) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.title = title;
            this.departmentId = departmentId;
            this.departmentName = departmentName;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private String name;
            private String email;
            private String title;
            private Long departmentId;
            private String departmentName;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder name(String name) { this.name = name; return this; }
            public Builder email(String email) { this.email = email; return this; }
            public Builder title(String title) { this.title = title; return this; }
            public Builder departmentId(Long departmentId) { this.departmentId = departmentId; return this; }
            public Builder departmentName(String departmentName) { this.departmentName = departmentName; return this; }
            public InstructorResponse build() { return new InstructorResponse(id, name, email, title, departmentId, departmentName); }
        }

        public static InstructorResponse fromEntity(Instructor instructor) {
            return InstructorResponse.builder()
                    .id(instructor.getId())
                    .name(instructor.getName())
                    .email(instructor.getEmail())
                    .title(instructor.getTitle())
                    .departmentId(instructor.getDepartment().getId())
                    .departmentName(instructor.getDepartment().getName())
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
        public String getDepartmentName() { return departmentName; }
        public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    }

    public static class CourseSessionResponse {
        private Long id;
        private Long courseId;
        private String courseCode;
        private String courseTitle;
        private Long sessionId;
        private String sessionName;
        private Long instructorId;
        private String instructorName;
        private String section;
        private Long noteCount;
        private Long totalDownloads;

        public CourseSessionResponse() {}

        public CourseSessionResponse(Long id, Long courseId, String courseCode, String courseTitle, Long sessionId, String sessionName, Long instructorId, String instructorName, String section, Long noteCount, Long totalDownloads) {
            this.id = id;
            this.courseId = courseId;
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.sessionId = sessionId;
            this.sessionName = sessionName;
            this.instructorId = instructorId;
            this.instructorName = instructorName;
            this.section = section;
            this.noteCount = noteCount;
            this.totalDownloads = totalDownloads;
        }

        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private Long id;
            private Long courseId;
            private String courseCode;
            private String courseTitle;
            private Long sessionId;
            private String sessionName;
            private Long instructorId;
            private String instructorName;
            private String section;
            private Long noteCount;
            private Long totalDownloads;

            public Builder id(Long id) { this.id = id; return this; }
            public Builder courseId(Long courseId) { this.courseId = courseId; return this; }
            public Builder courseCode(String courseCode) { this.courseCode = courseCode; return this; }
            public Builder courseTitle(String courseTitle) { this.courseTitle = courseTitle; return this; }
            public Builder sessionId(Long sessionId) { this.sessionId = sessionId; return this; }
            public Builder sessionName(String sessionName) { this.sessionName = sessionName; return this; }
            public Builder instructorId(Long instructorId) { this.instructorId = instructorId; return this; }
            public Builder instructorName(String instructorName) { this.instructorName = instructorName; return this; }
            public Builder section(String section) { this.section = section; return this; }
            public Builder noteCount(Long noteCount) { this.noteCount = noteCount; return this; }
            public Builder totalDownloads(Long totalDownloads) { this.totalDownloads = totalDownloads; return this; }
            public CourseSessionResponse build() { return new CourseSessionResponse(id, courseId, courseCode, courseTitle, sessionId, sessionName, instructorId, instructorName, section, noteCount, totalDownloads); }
        }

        public static CourseSessionResponse fromEntity(CourseSession cs) {
            return CourseSessionResponse.builder()
                    .id(cs.getId())
                    .courseId(cs.getCourse().getId())
                    .courseCode(cs.getCourse().getCode())
                    .courseTitle(cs.getCourse().getTitle())
                    .sessionId(cs.getSession().getId())
                    .sessionName(cs.getSession().getName())
                    .instructorId(cs.getInstructor() != null ? cs.getInstructor().getId() : null)
                    .instructorName(cs.getInstructor() != null ? cs.getInstructor().getName() : null)
                    .section(cs.getSection())
                    .build();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
        public String getCourseTitle() { return courseTitle; }
        public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
        public Long getSessionId() { return sessionId; }
        public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
        public String getSessionName() { return sessionName; }
        public void setSessionName(String sessionName) { this.sessionName = sessionName; }
        public Long getInstructorId() { return instructorId; }
        public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
        public String getInstructorName() { return instructorName; }
        public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
        public String getSection() { return section; }
        public void setSection(String section) { this.section = section; }
        public Long getNoteCount() { return noteCount; }
        public void setNoteCount(Long noteCount) { this.noteCount = noteCount; }
        public Long getTotalDownloads() { return totalDownloads; }
        public void setTotalDownloads(Long totalDownloads) { this.totalDownloads = totalDownloads; }
    }
}
