package liga.school.service.controller.handler;

import liga.school.service.exception.SchoolNotFoundException;
import liga.school.service.exception.StudentNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = SchoolNotFoundException.class)
    public String schoolNotFound(SchoolNotFoundException ex) {
        return String.format("{\"error\":\"%s\"}", ex.getMessage());
    }

    @ExceptionHandler(value = StudentNotFoundException.class)
    public String schoolNotFound(StudentNotFoundException ex) {
        return String.format("{\"error\":\"%s\"}", ex.getMessage());
    }

}
