package liga.student.service.api;

import liga.student.service.dto.StudentDTO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/student")
public interface StudentRelationApi {
    @GetMapping("/id/{id}")
    StudentDTO getStudentById(@PathVariable("id") String id);

}
