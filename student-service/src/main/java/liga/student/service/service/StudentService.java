package liga.student.service.service;


import liga.student.service.transport.dto.*;

import java.util.List;

public interface StudentService {

    List<StudentOutComeDto> getAll(PaginationStudentSearchTextDto dto);

    List<StudentOutComeDto> getAll(PaginationStudentDto dto);

    StudentOutComeDto getById(String id);

    boolean existsByIds(List<String> ids);

    boolean existsById(String id);

    StudentOutComeDto create(StudentCreatrDto dto);

    StudentOutComeDto update(StudentUpdateDto dto);

    void remove(String id);
}
