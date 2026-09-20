package tobyspring.splearn.application.course.provided;

import tobyspring.splearn.domain.course.Course;

import java.util.List;

public interface CourseFinder {
    Course find(Long courseId);

    List<Course> findByTitle(String keyword);

    List<Course> findByInstructor(Long instructorId);
}
