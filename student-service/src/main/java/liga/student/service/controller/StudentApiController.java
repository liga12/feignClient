package liga.student.service.controller;

import liga.student.service.api.StudentApi;
import liga.student.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class StudentApiController implements StudentApi {

    private final StudentService studentService;

    @Override
    public Boolean existsAllStudentsByIds(@RequestBody Set<String> ids) {
        return studentService.existsByIds(ids);
    }
}
