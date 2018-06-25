package liga.student.service.api;

import liga.student.service.dto.StudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

public interface StudentApi<T> {
    @GetMapping("/")
    ResponseEntity getStudents();

    @GetMapping("/id/{id}")
    ResponseEntity getStudentById(@PathVariable("id") String id) throws NoSuchElementException;

    @GetMapping("/name/{name}")
    ResponseEntity getStudentByName(@PathVariable("name") String name);

    @GetMapping("/surname/{surname}")
    ResponseEntity getStudentBySurname(@PathVariable("surname") String surname);

    @GetMapping("/age/{age}")
    ResponseEntity getStudentBySurname(@PathVariable("age") int age);

    @PutMapping("/")
    StudentDto createStudent(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("age") int age);

    @PostMapping("/{id}")
    ResponseEntity updateStudent(@PathVariable("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("age") int age) throws NoSuchElementException;

    @DeleteMapping("/{id}")
    ResponseEntity deleteStudent(@PathVariable("id") String id) throws NoSuchElementException;
}
