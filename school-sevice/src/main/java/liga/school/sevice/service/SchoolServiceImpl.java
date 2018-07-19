package liga.school.sevice.service;

import liga.school.sevice.domain.School;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper mapper;

    @Override
    @Transactional
    public List<SchoolDTO> getAll() {
        return mapper.schoolToSchoolDTO( schoolRepository.findAll());
    }

    @Override
    @Transactional
    public SchoolDTO getById(Long id) {
        return mapper.schoolToSchoolDTO(schoolRepository.findById(id).orElseThrow(SchoolNotFoundException::new));
    }

    @Override
    @Transactional
    public List<SchoolDTO> getByName(String name) {
        return mapper.schoolToSchoolDTO(schoolRepository.getByName(name));
    }

    @Override
    @Transactional
    public List<SchoolDTO> getByAddress(String address) {
        return mapper.schoolToSchoolDTO(schoolRepository.getByAddress(address));
    }

    @Override
    @Transactional
    public SchoolDTO create(SchoolDTO dto) {
        School school = mapper.schoolDTO_ToSchool(dto);
        School save = schoolRepository.save(school);
        return mapper.schoolToSchoolDTO(save);
    }

    @Override
    public SchoolDTO update(SchoolDTO dto) {
        return mapper.schoolToSchoolDTO(schoolRepository.save(mapper.schoolDTO_ToSchool(dto)));
    }

    @Override
    public void remove(SchoolDTO dto) {
        schoolRepository.delete(mapper.schoolDTO_ToSchool(dto));
    }
}
