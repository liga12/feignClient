package liga.student.service.handler;

import liga.student.service.exception.StudentNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = StudentNotFoundException.class)
    public String studentNotFond(StudentNotFoundException ex) {
        return String.format("{\"error\":\"%s\"}", ex.getMessage());
    }

}
