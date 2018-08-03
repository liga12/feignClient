package liga.student.service.service;

import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.PaginationStudentDto;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;

    @Override
    public List<StudentDTO> getAll(PaginationStudentDto dto) {
        return mapper.studentToStudentDTO(studentRepository.findAll());
    }


    @Override
    public StudentDTO getById(String id) {
        return mapper.studentToStudentDTO(studentRepository.findById(id).orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public boolean existsById(String id) {
        boolean result = studentRepository.existsById(id);

        if (!result) {
            throw new StudentNotFoundException();
        }
        return true;
    }

    @Override
    public boolean existsByIds(List<String> ids) {
        return ids.stream().map(studentRepository::existsById).filter(exist -> !exist).findFirst().orElse(true);
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        return mapper.studentToStudentDTO(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        existsById(dto.getId());
        return mapper.studentToStudentDTO(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public void remove(String id) {
        existsById(id);
        studentRepository.deleteById(id);
    }
}
