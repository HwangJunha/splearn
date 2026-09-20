package tobyspring.splearn.application.course.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.application.course.provided.dto.CourseCreateRequest;
import tobyspring.splearn.application.course.required.CourseRepository;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.course.CourseFixture;
import tobyspring.splearn.support.exception.ValidationException;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseValidatorTest extends BaseApplicationServiceTest {
    final CourseValidator courseValidator;
    final CourseRepository courseRepository;

    @Test
    void titleDuplicationForCreate() {
        var instructor1 = prepareInstructor();

        var instructor2 = prepareInstructor();

        Course course1 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Spring"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "Clean Code"));

        //instructor1 중복되지 않는 제목 - OK
        courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Spring 7", null));
        //instructor1 중복 제목 - FAIL
        assertThatThrownBy(() ->
                courseValidator.validateForCreate(instructor1, new CourseCreateRequest(instructor1.getId(), "Clean Spring", null)))
                .isInstanceOfSatisfying(ValidationException.class, e -> {
                    assertThat(e.getErrors()).hasSize(1);
                });

        //instructor2 1과 중복되는 제목 - OK
        courseValidator.validateForCreate(instructor2, new CourseCreateRequest(instructor2.getId(), "Clean Spring", null));
    }

    @Test
    void titleDuplicationForUpdate() {
        var instructor1 = prepareInstructor();
        var instructor2 = prepareInstructor();

        Course course1_1 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Spring"));
        Course course1_2 = courseRepository.save(CourseFixture.createCourse(instructor1, "Clean Code"));
        Course course2 = courseRepository.save(CourseFixture.createCourse(instructor2, "Clean Spring"));

        courseValidator.validateForUpdate(course1_1, CourseFixture.createCourseInfoUpdateRequest(course1_1.getTitle()));

        assertThatThrownBy(() ->
                courseValidator.validateForUpdate(course1_1, CourseFixture.createCourseInfoUpdateRequest(course1_2.getTitle())))
                .isInstanceOfSatisfying(ValidationException.class, e->{
                    assertThat(e.getErrors()).hasSize(1);
                });
    }

}