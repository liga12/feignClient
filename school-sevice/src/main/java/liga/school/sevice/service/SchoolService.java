package liga.school.sevice.service;

import liga.school.sevice.transport.dto.PaginationSchoolDto;
import liga.school.sevice.transport.dto.SchoolDTO;

import java.util.List;

public interface SchoolService {

    List<SchoolDTO> getAll(PaginationSchoolDto dto);

    SchoolDTO getById(Long id);

    boolean existById(Long id);

    SchoolDTO create(SchoolDTO dto);

    SchoolDTO update(SchoolDTO dto);

    void remove(Long id);
}
