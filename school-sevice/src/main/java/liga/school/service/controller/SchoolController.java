package liga.school.service.controller;

import liga.school.service.service.SchoolService;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
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
    public Page<SchoolOutcomeDto> getSchools(SchoolFindDto dto, @PageableDefault(size = 5) Pageable pageable) {
        return schoolService.getAll(dto, pageable);
    }

    @GetMapping("/{id}")
    public SchoolOutcomeDto getSchoolById(@PathVariable Long id) {
        return schoolService.getById(id);
    }

    @PutMapping
    public SchoolOutcomeDto createSchool(@RequestBody @Valid SchoolCreateDto dto) {
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolOutcomeDto updateSchool(@RequestBody @Valid SchoolUpdateDto dto) {
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolService.remove(id);
    }
}

