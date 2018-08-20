package liga.school.service.service;

import liga.school.service.domain.entity.School;
import liga.school.service.domain.repository.SchoolRepository;
import liga.school.service.exception.SchoolNotFoundException;
import liga.school.service.exception.StudentNotFoundException;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
import liga.school.service.transport.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    public Page<SchoolOutcomeDto> getAll(SchoolFindDto dto, Pageable pageable) {
        Page<School> result = schoolRepository.findAll(
                SchoolSearchSpecification.schoolFilter(dto),
                pageable
        );
        return result.map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolOutcomeDto getById(Long id) {
        return mapper.toDto(
                schoolRepository
                        .findById(id)
                        .orElseThrow(SchoolNotFoundException::new)
        );
    }

    @Override
    public SchoolOutcomeDto create(SchoolCreateDto dto) {
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
    public SchoolOutcomeDto update(SchoolUpdateDto dto) {
        SchoolOutcomeDto storedSchool = getById(dto.getId());
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
        if (null == id || !schoolRepository.existsById(id)) {
            throw new SchoolNotFoundException();
        }
    }
}
