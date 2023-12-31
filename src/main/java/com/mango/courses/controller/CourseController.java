package com.mango.courses.controller;

import com.mango.courses.model.Course;
import com.mango.courses.model.Status;
import com.mango.courses.model.dto.StudentDto;
import com.mango.courses.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false) Status status) {
        return courseService.getCourses(status);
    }

    @PostMapping
    public Course addCourse(@Valid @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }


    @PostMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<?> courseEnrollment(@PathVariable String courseId, @PathVariable String studentId){
        courseService.courseEnrollment(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}/members")
    public List<StudentDto> getCourseMembers(@PathVariable String courseId){
        return courseService.getCourseMembers(courseId);
    }
}

