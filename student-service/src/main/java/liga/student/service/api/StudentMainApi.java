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
    List<StudentDTO> getStudentBySurname(@PathVariable("age") int age);

    @PutMapping("/")
    StudentDTO createStudent(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("age") int age);

    @PostMapping("/{id}")
    StudentDTO updateStudent(@PathVariable("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("age") int age);

    @DeleteMapping("/{id}")
    StudentDTO deleteStudent(@PathVariable("id") String id);
}
