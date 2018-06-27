package liga.student.service.service;

import liga.student.service.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAll();

    StudentDto getById(String id);

    List<StudentDto> getByName(String name);

    List<StudentDto> getBySurname(String surname);

    List<StudentDto> getByAge(int age);

    StudentDto create(StudentDto dto);

    StudentDto update(StudentDto dto);

    void remove(StudentDto dto);
}
