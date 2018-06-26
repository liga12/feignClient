package liga.student.service.controller;

import liga.student.service.api.StudentApi;
import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import liga.student.service.mapper.StudentMapperT;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/student")
public class StudentController implements StudentApi {

    @Autowired
    private StudentService studentService;

    @Override
    @GetMapping("/")
    public ResponseEntity getStudents() {
        List<StudentDto> studentDto = studentService.getAll();
        return studentDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseEntity getStudentById(@PathVariable("id") String id) {
        StudentDto studentDto;
        try {
            studentDto = studentService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @Override
    public ResponseEntity getStudentByName(@PathVariable("name") String name) {
        List<StudentDto> studentDto = studentService.getByName(name);

        return studentDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/surname/{surname}")
    @Override
    public ResponseEntity getStudentBySurname(@PathVariable("surname") String surname) {
        List<StudentDto> studentDto = studentService.getBySurname(surname);
        return studentDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping("/age/{age}")
    @Override
    public ResponseEntity getStudentBySurname(@PathVariable("age") int age) {
        List<StudentDto> studentDto = studentService.getByAge(age);
        return studentDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PutMapping("/")
    @Override
    public StudentDto createStudent(@RequestParam("name") String name,
                                    @RequestParam("surname") String surname,
                                    @RequestParam("age") int age) {
        return studentService.create(new StudentDto(name, surname, age));
    }

    @PostMapping("/{id}")
    @Override
    public ResponseEntity updateStudent(@PathVariable("id") String id,
                                        @RequestParam("name") String name,
                                        @RequestParam("surname") String surname,
                                        @RequestParam("age") int age) {

        try {
            studentService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(studentService.update
                (StudentDto.builder().id(id).name(name).surname(surname).age(age).build()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity deleteStudent(@PathVariable("id") String id) {
        StudentDto studentDto;
        try {
            studentDto = studentService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        studentService.remove(studentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
