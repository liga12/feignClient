package liga.student.service.api;

import liga.student.service.dto.StudentDTO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/student-api")
public interface StudentApi {
    @GetMapping("/{id}")
    StudentDTO getStudentById(@PathVariable("id") String id);

}
