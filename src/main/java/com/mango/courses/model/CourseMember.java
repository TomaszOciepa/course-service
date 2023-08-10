package com.mango.courses.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CourseMember {
    @NotNull
    private LocalDateTime enrollmentData;
    @NotNull
    private String email;

    public CourseMember(@NotNull String email) {
        this.enrollmentData = LocalDateTime.now();
        this.email = email;
    }

    public CourseMember() {
    }

    public LocalDateTime getEnrollmentData() {
        return enrollmentData;
    }

    public String getEmail() {
        return email;
    }
}
