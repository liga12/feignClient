package liga.student.service.api;

import liga.student.service.dto.StudentDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
public interface StudentMainApi {
    @GetMapping("/")
    List<StudentDto> getStudents();

    @GetMapping("/name/{name}")
    List<StudentDto> getStudentByName(@PathVariable("name") String name);

    @GetMapping("/surname/{surname}")
    List<StudentDto> getStudentBySurname(@PathVariable("surname") String surname);

    @GetMapping("/age/{age}")
    List<StudentDto> getStudentBySurname(@PathVariable("age") int age);

    @PutMapping("/")
    StudentDto createStudent(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("age") int age);

    @PostMapping("/{id}")
    StudentDto updateStudent(@PathVariable("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("age") int age);

    @DeleteMapping("/{id}")
    StudentDto deleteStudent(@PathVariable("id") String id);
}
