package liga.student.service.controller;

import liga.student.service.api.StudentRelationApi;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentRelationController implements StudentRelationApi {

    @Autowired
    StudentService studentService;

    @Override
    public StudentDTO getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }
}
