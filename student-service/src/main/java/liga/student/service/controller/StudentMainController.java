package liga.student.service.controller;

import liga.student.service.api.StudentMainApi;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentMainController implements StudentMainApi {

    private final StudentService studentService;

    @Autowired
    public StudentMainController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public List<StudentDTO> getStudents() {
        return studentService.getAll();
    }

    @Override
    public List<StudentDTO> getStudentByName(@PathVariable String name) {
        return studentService.getByName(name);
    }

    @Override
    public List<StudentDTO> getStudentBySurname(@PathVariable String surname) {
        return studentService.getBySurname(surname);
    }

    @Override
    public List<StudentDTO> getStudentByAge(@PathVariable int age) {
        return studentService.getByAge(age);
    }

    @Override
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @Override
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {

        try {
            studentService.getById(studentDTO.getId());
        } catch (NoSuchElementException e) {
            return null;
        }
        return studentService.update(studentDTO);
    }

    @Override
    public StudentDTO deleteStudent(@PathVariable String id) {
        StudentDTO studentDTO;
        try {
            studentDTO = studentService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        studentService.remove(studentDTO);
        return studentDTO;
    }
}
