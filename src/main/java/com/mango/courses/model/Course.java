package com.mango.courses.model;


import com.mango.courses.exceptions.CourseError;
import com.mango.courses.exceptions.CourseException;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Course {

    @Id
    private String id;
    @NotBlank
    private String name;
    private String descriptions;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    @NotNull
    @Min(0)
    private Long participantsLimit;
    private Long participantsNumber;
    @NotNull
    private Status status;

    private List<CourseMember> courseMembers = new ArrayList<>();


    public Course() {
    }

    public void validateCourse() {
        validateStartDate();
        validateParticipantsNumber();
        validateStatus();
    }

    public void incrementParticipantsNumber() {
        participantsNumber++;
        if (getParticipantsNumber().equals(getParticipantsLimit())) {
            setStatus(Status.FULL);
        }
    }

    private void validateStatus() {
        if (status.equals(Status.FULL) && !participantsLimit.equals(participantsNumber)) {
            throw new CourseException(CourseError.COURSE_IS_NOT_FULL);
        }
        if (status.equals(Status.ACTIVE) && participantsLimit.equals(participantsNumber)) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }

    private void validateParticipantsNumber() {
        if (participantsNumber > participantsLimit) {
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_NUMBER_IS_GREATER_THAN_PARTICIPANTS_LIMIT);
        }
    }

    private void validateStartDate() {
        if (startDate.isAfter(endDate)) {
            throw new CourseException(CourseError.COURSE_START_DATE_IS_GREATER_THAN_END_DATE);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getParticipantsLimit() {
        return participantsLimit;
    }

    public void setParticipantsLimit(Long participantsLimit) {
        this.participantsLimit = participantsLimit;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<CourseMember> getCourseMembers() {
        return courseMembers;
    }

    public void setCourseMembers(List<CourseMember> courseMembers) {
        this.courseMembers = courseMembers;
    }
}
