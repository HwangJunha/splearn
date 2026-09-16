package tobyspring.splearn.application.instructor.provided;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.application.instructor.provided.dto.InstructorApplyRequest;
import tobyspring.splearn.application.instructor.required.InstructorRepository;
import tobyspring.splearn.application.member.required.MemberRepository;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.instructor.InstructorStatus;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
class InstructorApplicationTest {
    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply(){
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);

        instructorRepository.findById(instructor.getId()).orElseThrow();
    }

    @Test
    void duplicateApply(){
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        assertThatThrownBy(
                () -> instructorApplication.apply(new InstructorApplyRequest(null))
        ).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void approve(){
        Instructor instructor = instructorApplication.approve(preparePendingInstructor().getId());
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);

    }

    @Test
    void reject(){
        Instructor instructor = instructorApplication.reject(preparePendingInstructor().getId());
        assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);

    }

    private Instructor preparePendingInstructor() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);
        return instructorApplication.apply(new InstructorApplyRequest(member.getId()));
    }
}