package com.mango.courses.service;

import com.mango.courses.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient{

    @GetMapping("/student/{id}")
    StudentDto getStudentById(@PathVariable String id);

    @PostMapping("/student/emails")
    List<StudentDto> getStudentsByEmails(@RequestBody List<String> emails);
}