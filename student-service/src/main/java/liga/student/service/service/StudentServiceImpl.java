package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.transport.dto.*;
import liga.student.service.transport.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<StudentOutComeDto> getAll(StudentFindByTextSearchDto dto, Pageable pageable) {
        List<Student> students = studentRepository.
                searchByNamesAndSurname(
                        dto.getText(),
                        dto.getCaseSensitive(),
                        pageable
                );
        return mapper.toDto(students);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentOutComeDto> getAll(StudentFindDto dto, Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
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
    @Transactional(readOnly = true)
    public StudentOutComeDto getById(String id) {
        return mapper.toDto(studentRepository.findById(id).orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public StudentOutComeDto create(StudentCreatrDto dto) {
        return mapper.toDto(
                studentRepository.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    public StudentOutComeDto update(StudentUpdateDto dto) {
        StudentOutComeDto storedStudent = getById(dto.getId());
        return mapper.toDto(
                studentRepository.save(
                        mapper.toEntity(storedStudent)
                )
        );
    }

    @Override
    public void remove(String id) {
        validateExistingById(id);
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIds(Set<String> ids) {
        return ids.stream().map(studentRepository::existsById).filter(exist -> !exist).findFirst().orElse(true);
    }

    private void validateExistingById(String id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException();
        }
    }
}
