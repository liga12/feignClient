package liga.student.service.service;


import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.PaginationStudentSearchTextDto;
import liga.student.service.transport.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAll(PaginationStudentSearchTextDto dto);

    List<StudentDTO> getAll(PaginationStudentDto dto);

    StudentDTO getById(String id);

    boolean existsByIds(List<String> ids);

    boolean existsById(String id);

    StudentDTO create(StudentDTO dto);

    StudentDTO update(StudentDTO dto);

    void remove(String id);
}
