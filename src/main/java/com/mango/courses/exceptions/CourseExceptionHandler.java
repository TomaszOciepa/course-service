package com.mango.courses.exceptions;

import feign.FeignException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorInfo> handleException(CourseException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (CourseError.COURSE_NOT_FOUND.equals(e.getCourseError())) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (CourseError.COURSE_NAME_ALREADY_EXISTS.equals(e.getCourseError())) {
            httpStatus = HttpStatus.CONFLICT;
        } else if (CourseError.COURSE_START_DATE_IS_GREATER_THAN_END_DATE.equals(e.getCourseError())
                || CourseError.COURSE_IS_NOT_ACTIVE.equals(e.getCourseError())
                || CourseError.STUDENT_IS_NOT_ACTIVE.equals(e.getCourseError())) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (CourseError.COURSE_PARTICIPANTS_NUMBER_IS_GREATER_THAN_PARTICIPANTS_LIMIT.equals(e.getCourseError())
                || CourseError.COURSE_IS_NOT_FULL.equals(e.getCourseError())
                || CourseError.STUDENT_ALREADY_ENROLLED.equals(e.getCourseError())) {
            httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?>handleFeignException(FeignException e){
        return ResponseEntity.status(e.status())
                .body(new JSONObject(e.contentUTF8()).toMap());
    }
}
