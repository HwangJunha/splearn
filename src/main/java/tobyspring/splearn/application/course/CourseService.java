package tobyspring.splearn.application.course;

import lombok.RequiredArgsConstructor;
import tobyspring.splearn.application.course.provided.CourseFinder;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.support.stereotype.ApplicationService;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseService implements CourseFinder {
    final CourseRepository courseRepository;


    @Override
    public Course find(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalArgumentException("강의를 찾을 수 없습니다. ID: " + courseId)
        );
    }

    @Override
    public List<Course> findByTitle(String keyword) {
        return List.of();
    }

    @Override
    public List<Course> findByInstructor(Long instructorId) {
        return List.of();
    }
}
