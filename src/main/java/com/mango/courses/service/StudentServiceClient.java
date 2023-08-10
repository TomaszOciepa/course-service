package com.mango.courses.service;

import com.mango.courses.model.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient{

    @GetMapping("/student/{id}")
    Student getStudentById(@PathVariable String id);
}