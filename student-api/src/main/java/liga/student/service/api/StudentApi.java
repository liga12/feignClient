package liga.student.service.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student-api")
public interface StudentApi {
    @PostMapping
    Boolean existsAllStudentsByIds(@RequestBody List<String> ids);

}
