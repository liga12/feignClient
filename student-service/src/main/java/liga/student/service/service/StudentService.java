package liga.student.service.service;


import liga.student.service.transport.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface StudentService {

    List<StudentOutComeDto> getAll(StudentFindByTextSearchDto dto, Pageable pageable);

    List<StudentOutComeDto> getAll(StudentFindDto dto, Pageable pageable);

    StudentOutComeDto getById(String id);

    boolean existsByIds(Set<String> ids);

    StudentOutComeDto create(StudentCreatrDto dto);

    StudentOutComeDto update(StudentUpdateDto dto);

    void remove(String id);
}
