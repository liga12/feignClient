package liga.school.sevice.controller;

import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    private final StudentService studentFeignService;

    @GetMapping
    public List<SchoolDTO> getSchools() {
        return schoolService.getAll();
    }

    @GetMapping("/{id}")
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        return schoolService.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<SchoolDTO> getSchoolByName(@PathVariable String name) {
        return schoolService.getByName(name);
    }

    @GetMapping("/address/{address}")
    public List<SchoolDTO> getSchoolByAddress(@PathVariable String address) {
        return schoolService.getByAddress(address);
    }

    @PutMapping
    public SchoolDTO createSchool(@RequestBody SchoolDTO dto) {
        if (!studentFeignService.getStudentById(dto.getStudentIds()))
            throw new StudentNotFoundException();
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolDTO updateSchool(@RequestBody SchoolDTO dto) {
        schoolService.getById(dto.getId());
        if (!studentFeignService.getStudentById(dto.getStudentIds()))
            throw new StudentNotFoundException();
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public SchoolDTO deleteSchool(@PathVariable Long id) {
        SchoolDTO schoolDTO = schoolService.getById(id);
        schoolService.remove(schoolDTO);
        return schoolDTO;
    }
}

