package liga.student.service.controller;

import liga.student.service.service.StudentService;
import liga.student.service.transport.dto.*;
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
    public List<StudentOutComeDto> getStudents(StudentFindDto dto, @PageableDefault(size = 5) Pageable pageable) {
        return studentService.getAll(dto, pageable);
    }

    @GetMapping("/textSearch")
    public List<StudentOutComeDto> getStudentsTextSearch(StudentFindByTextSearchDto dto,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        return studentService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public StudentOutComeDto getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @PutMapping
    public StudentOutComeDto createStudent(@RequestBody StudentCreateDto dto) {
        return studentService.create(dto);
    }

    @PostMapping
    public StudentOutComeDto updateStudent(@RequestBody StudentUpdateDto dto) {
        return studentService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.remove(id);
    }
}
