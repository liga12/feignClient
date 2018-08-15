package liga.student.service.service;

import liga.student.service.transport.dto.StudentFindByTextSearchDto;
import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.transport.dto.StudentOutComeDto;
import liga.student.service.transport.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<StudentOutComeDto> getAll(StudentFindByTextSearchDto dto) {
        Sorter sorter = dto.getSorter();
        PageRequest pageRequest = PageRequest
                .of(sorter.getPage(), sorter.getSize(), sorter.getSortDirection(), sorter.getSortBy());
        List<Student> students = studentRepository.
                searchByNamesAndSurname(dto.getText(), dto.getCaseSensitive(), pageRequest);
        return mapper.toDto(students);
    }

    @Override
    public List<StudentDTO> getAll(PaginationStudentDto dto) {
        Sorter sorter = dto.getSorter();
        PageRequest pageRequest = PageRequest
                .of(sorter.getPage(), sorter.getSize(), sorter.getSortDirection(), sorter.getSortBy());
        Query query = new Query();
        query.with(pageRequest);
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(toEquals("id", dto.getId()));
        criteriaList.add(toLike("name", dto.getName()));
        criteriaList.add(toLike("surname", dto.getSurname()));
        criteriaList.add(toBetween("age", dto.getStartAge(), dto.getEndAge()));
        criteriaList.forEach(q -> {
            if (q != null) {
                query.addCriteria(q);
            }
        });
        List<Student> students = mongoTemplate.find(query, Student.class);
        return mapper.toDto(students);
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
        return mapper.toDto(studentRepository.findById(id).orElseThrow(StudentNotFoundException::new));
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
        return mapper.toDto(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        existsById(dto.getId());
        return mapper.toDto(studentRepository.save(mapper.studentDTOToStudent(dto)));
    }

    @Override
    public void remove(String id) {
        existsById(id);
        studentRepository.deleteById(id);
    }
}
