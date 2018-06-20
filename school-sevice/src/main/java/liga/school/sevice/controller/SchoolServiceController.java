package liga.school.sevice.controller;

import liga.school.sevice.service.StudentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolServiceController {

    @Autowired
    private StudentFeignService studentFeignService;

    @GetMapping("/getSchoolDetails/{schoolname}")
    public String getStudents(@PathVariable("schoolname") String schoolname) {
        List student = studentFeignService.getStudent(schoolname);
        System.out.println(student);

        return "School Name -  " + schoolname + " \n Student Details " + student;
    }
}

