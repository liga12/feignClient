package liga.student.service.controller;

import liga.student.service.transport.dto.PaginationStudentSearchTextDto;
import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.StudentDTO;
import liga.student.service.service.StudentService;
import liga.student.service.transport.dto.StudentOutComeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentOutComeDto> getStudents(@PageableDefault(size = 5) Pageable pageable) {
        return studentService.getAll(dto);
    }

    @PostMapping("/textSearch")
    public List<StudentDTO> getStudentsTextSearch(@RequestBody PaginationStudentSearchTextDto dto) {
        return studentService.getAll(dto);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @PutMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
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
