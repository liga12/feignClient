package liga.school.sevice.service;

import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolService {
    Page<SchoolOutComeDto> getAll(SchoolFindDto dto, Pageable pageable);

    SchoolOutComeDto getById(Long id);

    SchoolOutComeDto create(SchoolCreateDto dto);

    SchoolOutComeDto update(SchoolUpdateDto dto);

    void remove(Long id);
}
