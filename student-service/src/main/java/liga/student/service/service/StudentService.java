package liga.student.service.service;

import liga.student.service.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO> getAll();

    StudentDTO getById(String id);

    boolean existsByIds(List<String> ids);

    boolean existsById(String id);

    List<StudentDTO> getByName(String name);

    List<StudentDTO> getBySurname(String surname);

    List<StudentDTO> getByAge(int age);

    StudentDTO create(StudentDTO dto);

    StudentDTO update(StudentDTO dto);

    void remove(StudentDTO dto);
}
