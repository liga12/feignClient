package liga.school.sevice.service;

import liga.school.sevice.domain.School;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDto;
import liga.school.sevice.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper mapper;

    @Override
    public List<SchoolDto> getAll() {
        return mapper.schoolToSchoolDto( schoolRepository.findAll());
    }

    @Override
    public SchoolDto getById(Long id) {
        return mapper.schoolToSchoolDto(schoolRepository.findById(id).get());
    }

    @Override
    public List<SchoolDto> getByName(String name) {
        return mapper.schoolToSchoolDto(schoolRepository.getByName(name));
    }

    @Override
    public List<SchoolDto> getByAddress(String address) {
        return mapper.schoolToSchoolDto(schoolRepository.getByAddress(address));
    }

    @Override
    public SchoolDto create(SchoolDto dto) {
        School school = mapper.schoolDtoToSchool(dto);
        School save = schoolRepository.save(school);
        return mapper.schoolToSchoolDto(save);
    }

    @Override
    public SchoolDto update(SchoolDto dto) {
        return mapper.schoolToSchoolDto(schoolRepository.save(mapper.schoolDtoToSchool(dto)));
    }

    @Override
    public void remove(SchoolDto dto) {
        schoolRepository.delete(mapper.schoolDtoToSchool(dto));
    }
}
