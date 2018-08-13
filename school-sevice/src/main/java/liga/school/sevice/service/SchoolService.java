package liga.school.sevice.service;

import liga.school.sevice.transport.dto.SchoolDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolService {
    Page<SchoolDto> getAll(SchoolFindDto dto, Pageable pageable);

    SchoolDto getById(Long id);

    SchoolDto create(SchoolDto dto);

    SchoolDto update(SchoolDto dto);

    void remove(Long id);
}
