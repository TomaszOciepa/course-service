package com.mango.courses.repository;

import com.mango.courses.model.Course;
import com.mango.courses.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findAllByStatus(Status status);
    boolean existsByName(String name);
}
