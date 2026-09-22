package tobyspring.splearn.application.enrollment.required;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tobyspring.splearn.domain.course.Course;
import tobyspring.splearn.domain.enrollment.Enrollment;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.support.test.BaseRepositoryTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RequiredArgsConstructor
class EnrollmentRepositoryTest extends BaseRepositoryTest {
    final EnrollmentRepository enrollmentRepository;


    @Test
    void saveAndFindId(){
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();

        Enrollment enrollment = Enrollment.enroll(member, course);

        enrollment = enrollmentRepository.save(enrollment);

        assertThat(enrollment.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Enrollment found = enrollmentRepository.findById(enrollment.getId()).orElseThrow();

        assertThat(found).isEqualTo(enrollment);
    }

    @Test
    void findByMemberId(){
        Member member1 = prepareActiveMember();
        Member member2 = prepareActiveMember();

        Enrollment enrollment1_1 = prepareEnrollment(member1, preparePublishedCourse());
        Enrollment enrollment1_2 = prepareEnrollment(member1, preparePublishedCourse());
        Enrollment enrollment2 = prepareEnrollment(member2, preparePublishedCourse());

        List<Enrollment> enrollmentList1 = enrollmentRepository.findByMemberId(member1.getId());
        assertThat(enrollmentList1).hasSize(2).containsExactly(enrollment1_1, enrollment1_2);

        List<Enrollment> enrollmentList2 = enrollmentRepository.findByMemberId(member2.getId());
        assertThat(enrollmentList2).hasSize(1).containsExactly(enrollment2);
    }

    @Test
    void findByMemberIdAndCourseId(){
        Member member1 = prepareActiveMember();
        Member member2 = prepareActiveMember();

        Course course1 = preparePublishedCourse();
        Course course2 = preparePublishedCourse();

        Enrollment enrollment1 = prepareEnrollment(member1, course1);
        Enrollment enrollment2 = prepareEnrollment(member2, course2);

        assertThat(enrollmentRepository.findByMemberIdAndCourseId(member1.getId(), course1.getId()).orElseThrow())
                .isEqualTo(enrollment1);

        assertThat(enrollmentRepository.findByMemberIdAndCourseId(member2.getId(), course2.getId()).orElseThrow())
                .isEqualTo(enrollment2);

        assertThat(enrollmentRepository.findByMemberIdAndCourseId(member1.getId(), course2.getId()).isPresent())
                .isFalse();
    }

}