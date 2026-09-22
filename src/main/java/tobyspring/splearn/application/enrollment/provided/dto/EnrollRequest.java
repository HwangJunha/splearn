package tobyspring.splearn.application.enrollment.provided.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;


public record EnrollRequest(
        @NotNull Long memberId,
        @NotNull Long courseId
) {
}
