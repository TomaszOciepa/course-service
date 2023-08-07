package com.mango.courses.service;

import com.mango.courses.model.Course;
import com.mango.courses.model.Status;

import java.util.List;

public interface CourseService {

    List<Course> getCourses(Status status);
    Course getCourse(String id);
    Course addCourse(Course course);
    void deleteCourse(String id);
    Course putCourse(String id, Course course);
    Course patchCourse(String id, Course course);
}
