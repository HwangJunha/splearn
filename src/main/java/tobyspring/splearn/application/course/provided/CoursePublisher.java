package tobyspring.splearn.application.course.provided;

import tobyspring.splearn.domain.course.Course;

/**
 * 강의 공개와 관련된 작업
 */
public interface CoursePublisher {
    Course submitForReview(Long courseId);

    Course publish(Long courseId);

    Course archive(Long courseId);
}
