package tobyspring.splearn.application.course.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tobyspring.splearn.application.course.provided.dto.CourseCreateRequest;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.course.CourseFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseCreatorTest extends BaseApplicationServiceTest {
    final CourseCreator courseCreator;
    @Autowired
    private CourseFinder courseFinder;

    @Test
    void create(){
        prepareInstructor();
        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));
        assertThat(course.getId()).isNotNull();
    }

    @Test
    void updateInfo(){
        prepareInstructor();
        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        Course updated = courseCreator.updateInfo(course.getId(), CourseFixture.createCourseInfoUpdateRequest("Updated"));
        assertThat(updated.getTitle()).isEqualTo("Updated");
    }

}