package liga.student.service.service;

import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    @Override
    public List<StudentDTO> getAll() {
        return mapper.studentToStudentDTO(studentRepository.findAll());
    }

    @Override
    public StudentDTO getById(String id) {
        return mapper.studentToStudentDTO(
                studentRepository.findById(id).orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public List<StudentDTO> getByName(String name) {
        return mapper.studentToStudentDTO(studentRepository.findByName(name));
    }

    @Override
    public List<StudentDTO> getBySurname(String surname) {
        return mapper.studentToStudentDTO(studentRepository.findBySurname(surname));
    }

    @Override
    public List<StudentDTO> getByAge(int age) {
        return mapper.studentToStudentDTO(studentRepository.findByAge(age));
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        return mapper.studentToStudentDTO(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        return mapper.studentToStudentDTO(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public void remove(StudentDTO dto) {
        studentRepository.delete(mapper.studentDTOToStudent(dto));
    }
}
