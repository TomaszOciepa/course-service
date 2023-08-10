package com.mango.courses.service;

import com.mango.courses.exceptions.CourseError;
import com.mango.courses.exceptions.CourseException;
import com.mango.courses.model.Course;
import com.mango.courses.model.CourseMember;
import com.mango.courses.model.Status;
import com.mango.courses.repository.CourseRepository;
import com.mango.courses.model.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
    }

    @Override
    public List<Course> getCourses(Status status) {
        if (status != null) {
            return courseRepository.findAllByStatus(status);
        }
        return courseRepository.findAll();
    }

    @Override
    public Course getCourse(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        if (courseRepository.existsByName(course.getName())) {
            throw new CourseException(CourseError.COURSE_NAME_ALREADY_EXISTS);
        }
        course.validateCourse();
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        courseRepository.deleteById(id);
    }

    @Override
    public Course putCourse(String id, Course course) {
        course.validateCourse();
        return courseRepository.findById(id)
                .map(courseFromDb -> {
                    if (courseFromDb.getName().equals(course.getName())
                            && courseRepository.existsByName(course.getName())) {
                        throw new CourseException(CourseError.COURSE_NAME_ALREADY_EXISTS);
                    }
                    courseFromDb.setName(course.getName());
                    courseFromDb.setDescriptions(course.getDescriptions());
                    courseFromDb.setStartDate(course.getStartDate());
                    courseFromDb.setEndDate(course.getEndDate());
                    courseFromDb.setStatus(course.getStatus());
                    courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    return courseRepository.save(courseFromDb);
                }).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course patchCourse(String id, Course course) {
        course.validateCourse();
        return courseRepository.findById(id)
                .map((courseFromDb -> {
                    if (!course.getName().isEmpty()) {
                        courseFromDb.setName(course.getName());
                    }
                    if (!course.getDescriptions().isEmpty()) {
                        courseFromDb.setDescriptions(course.getDescriptions());
                    }
                    if (course.getStartDate() != null) {
                        courseFromDb.setStartDate(course.getStartDate());
                    }
                    if (course.getEndDate() != null) {
                        courseFromDb.setEndDate(course.getEndDate());
                    }
                    if (course.getParticipantsLimit() != null) {
                        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
                    }
                    if (course.getParticipantsNumber() != null) {
                        courseFromDb.setParticipantsNumber(course.getParticipantsNumber());
                    }
                    if (course.getStatus() != null) {
                        courseFromDb.setStatus(course.getStatus());
                    }
                    return courseRepository.save(courseFromDb);
                })).orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public void courseEnrollment(String courseId, String studentId) {
        Course course = getCourse(courseId);
        validateCourseStatus(course);

        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrollment(course, studentDto);
        course.incrementParticipantsNumber();
        course.getCourseMembers().add(new CourseMember(studentDto.getEmail()));
        courseRepository.save(course);
    }

    @Override
    public List<StudentDto> getCourseMembers(String courseId) {
        Course course = getCourse(courseId);
        List<String> emailsMembers = course.getCourseMembers().stream()
                .map(CourseMember::getEmail).collect(Collectors.toList());
        return studentServiceClient.getStudentsByEmails(emailsMembers);
    }

    private void validateStudentBeforeCourseEnrollment(Course course, StudentDto studentDto) {
        if (!Status.ACTIVE.equals(studentDto.getStatus())) {
            throw new CourseException(CourseError.STUDENT_IS_NOT_ACTIVE);
        }
        if (course.getCourseMembers()
                .stream()
                .anyMatch(member -> studentDto.getEmail().equals(member.getEmail()))) {

            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private void validateCourseStatus(Course course) {
        if (!Status.ACTIVE.equals(course.getStatus())) {
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }

}
