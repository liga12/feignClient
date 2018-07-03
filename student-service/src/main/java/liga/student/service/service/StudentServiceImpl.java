package liga.student.service.service;

import liga.student.service.domain.Student;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDto;
import liga.student.service.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper mapper;

    @Override
    public List<StudentDto> getAll() {
        return mapper.studentToStudentDto(studentRepository.findAll());
    }

    @Override
    public StudentDto getById(String id) {
        Student student = studentRepository.findById(id).get();
        return mapper.studentToStudentDto(student);
    }

    @Override
    public List<StudentDto> getByName(String name) {
        return mapper.studentToStudentDto(studentRepository.findByName(name));
    }

    @Override
    public List<StudentDto> getBySurname(String surname) {
        return mapper.studentToStudentDto(studentRepository.findBySurname(surname));
    }

    @Override
    public List<StudentDto> getByAge(int age) {
        return mapper.studentToStudentDto(studentRepository.findByAge(age));
    }

    @Override
    public StudentDto create(StudentDto dto) {
        return mapper.studentToStudentDto(studentRepository.save(mapper.studentDtoToStudent(dto)));
    }

    @Override
    public StudentDto update(StudentDto dto) {
        return mapper.studentToStudentDto(studentRepository.save(mapper.studentDtoToStudent(dto)));
    }

    @Override
    public void remove(StudentDto dto) {
        studentRepository.delete(mapper.studentDtoToStudent(dto));
    }
}
