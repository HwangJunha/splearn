package tobyspring.splearn.application.instructor.provided.dto;

import jakarta.validation.constraints.NotNull;

public record InstructorApplyRequest(
        @NotNull Long memberId
) {
}
