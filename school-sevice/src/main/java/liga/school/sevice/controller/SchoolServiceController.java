package liga.school.sevice.controller;

import liga.school.sevice.api.SchoolApi;
import liga.school.sevice.dto.SchoolDto;
import liga.school.sevice.mapper.SchoolMapper;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SchoolServiceController implements SchoolApi {

    @Autowired
    SchoolService schoolService;

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    StudentService studentFeingService;

    @Override
    public List<SchoolDto> getSchools() {
        return schoolService.getAll();
    }

    @Override
    public SchoolDto getSchoolById(@PathVariable Long id) {
        SchoolDto schoolDto;
        try {
            schoolDto = schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        return schoolDto;
    }

    @Override
    public List<SchoolDto> getSchoolByName(@PathVariable String name) {
        return schoolService.getByName(name);
    }

    @Override
    public List<SchoolDto> getSchoolByAddress(@PathVariable String address) {
        return schoolService.getByAddress(address);
    }

    @Override
    public SchoolDto createSchool(@RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam List<String> stIds) {

        for (String stId : stIds) {
            StudentDto studentById = studentFeingService.getStudentById(stId);
            if (null == studentById)
                return null;
        }
        SchoolDto schoolDto = schoolService.create(new SchoolDto(name, address, stIds));
        schoolService.create(schoolDto);
        return schoolDto;
    }

    @Override
    public SchoolDto updateSchool(@PathVariable Long id,
                                  @RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam List<String> stIds) {
        SchoolDto schoolDto;
        try {
            schoolDto = schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        if (null!=stIds) {
            for (String stId : stIds) {
                StudentDto studentById = studentFeingService.getStudentById(stId);
                if (null == studentById)
                    return null;
            }
        }
        schoolDto = schoolService.update(new SchoolDto(schoolDto.getId(),name, address, stIds));
        schoolService.update(schoolDto);
        return schoolDto;
    }

    @Override
    public SchoolDto deleteSchool(@PathVariable Long id) {
        SchoolDto schoolDto;
        try {
            schoolDto = schoolService.getById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
        schoolService.remove(schoolDto);
        return schoolDto;
    }


}

