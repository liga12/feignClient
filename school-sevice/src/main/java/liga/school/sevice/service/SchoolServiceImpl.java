package liga.school.sevice.service;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.transport.dto.PaginationSchoolDto;
import liga.school.sevice.transport.dto.SchoolDTO;
import liga.school.sevice.transport.dto.Sorter;
import liga.school.sevice.transport.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper mapper;
    private final StudentService studentFeignService;

    @Override
    @Transactional
    public List<SchoolDTO> getAll(PaginationSchoolDto dto) {
        Sorter sorter = dto.getSorter();
        PageRequest pageRequest = PageRequest.of(
                sorter.getPage(),
                sorter.getSize(),
                sorter.getSortDirection(),
                sorter.getSortBy()
        );
        Page<School> result = schoolRepository.findAll(
                SchoolSearchSpecification.schoolFilter(dto),
                pageRequest
        );
        return mapper.toDto(result.getContent());
    }

    @Override
    @Transactional
    public SchoolDTO getById(Long id) {
        return mapper.toDto(schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new));
    }

    @Override
    @Transactional
    public SchoolDTO create(SchoolDTO dto) {
        List<String> studentIds = dto.getStudentIds();
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
    public SchoolDTO update(SchoolDTO dto) {
        Long id = dto.getId();
        validateExistingById(id);
        List<String> studentIds = dto.getStudentIds();
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
    public void remove(Long id) {
        validateExistingById(id);
        schoolRepository.deleteById(id);
    }

    private void validateExistingById(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new SchoolNotFoundException();
        }
    }
}
