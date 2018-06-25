package liga.school.sevice.service;

import liga.school.sevice.dto.SchoolDto;

import java.util.List;

public interface SchoolService {    List<SchoolDto> getAll();

    SchoolDto getById(Long id);

    List<SchoolDto> getByName(String name);

    List<SchoolDto> getByAddress(String address);

    SchoolDto create(SchoolDto dto);

    SchoolDto update(SchoolDto dto);

    void remove(SchoolDto dto);
}
