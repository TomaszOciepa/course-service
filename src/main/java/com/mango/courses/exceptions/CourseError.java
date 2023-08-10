package com.mango.courses.exceptions;

public enum CourseError {

    COURSE_NOT_FOUND("Course does not exists"),
    COURSE_NAME_ALREADY_EXISTS("Course name already exists"),
    COURSE_START_DATE_IS_GREATER_THAN_END_DATE("Course start date is greater than end date"),
    COURSE_PARTICIPANTS_NUMBER_IS_GREATER_THAN_PARTICIPANTS_LIMIT("Course participants number is greater than participants limit"),
    COURSE_IS_NOT_FULL("Course participants number is lower than participants limit"),
    COURSE_IS_NOT_ACTIVE("Course is not ACTIVE"),
    STUDENT_IS_NOT_ACTIVE("Student is not ACTIVE"),
    STUDENT_ALREADY_ENROLLED("Student already enrolled on this course"),
    STUDENT_CANNOT_BE_ENROLL("Student cannot be enroll on course.");

    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
