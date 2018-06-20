package liga.student.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StudentApi<T> {
    @GetMapping(value = "/getStudentDetails/{schoolname}")
    List<T> getStudent(@PathVariable("schoolname") String schoolname);
}
