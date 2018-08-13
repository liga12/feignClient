package liga.school.sevice.controller;

import liga.school.sevice.service.SchoolService;
import liga.school.sevice.transport.dto.SchoolDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public Page<SchoolDto> getSchools(SchoolFindDto dto, Pageable pageable) {
        return schoolService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public SchoolDto getSchoolById(@PathVariable Long id) {
        return schoolService.getById(id);
    }

    @PutMapping
    public SchoolDto createSchool(@RequestBody SchoolDto dto) {
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolDto updateSchool(@RequestBody @Valid SchoolDto dto) {
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolService.remove(id);
    }
}

