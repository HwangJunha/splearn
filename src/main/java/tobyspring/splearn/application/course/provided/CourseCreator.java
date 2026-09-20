package tobyspring.splearn.application.course.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.application.course.provided.dto.CourseCreateRequest;
import tobyspring.splearn.application.course.provided.dto.CourseInfoUpdateRequest;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.support.exception.ValidationException;

/**
 * 강의를 준비하는 작업
 */
public interface CourseCreator {
    Course create(@Valid CourseCreateRequest createRequest) throws ValidationException;

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest infoUpdateRequest) throws ValidationException;
}
