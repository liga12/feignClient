package liga.student.service.controller;

import liga.student.service.dto.PaginationStudentDto;
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

    @PostMapping("/")
    public List<StudentDTO> getStudents(@RequestBody PaginationStudentDto dto) {
        return studentService.getAll(dto);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @PutMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){
        return studentService.create(studentDTO);
    }

    @PostMapping
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.update(studentDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.remove(id);
    }
}
