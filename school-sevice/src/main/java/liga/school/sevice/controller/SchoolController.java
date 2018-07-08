package liga.school.sevice.controller;

import liga.school.sevice.api.SchoolApi;
import liga.school.sevice.domain.School;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SchoolController implements SchoolApi {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private StudentService studentFeingService;

    @Override
    public List<SchoolDTO> getSchools() {
        return schoolService.getAll();
    }

    @Override
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        SchoolDTO schoolDTO;
        try {
            schoolDTO = schoolService.getById(id);
        }catch (NoSuchElementException e){
            return null;
        }
        return schoolDTO;
    }

    @Override
    public List<SchoolDTO> getSchoolByName(@PathVariable String name) {
        return schoolService.getByName(name);
    }

    @Override
    public List<SchoolDTO> getSchoolByAddress(@PathVariable String address) {
        return schoolService.getByAddress(address);
    }

    @Override
    public SchoolDTO createSchool(@RequestBody SchoolDTO dto) {
        if (null != dto.getStudentIds())
            for (String stId : dto.getStudentIds()) {
                StudentDTO studentById = studentFeingService.getStudentById(stId);
                if (null==studentById)
                    return null;
            }
        return schoolService.create(dto);
    }

    @Override
    public SchoolDTO updateSchool(@RequestBody SchoolDTO dto) {
        schoolService.getById(dto.getId());
        if (null != dto.getStudentIds()) {
            for (String stId : dto.getStudentIds()) {
                StudentDTO studentById = studentFeingService.getStudentById(stId);
                if (null == studentById)
                    return null;
            }
        }
        return schoolService.update(dto);
    }

    @Override
    public SchoolDTO deleteSchool(@PathVariable Long id) {
        SchoolDTO schoolDTO = schoolService.getById(id);
        schoolService.remove(schoolDTO);
        return schoolDTO;
    }


}

