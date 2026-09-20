package tobyspring.splearn.application.course.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.course.CourseStatus;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class CoursePublisherTest extends BaseApplicationServiceTest {
    final CoursePublisher coursePublisher;

    @BeforeEach
    void setUp() {
        prepareCourse();
    }

    @Test
    void submitForReview() {
        var courseForReview = coursePublisher.submitForReview(course.getId());
        assertThat(courseForReview.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);
    }

    @Test
    void publish() {
        coursePublisher.submitForReview(course.getId());
        var courseForPublish = coursePublisher.publish(course.getId());
        assertThat(courseForPublish.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
    }

    @Test
    void archive() {
        coursePublisher.submitForReview(course.getId());
        coursePublisher.publish(course.getId());
        var courseForArchive = coursePublisher.archive(course.getId());
        assertThat(courseForArchive.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
    }
}