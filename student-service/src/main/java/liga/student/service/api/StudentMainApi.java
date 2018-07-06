package liga.student.service.api;

import liga.student.service.dto.StudentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
public interface StudentMainApi {
    @GetMapping("/")
    List<StudentDTO> getStudents();

    @GetMapping("/name/{name}")
    List<StudentDTO> getStudentByName(@PathVariable("name") String name);

    @GetMapping("/surname/{surname}")
    List<StudentDTO> getStudentBySurname(@PathVariable("surname") String surname);

    @GetMapping("/age/{age}")
    List<StudentDTO> getStudentByAge(@PathVariable("age") int age);

    @PutMapping("/")
    StudentDTO createStudent(@RequestBody StudentDTO studentDTO);

    @PostMapping("/")
    StudentDTO updateStudent( @RequestBody StudentDTO studentDTO);

    @DeleteMapping("/{id}")
    StudentDTO deleteStudent(@PathVariable("id") String id);
}
