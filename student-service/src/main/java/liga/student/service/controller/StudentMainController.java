package liga.student.service.controller;

import liga.student.service.api.StudentMainApi;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return  studentService.getBySurname(surname);
    }

    @Override
    public List<StudentDTO> getStudentByAge(@PathVariable int age) {
        return studentService.getByAge(age);
    }

    @Override
    public StudentDTO createStudent(@RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam int age) {
        return studentService.create(new StudentDTO(name, surname, age));
    }

     @Override
    public StudentDTO updateStudent(@PathVariable String id,
                                    @RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam int age) {
        try {
            studentService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        return StudentDTO.builder().id(id).name(name).surname(surname).age(age).build();
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
