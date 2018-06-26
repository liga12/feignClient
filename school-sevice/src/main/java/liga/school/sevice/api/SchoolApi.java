package liga.school.sevice.api;

import liga.school.sevice.domain.Student;
import liga.school.sevice.dto.SchoolDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface SchoolApi {

    @GetMapping("/") ResponseEntity getSchools();

    @GetMapping("/id/{id}")ResponseEntity getSchoolById(@PathVariable("id") Long id);

    @GetMapping("/name/{name}")ResponseEntity getSchoolByName(@PathVariable("name") String name);

    @GetMapping("/address/{address}")ResponseEntity getSchoolByAddress(@PathVariable("address") String address);

    @PutMapping("/")SchoolDto createSchool(@RequestParam("name") String name,
                                  @RequestParam("address") String address);

    @PostMapping("/{id}")
    ResponseEntity updateSchool(@PathVariable("id") Long id,
                                @RequestParam("name") String name,
                                @RequestParam("address") String address);

    @DeleteMapping("/{id}")ResponseEntity deleteSchool(@PathVariable("id") Long id);
}
