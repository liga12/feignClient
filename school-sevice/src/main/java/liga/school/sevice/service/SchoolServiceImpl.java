package liga.school.sevice.service;

import liga.school.sevice.domain.School;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.PaginationSchoolDto;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.dto.Sorter;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.mapper.SchoolMapper;
import liga.school.sevice.util.SchoolSearcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        int page = sorter.getPage();
        int size = sorter.getSize();
        Sort.Direction sortDirection = sorter.getSortDirection();
        String sortBy = sorter.getSortBy();
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, sortBy);
        Page<School> result = schoolRepository.findAll(SchoolSearcher.schoolFilter(dto),pageRequest);
        return mapper.toDto(result.getContent());
    }

    @Override
    @Transactional
    public SchoolDTO getById(Long id) {
        return mapper.toDto(schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new));
    }

    @Override
    public boolean existById(Long id) {
        boolean result = schoolRepository.existsById(id);
        if (!result){
            throw new SchoolNotFoundException();
        }
        return true;
    }

    @Override
    @Transactional
    public SchoolDTO create(SchoolDTO dto) {
        List<String> studentIds = dto.getStudentIds();
        if (!studentFeignService.existsStudentsByIds(studentIds))
            throw new StudentNotFoundException();
        School school = mapper.toEntity(dto);
        School save = schoolRepository.save(school);
        return mapper.toDto(save);
    }

    @Override
    public SchoolDTO update(SchoolDTO dto) {
        Long id = dto.getId();
        existById(id);
        List<String> studentIds = dto.getStudentIds();
        if (!studentFeignService.existsStudentsByIds(studentIds)) {
            throw new StudentNotFoundException();
        }
        return mapper.toDto(schoolRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void remove(Long id) {
        existById(id);
        schoolRepository.deleteById(id);
    }
}
