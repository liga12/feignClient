package liga.student.service.controller;

import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<StudentDTO> getStudentByName(@PathVariable String name) {
        return studentService.getByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<StudentDTO> getStudentBySurname(@PathVariable String surname) {
        return studentService.getBySurname(surname);
    }

    @GetMapping("/age/{age}")
    public List<StudentDTO> getStudentByAge(@PathVariable int age) {
        return studentService.getByAge(age);
    }

    @PutMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.create(studentDTO);
    }

    @PostMapping
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {
        studentService.getById(studentDTO.getId());
        return studentService.update(studentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        StudentDTO studentDTO = studentService.getById(id);
        studentService.remove(studentDTO);
    }
}
