package liga.student.service.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping("/student-api")
public interface StudentApi {
    @PostMapping
    Boolean existsAllStudentsByIds(@RequestBody Set<String> ids);

}
