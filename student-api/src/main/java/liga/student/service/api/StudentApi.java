package liga.student.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student-api")
public interface StudentApi {
    @GetMapping("/{id}")
    String getStudentById(@PathVariable("id") String id);

}
