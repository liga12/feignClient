package liga.student.service.controller;

import liga.student.service.api.StudentMainApi;
import liga.student.service.dto.StudentDto;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentMainController implements StudentMainApi {

    @Autowired
    private StudentService studentService;

    @Override
    public List<StudentDto> getStudents() {
        return studentService.getAll();
    }

    @Override
    public List<StudentDto> getStudentByName(@PathVariable String name) {
        return studentService.getByName(name);
    }

    @Override
    public List<StudentDto> getStudentBySurname(@PathVariable String surname) {
        return  studentService.getBySurname(surname);
    }

    @Override
    public List<StudentDto> getStudentBySurname(@PathVariable int age) {
        return studentService.getByAge(age);
    }

    @Override
    public StudentDto createStudent(@RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam int age) {
        return studentService.create(new StudentDto(name, surname, age));
    }

     @Override
    public StudentDto updateStudent(@PathVariable String id,
                                        @RequestParam String name,
                                        @RequestParam String surname,
                                        @RequestParam int age) {
        try {
            studentService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        return StudentDto.builder().id(id).name(name).surname(surname).age(age).build();
    }

    @Override
    public StudentDto deleteStudent(@PathVariable String id) {
        StudentDto studentDto;
        try {
            studentDto = studentService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        studentService.remove(studentDto);
        return studentDto;
    }
}
