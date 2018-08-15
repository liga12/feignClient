package liga.student.service.service;


import liga.student.service.transport.dto.*;

import java.util.List;
import java.util.Set;

public interface StudentService {

    List<StudentOutComeDto> getAll(StudentFindByTextSearchDto dto);

    List<StudentOutComeDto> getAll(PaginationStudentDto dto);

    StudentOutComeDto getById(String id);

    boolean existsByIds(Set<String> ids);

    boolean existsById(String id);

    StudentOutComeDto create(StudentCreatrDto dto);

    StudentOutComeDto update(StudentUpdateDto dto);

    void remove(String id);
}
