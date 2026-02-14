package org.bosf.moondance.service;

import org.bosf.moondance.dto.CourseDto;
import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.entity.Course;
import org.bosf.moondance.entity.CourseSession;
import org.bosf.moondance.entity.Department;
import org.bosf.moondance.entity.School;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.CourseRepository;
import org.bosf.moondance.repository.CourseSessionRepository;
import org.bosf.moondance.repository.DepartmentRepository;
import org.bosf.moondance.repository.InstructorRepository;
import org.bosf.moondance.repository.NoteRepository;
import org.bosf.moondance.repository.SchoolRepository;
import org.bosf.moondance.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final SchoolRepository schoolRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final SessionRepository sessionRepository;
    private final InstructorRepository instructorRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final NoteRepository noteRepository;

    public CourseService(
        SchoolRepository schoolRepository, 
        DepartmentRepository departmentRepository, 
        CourseRepository courseRepository, 
        SessionRepository sessionRepository, 
        InstructorRepository instructorRepository, 
        CourseSessionRepository courseSessionRepository, 
        NoteRepository noteRepository
    ) {
        this.schoolRepository = schoolRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.sessionRepository = sessionRepository;
        this.instructorRepository = instructorRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.noteRepository = noteRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseDto.SchoolResponse> getAllSchools() {
        return schoolRepository.findAllActive().stream()
                .map(CourseDto.SchoolResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDto.SchoolResponse getSchoolById(Long id) {
        School school = schoolRepository.findActiveById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("School", id));

        return CourseDto.SchoolResponse.fromEntity(school);
    }

    @Transactional(readOnly = true)
    public List<CourseDto.DepartmentResponse> getDepartmentsBySchool(Long schoolId) {
        return departmentRepository.findBySchoolId(schoolId).stream()
                .map(CourseDto.DepartmentResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDto.DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findActiveById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Department", id));

        return CourseDto.DepartmentResponse.fromEntity(department);
    }

    @Transactional(readOnly = true)
    public List<CourseDto.CourseResponse> getCoursesByDepartment(Long departmentId) {
        return courseRepository.findByDepartmentId(departmentId).stream()
                .map(CourseDto.CourseResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseDto.CourseResponse> getCoursesBySchool(Long schoolId) {
        return courseRepository.findBySchoolId(schoolId).stream()
                .map(CourseDto.CourseResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ApiResponse.PageResponse<CourseDto.CourseResponse> searchCourses(Long schoolId, String query, Pageable pageable) {
        Page<Course> page = courseRepository.searchBySchoolId(schoolId, query, pageable);

        return ApiResponse.PageResponse.from(page, CourseDto.CourseResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public CourseDto.CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findActiveById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Course", id));

        return CourseDto.CourseResponse.fromEntity(course);
    }

    @Transactional(readOnly = true)
    public List<CourseDto.SessionResponse> getSessionsBySchool(Long schoolId) {
        return sessionRepository.findBySchoolId(schoolId).stream()
                .map(CourseDto.SessionResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseDto.InstructorResponse> getInstructorsBySchool(Long schoolId) {
        return instructorRepository.findBySchoolId(schoolId).stream()
                .map(CourseDto.InstructorResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseDto.InstructorResponse> searchInstructors(Long schoolId, String query) {
        return instructorRepository.searchBySchoolId(schoolId, query).stream()
                .map(CourseDto.InstructorResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseDto.CourseSessionResponse> getCourseSessionsByCourse(Long courseId) {
        List<CourseSession> sessions = courseSessionRepository.findByCourseId(courseId);

        return sessions.stream()
                .map(cs -> {
                    CourseDto.CourseSessionResponse response = CourseDto.CourseSessionResponse.fromEntity(cs);

                    response.setNoteCount(noteRepository.countByCourseSessionId(cs.getId()));
                    response.setTotalDownloads(noteRepository.sumDownloadsByCourseSessionId(cs.getId()));

                    return response;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDto.CourseSessionResponse getCourseSessionById(Long id) {
        CourseSession cs = courseSessionRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("CourseSession", id));

        CourseDto.CourseSessionResponse response = CourseDto.CourseSessionResponse.fromEntity(cs);

        response.setNoteCount(noteRepository.countByCourseSessionId(id));
        response.setTotalDownloads(noteRepository.sumDownloadsByCourseSessionId(id));
        
        return response;
    }
}
