package liga.student.service.service;

import liga.student.service.domain.Student;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.PaginationStudentDto;
import liga.student.service.dto.Sorter;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<StudentDTO> getAll(PaginationStudentDto dto) {
        Sorter sorter = dto.getSorter();



        PageRequest pageRequest = PageRequest.of(sorter.getPage(), sorter.getSize(), sorter.getSortDirection(), sorter.getSortBy());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
        .withMatcher("id", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.NumberMatcher.BETWEEN).from(5).to(15))
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("surname", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("age", matcher -> matcher.);
        Example<Student> example = Example
                .of(Student.builder().
                        id(dto.getId()).
                        name(dto.getName()).
                        surname(dto.getSurname()).
                        age(dto.getAge()).build(), exampleMatcher);
        return mapper.studentToStudentDTO(studentRepository.findAll(example, pageRequest).getContent());
        return mapper.studentToStudentDTO(studentRepository.findAll(dto.getId()));



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
