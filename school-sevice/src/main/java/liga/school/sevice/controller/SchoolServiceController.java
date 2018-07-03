package liga.school.sevice.controller;

import liga.school.sevice.api.SchoolApi;
import liga.school.sevice.dto.SchoolDto;
import liga.school.sevice.mapper.SchoolMapper;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return schoolService.getById(id);
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
            studentFeingService.getStudentById(stId);
        }
        return schoolService.create(schoolService.create(new SchoolDto(name, address, stIds)));
    }

    @Override
    public SchoolDto updateSchool(@PathVariable Long id,
                                  @RequestParam String name,
                                  @RequestParam String address,
                                  @RequestParam List<String> stIds) {
        SchoolDto schoolDto;
            schoolDto = schoolService.getById(id);
        if (null != stIds) {
            for (String stId : stIds) {
                StudentDTO studentById = studentFeingService.getStudentById(stId);
                if (null == studentById)
                    return null;
            }
        }
        schoolDto = schoolService.update(new SchoolDto(schoolDto.getId(), name, address, stIds));
        schoolService.update(schoolDto);
        return schoolDto;
    }

    @Override
    public SchoolDto deleteSchool(@PathVariable Long id) {
        SchoolDto schoolDto = schoolService.getById(id);
        schoolService.remove(schoolDto);
        return schoolDto;
    }


}

