package liga.student.service.service;

import liga.student.service.dto.StudentDto;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Map<String, List<StudentDto>> createStudentsDto();
}
