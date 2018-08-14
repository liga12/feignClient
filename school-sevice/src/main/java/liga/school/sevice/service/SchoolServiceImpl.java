package liga.school.sevice.service;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import liga.school.sevice.transport.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper mapper;
    private final StudentService studentFeignService;

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolOutComeDto> getAll(SchoolFindDto dto, Pageable pageable) {
        Page<School> result = schoolRepository.findAll(
                SchoolSearchSpecification.schoolFilter(dto),
                pageable
        );
        return result.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolOutComeDto getById(Long id) {
        return mapper.toDto(schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new));
    }

    @Override
    public SchoolOutComeDto create(SchoolCreateDto dto) {
        Set<String> studentIds = dto.getStudentIds();
        if (!studentFeignService.existsAllStudentsByIds(studentIds)) {
            throw new StudentNotFoundException();
        }
        return mapper.toDto(
                schoolRepository.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    public SchoolOutComeDto update(SchoolUpdateDto dto) {
        SchoolOutComeDto storedSchool = getById(dto.getId());
        Set<String> studentIds = dto.getStudentIds();
        boolean isNotEmptyStudentsIds = !CollectionUtils.isEmpty(studentIds);
        if (isNotEmptyStudentsIds && !studentFeignService.existsAllStudentsByIds(studentIds)) {
            throw new StudentNotFoundException();
        }
        storedSchool.setName(dto.getName());
        storedSchool.setAddress(dto.getAddress());
        storedSchool.getStudentIds().clear();
        if (isNotEmptyStudentsIds) {
            storedSchool.getStudentIds().addAll(dto.getStudentIds());
        }
        return mapper.toDto(
                schoolRepository.save(mapper.toEntity(storedSchool))
        );
    }

    @Override
    public void remove(Long id) {
        validateExistingById(id);
        schoolRepository.deleteById(id);
    }

    private void validateExistingById(Long id) {
        if (null==id||!schoolRepository.existsById(id)) {
            throw new SchoolNotFoundException();
        }
    }
}
