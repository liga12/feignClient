package liga.school.sevice.api;

import liga.school.sevice.dto.SchoolDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/school")
public interface SchoolApi {

    @GetMapping("/")
    List<SchoolDTO> getSchools();

    @GetMapping("/id/{id}")
    SchoolDTO getSchoolById(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")
    List<SchoolDTO> getSchoolByName(@PathVariable("name") String name);

    @GetMapping("/address/{address}")
    List<SchoolDTO> getSchoolByAddress(@PathVariable("address") String address);

    @PutMapping("/")
    SchoolDTO createSchool(@RequestBody SchoolDTO dto);

    @PostMapping("/")
    SchoolDTO updateSchool(@RequestBody SchoolDTO dto);

    @DeleteMapping("/{id}")
    SchoolDTO deleteSchool(@PathVariable("id") Long id);
}
