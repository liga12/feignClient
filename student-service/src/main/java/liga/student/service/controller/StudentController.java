package liga.student.service.controller;

import liga.student.service.service.StudentService;
import liga.student.service.transport.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentOutcomeDto> getStudents(@Valid StudentFindDto dto, @PageableDefault(size = 5) Pageable pageable) {
        return studentService.getAll(dto, pageable);
    }

    @GetMapping("/textSearch")
    public List<StudentOutcomeDto> getStudentsTextSearch(@Valid StudentFindByTextSearchDto dto,
                                                         @PageableDefault(size = 5) Pageable pageable) {
        return studentService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public StudentOutcomeDto getStudentById(@PathVariable String id) {
        return studentService.getById(id);
    }

    @PutMapping
    public StudentOutcomeDto createStudent(@RequestBody @Valid StudentCreateDto dto) {
        return studentService.create(dto);
    }

    @PostMapping
    public StudentOutcomeDto updateStudent(@RequestBody @Valid StudentUpdateDto dto) {
        return studentService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.remove(id);
    }
}
