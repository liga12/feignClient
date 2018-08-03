package liga.student.service.service;


import liga.student.service.dto.StudentDTO;
import liga.student.service.dto.PaginationStudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAll(PaginationStudentDto dto);

    StudentDTO getById(String id);

    boolean existsByIds(List<String> ids);

    boolean existsById(String id);

    StudentDTO create(StudentDTO dto);

    StudentDTO update(StudentDTO dto);

    void remove(String id);
}
