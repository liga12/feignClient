package liga.school.sevice.controller;

import liga.school.sevice.service.SchoolService;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public Page<SchoolOutComeDto> getSchools(SchoolFindDto dto, @PageableDefault(size = 5) Pageable pageable) {
        return schoolService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public SchoolOutComeDto getSchoolById(@PathVariable Long id) {
        return schoolService.getById(id);
    }

    @PutMapping
    public SchoolOutComeDto createSchool(@RequestBody @Valid SchoolCreateDto dto) {
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolOutComeDto updateSchool(@RequestBody @Valid SchoolUpdateDto dto) {
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolService.remove(id);
    }
}

