package tobyspring.splearn.application.instructor.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import tobyspring.splearn.application.instructor.provided.dto.InstructorApplyRequest;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;
import tobyspring.splearn.support.test.BaseApplicationServiceTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ApplicationServiceTest
@RequiredArgsConstructor
class InstructorFinderTest extends BaseApplicationServiceTest {
    final InstructorFinder instructorFinder;
    final InstructorApplication instructorApplication;

    @Test
    void findByMember(){
        prepareMember();

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        Instructor found = instructorFinder.findByMember(member.getId()).orElseThrow();

        assertThat(instructor).isEqualTo(found);

        assertThat(instructorFinder.findByMember(Long.MAX_VALUE).isPresent()).isFalse();
    }

}