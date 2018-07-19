package liga.student.service.controller;

import liga.student.service.api.StudentApi;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentApiController implements StudentApi {

    private final StudentService studentService;

    @Autowired
    public StudentApiController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public StudentDTO getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }
}
