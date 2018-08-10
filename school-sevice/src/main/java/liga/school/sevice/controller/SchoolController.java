package liga.school.sevice.controller;

import liga.school.sevice.transport.dto.PaginationSchoolDto;
import liga.school.sevice.transport.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public List<SchoolDTO> getSchools(PaginationSchoolDto dto) {
        return schoolService.getAll(dto);
    }

    @GetMapping("/{id}")
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        return schoolService.getById(id);
    }

    @PutMapping
    public SchoolDTO createSchool(@RequestBody SchoolDTO dto) {
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolDTO updateSchool(@RequestBody SchoolDTO dto) {
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable Long id) {
        schoolService.remove(id);
    }
}

