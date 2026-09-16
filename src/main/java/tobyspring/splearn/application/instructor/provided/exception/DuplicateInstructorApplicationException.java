package tobyspring.splearn.application.instructor.provided.exception;

public class DuplicateInstructorApplicationException extends RuntimeException {
    public DuplicateInstructorApplicationException(){

    }

    public DuplicateInstructorApplicationException(String message){
        super(message);
    }
}
