package tobyspring.splearn.application.enrollment.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.application.enrollment.provided.dto.EnrollRequest;
import tobyspring.splearn.domain.enrollment.Enrollment;

public interface Enroller {
    Enrollment enroll(@Valid EnrollRequest enrollRequest);

    Enrollment startStudying(Long enrollmentId);

    Enrollment complete(Long enrollmentId);
}
