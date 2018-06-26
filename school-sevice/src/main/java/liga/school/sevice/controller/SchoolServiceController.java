package liga.school.sevice.controller;

import liga.school.sevice.api.SchoolApi;
import liga.school.sevice.domain.Student;
import liga.school.sevice.dto.SchoolDto;
import liga.school.sevice.mapper.SchoolMapper;
import liga.school.sevice.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/school")
public class SchoolServiceController implements SchoolApi {

    @Autowired
    SchoolService schoolService;

    @Autowired
    SchoolMapper schoolMapper;

    @GetMapping("/")
    @Override
    public ResponseEntity getSchools() {
        List<SchoolDto> schoolDto = schoolService.getAll();
        System.out.println();
        return schoolDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(schoolDto, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseEntity getSchoolById(@PathVariable("id") Long id) {
        SchoolDto schoolDto;
        try {
            schoolDto = schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(schoolDto, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @Override
    public ResponseEntity getSchoolByName(@PathVariable("name") String name) {
        List<SchoolDto> schoolDto = schoolService.getByName(name);

        return schoolDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(schoolDto, HttpStatus.OK);
    }

    @GetMapping("/address/{address}")
    @Override
    public ResponseEntity getSchoolByAddress(@PathVariable("address") String address) {
        List<SchoolDto> schoolDto = schoolService.getByAddress(address);
        return schoolDto.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(schoolDto, HttpStatus.OK);
    }

    @PutMapping("/")
    @Override
    public SchoolDto createSchool(@RequestParam("name") String name,
                                  @RequestParam("address") String address) {
        return schoolService.create(new SchoolDto(name, address));
    }

    @PostMapping("/{id}")
    @Override
    public ResponseEntity updateSchool(@PathVariable("id") Long id,
                                       @RequestParam("name") String name,
                                       @RequestParam("address") String address) {

        try {
            schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(schoolService.update
                (new SchoolDto(id, name, address)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity deleteSchool(@PathVariable("id") Long id) {
        SchoolDto studentDto;
        try {
            studentDto = schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        schoolService.remove(studentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

