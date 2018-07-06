package liga.school.sevice.controller;

import liga.school.sevice.api.SchoolApi;
import liga.school.sevice.dto.SchoolDto;
import liga.school.sevice.mapper.SchoolMapper;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SchoolServiceController implements SchoolApi {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentFeingService;

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
    public SchoolDto createSchool(@RequestBody SchoolDto dto) {

        for (String stId : dto.getStudentIds()) {
            studentFeingService.getStudentById(stId);
        }
        return schoolService.create(dto);
    }

    @Override
    public SchoolDto updateSchool(@RequestBody SchoolDto dto) {
        schoolService.getById(dto.getId());
        if (null != dto.getStudentIds()) {
            for (String stId : dto.getStudentIds()) {
                StudentDTO studentById = studentFeingService.getStudentById(stId);
                if (null == studentById)
                    return null;
            }
        }
        return  schoolService.update(dto);
    }

    @Override
    public SchoolDto deleteSchool(@PathVariable Long id) {
        SchoolDto schoolDto = schoolService.getById(id);
        schoolService.remove(schoolDto);
        return schoolDto;
    }


}

