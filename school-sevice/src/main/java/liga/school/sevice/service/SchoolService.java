package liga.school.sevice.service;

import liga.school.sevice.dto.SchoolDTO;

import java.util.List;

public interface SchoolService {

    List<SchoolDTO> getAll();

    SchoolDTO getById(Long id);

    List<SchoolDTO> getByName(String name);

    List<SchoolDTO> getByAddress(String address);

    SchoolDTO create(SchoolDTO dto);

    SchoolDTO update(SchoolDTO dto);

    void remove(SchoolDTO dto);
}
