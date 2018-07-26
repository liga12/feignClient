package liga.school.sevice.service;

import liga.school.sevice.domain.School;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.mapper.SchoolMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper mapper;

    @Override
    @Transactional
    public List<SchoolDTO> getAll() {
        return mapper.schoolToSchoolDto( schoolRepository.findAll());
    }

    @Override
    @Transactional
    public SchoolDTO getById(Long id) {
        return mapper.schoolToSchoolDto(schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new));
    }

    @Override
    @Transactional
    public List<SchoolDTO> getByName(String name) {
        return mapper.schoolToSchoolDto(schoolRepository.getByName(name));
    }

    @Override
    @Transactional
    public List<SchoolDTO> getByAddress(String address) {
        return mapper.schoolToSchoolDto(schoolRepository.getByAddress(address));
    }

    @Override
    @Transactional
    public SchoolDTO create(SchoolDTO dto) {
        School school = mapper.schoolDtoToSchool(dto);
        School save = schoolRepository.save(school);
        return mapper.schoolToSchoolDto(save);
    }

    @Override
    public SchoolDTO update(SchoolDTO dto) {
        return mapper.schoolToSchoolDto(schoolRepository.save(mapper.schoolDtoToSchool(dto)));
    }

    @Override
    public void remove(SchoolDTO dto) {
        schoolRepository.delete(mapper.schoolDtoToSchool(dto));
    }
}
