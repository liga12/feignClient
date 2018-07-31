package liga.school.sevice.controller;

import liga.school.sevice.dto.PaginationSchoolDto;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping("/")
    public List<SchoolDTO> getSchools(@RequestBody PaginationSchoolDto dto) {
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

