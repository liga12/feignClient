package liga.school.sevice.controller.handler;

import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
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
