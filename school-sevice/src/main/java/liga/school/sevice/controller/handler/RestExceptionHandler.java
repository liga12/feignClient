package liga.school.sevice.controller.handler;

import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = SchoolNotFoundException.class)
    public String schoolNotFond(SchoolNotFoundException ex) {
        return String.format("{\"error\":\"%s\"}", ex.getMessage());
    }

    @ExceptionHandler(value = StudentNotFoundException.class)
    public String schoolNotFond(StudentNotFoundException ex) {
        return String.format("{\"error\":\"%s\"}", ex.getMessage());
    }

    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public String entityNotFound(InvalidDataAccessApiUsageException ex) {
        return String.format("{\"error\":\"%s\"}", "Entity not found");
    }

}
