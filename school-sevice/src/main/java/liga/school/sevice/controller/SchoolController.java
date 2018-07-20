package liga.school.sevice.controller;

import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    private final StudentService studentFeingService;

    @Autowired
    public SchoolController(SchoolService schoolService, StudentService studentFeingService) {
        this.schoolService = schoolService;
        this.studentFeingService = studentFeingService;
    }

    @GetMapping("/")
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
        for (String stId : dto.getStudentIds()) {
            studentFeingService.getStudentById(stId);
        }
        return schoolService.create(dto);
    }

    @PostMapping
    public SchoolDTO updateSchool(@RequestBody SchoolDTO dto) {
        schoolService.getById(dto.getId());
        for (String stId : dto.getStudentIds()) {
            studentFeingService.getStudentById(stId);
        }
        return schoolService.update(dto);
    }

    @DeleteMapping("/{id}")
    public SchoolDTO deleteSchool(@PathVariable Long id) {
        SchoolDTO schoolDTO = schoolService.getById(id);
        schoolService.remove(schoolDTO);
        return schoolDTO;
    }
}

