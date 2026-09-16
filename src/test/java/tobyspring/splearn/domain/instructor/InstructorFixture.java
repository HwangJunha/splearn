package tobyspring.splearn.domain.instructor;

import jakarta.validation.Valid;
import tobyspring.splearn.application.instructor.provided.dto.InstructorApplyRequest;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

public class InstructorFixture {
    public static Instructor create(Member member) {
        return Instructor.apply(member);
    }

    public static Instructor createInstructor() {
        return create(MemberFixture.createActiveMember());
    }

    public static Instructor createActiveInstructor() {
        Instructor instructor = createInstructor();
        instructor.approve();
        return instructor;
    }

    public static @Valid InstructorApplyRequest createApplyRequest(Member member) {
        return new InstructorApplyRequest(member.getId());
    }
}
