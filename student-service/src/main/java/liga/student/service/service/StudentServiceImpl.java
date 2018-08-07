package liga.student.service.service;

import liga.student.service.dto.PaginationStudentSearchTextDto;
import liga.student.service.entity.Student;
import liga.student.service.repository.StudentRepository;
import liga.student.service.dto.PaginationStudentDto;
import liga.student.service.dto.Sorter;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<StudentDTO> getAll(PaginationStudentSearchTextDto dto) {
        Sorter sorter = dto.getSorter();
        PageRequest pageRequest = PageRequest
                .of(sorter.getPage(), sorter.getSize(), sorter.getSortDirection(), sorter.getSortBy());
        return mapper.studentToStudentDTO
                (studentRepository.searchByNamesAndSurname(dto.getText(), dto.getCaseSensitive(), pageRequest));
    }

    @Override
    public List<StudentDTO> getAll(PaginationStudentDto dto) {
        Sorter sorter = dto.getSorter();
        PageRequest pageRequest = PageRequest
                .of(sorter.getPage(), sorter.getSize(), sorter.getSortDirection(), sorter.getSortBy());
        Query query = new Query();
        query.with(pageRequest);
        List<Criteria> criterias = new ArrayList<>();
        criterias.add(toEquals("id", dto.getId()));
        criterias.add(toLike("name", dto.getName()));
        criterias.add(toLike("surname", dto.getSurname()));
        criterias.add(toBetween("age", dto.getStartAge(), dto.getEndAge()));
        criterias.stream().filter(Objects::nonNull).forEach(query::addCriteria);
        List<Student> students = mongoTemplate.find(query, Student.class);
        return mapper.studentToStudentDTO(students);
    }

    private Criteria toEquals(String param, Object paramValue) {
        return param != null && paramValue != null ? Criteria.where(param).is(paramValue) : null;
    }

    private Criteria toLike(String param, String paramValue) {
        return param != null && paramValue != null ? Criteria.where(param).regex(paramValue) : null;
    }

    private Criteria toBetween(String param, Object startParamValue, Object endParamValue) {
        Criteria criteria = null;
        if (startParamValue != null && endParamValue != null) {
            criteria = Criteria.where(param).gte(startParamValue).lte(endParamValue);
        } else {
            if (startParamValue != null) {
                criteria = Criteria.where(param).gte(startParamValue);
            }
            if (endParamValue != null) {
                criteria = Criteria.where(param).lte(endParamValue);
            }
        }
        return criteria;
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
