package liga.student.service.api;

import liga.student.service.dto.StudentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
public interface StudentRelationApi {
    @GetMapping("/id/{id}")
    StudentDTO getStudentById(@PathVariable("id") String id);

}
