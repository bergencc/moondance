package org.bosf.moondance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.bosf.moondance.dto.ApiResponse;
import org.bosf.moondance.dto.CourseDto;
import org.bosf.moondance.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Courses", description = "Course and school browsing endpoints")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/schools")
    @Operation(summary = "Get all schools")
    public ResponseEntity<ApiResponse<List<CourseDto.SchoolResponse>>> getAllSchools() {
        return ResponseEntity.ok(ApiResponse.success(courseService.getAllSchools()));
    }

    @GetMapping("/schools/{id}")
    @Operation(summary = "Get school by ID")
    public ResponseEntity<ApiResponse<CourseDto.SchoolResponse>> getSchoolById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getSchoolById(id)));
    }

    @GetMapping("/departments")
    @Operation(summary = "Get departments by school")
    public ResponseEntity<ApiResponse<List<CourseDto.DepartmentResponse>>> getDepartmentsBySchool(
        @RequestParam Long schoolId
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getDepartmentsBySchool(schoolId)));
    }

    @GetMapping("/departments/{id}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<ApiResponse<CourseDto.DepartmentResponse>> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getDepartmentById(id)));
    }

    @GetMapping("/courses")
    @Operation(summary = "Get courses by department or school")
    public ResponseEntity<ApiResponse<List<CourseDto.CourseResponse>>> getCourses(
        @RequestParam(required = false) Long departmentId,
        @RequestParam(required = false) Long schoolId
    ) {
        if (departmentId != null) {
            return ResponseEntity.ok(ApiResponse.success(courseService.getCoursesByDepartment(departmentId)));
        } else if (schoolId != null) {
            return ResponseEntity.ok(ApiResponse.success(courseService.getCoursesBySchool(schoolId)));
        }

        return ResponseEntity.badRequest().body(ApiResponse.error("Either departmentId or schoolId is required"));
    }

    @GetMapping("/courses/search")
    @Operation(summary = "Search courses")
    public ResponseEntity<ApiResponse<ApiResponse.PageResponse<CourseDto.CourseResponse>>> searchCourses(
        @RequestParam Long schoolId,
        @RequestParam String query,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.searchCourses(schoolId, query, pageable)));
    }

    @GetMapping("/courses/{id}")
    @Operation(summary = "Get course by ID")
    public ResponseEntity<ApiResponse<CourseDto.CourseResponse>> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getCourseById(id)));
    }

    @GetMapping("/sessions")
    @Operation(summary = "Get sessions by school")
    public ResponseEntity<ApiResponse<List<CourseDto.SessionResponse>>> getSessionsBySchool(
        @RequestParam Long schoolId
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getSessionsBySchool(schoolId)));
    }

    @GetMapping("/instructors")
    @Operation(summary = "Get instructors by school")
    public ResponseEntity<ApiResponse<List<CourseDto.InstructorResponse>>> getInstructorsBySchool(
        @RequestParam Long schoolId
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getInstructorsBySchool(schoolId)));
    }

    @GetMapping("/instructors/search")
    @Operation(summary = "Search instructors")
    public ResponseEntity<ApiResponse<List<CourseDto.InstructorResponse>>> searchInstructors(
        @RequestParam Long schoolId,
        @RequestParam String query
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.searchInstructors(schoolId, query)));
    }

    @GetMapping("/course-sessions")
    @Operation(summary = "Get course sessions by course")
    public ResponseEntity<ApiResponse<List<CourseDto.CourseSessionResponse>>> getCourseSessionsByCourse(
        @RequestParam Long courseId
    ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getCourseSessionsByCourse(courseId)));
    }

    @GetMapping("/course-sessions/{id}")
    @Operation(summary = "Get course session by ID")
    public ResponseEntity<ApiResponse<CourseDto.CourseSessionResponse>> getCourseSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.getCourseSessionById(id)));
    }
}
