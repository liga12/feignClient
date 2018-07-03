package liga.student.service.service;

import liga.student.service.domain.Student;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
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
    public List<StudentDTO> getAll() {
        return mapper.studentToStudentDTO(studentRepository.findAll());
    }

    @Override
    public StudentDTO getById(String id) {
        Student student = studentRepository.findById(id).get();
        return mapper.studentToStudentDTO(student);
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
