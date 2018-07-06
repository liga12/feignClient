package liga.school.sevice.api;

import liga.school.sevice.dto.SchoolDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/school")
public interface SchoolApi {

    @GetMapping("/")
    List<SchoolDto> getSchools();

    @GetMapping("/id/{id}")
    SchoolDto getSchoolById(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")
    List<SchoolDto> getSchoolByName(@PathVariable("name") String name);

    @GetMapping("/address/{address}")
    List<SchoolDto> getSchoolByAddress(@PathVariable("address") String address);

    @PutMapping("/")
    SchoolDto createSchool(@RequestBody SchoolDto dto);

    @PostMapping("/")
    SchoolDto updateSchool(@RequestBody SchoolDto dto);

    @DeleteMapping("/{id}")
    SchoolDto deleteSchool(@PathVariable("id") Long id);
}
