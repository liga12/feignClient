package liga.student.service.controller;

import liga.student.service.api.StudentRelationApi;
import liga.student.service.dto.StudentDto;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class StudentRelationController implements StudentRelationApi {

    @Autowired
    StudentService studentService;

    @Override
    public StudentDto getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }
}
