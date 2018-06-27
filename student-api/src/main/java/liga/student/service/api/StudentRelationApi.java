package liga.student.service.api;

import liga.student.service.dto.StudentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
public interface StudentRelationApi {
    @GetMapping("/id/{id}")
    StudentDto getStudentById(@PathVariable("id") String id);

}
