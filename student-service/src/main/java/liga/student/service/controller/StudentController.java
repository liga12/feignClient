package liga.student.service.controller;

import liga.student.service.api.StudentApi;
import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import liga.student.service.mapper.StudentMapper;
import liga.student.service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController implements StudentApi {

    @Autowired
    StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping(value = "/getStudentDetails/{schoolname}")
    public List<StudentDto> getStudent(@PathVariable("schoolname") String schoolname) {
        System.out.println("Getting CreatorStudent details for " + schoolname);
        List<StudentDto> studentList = studentService.createStudentsDto().get(schoolname);
        if (studentList == null) {
            studentList = new ArrayList<>();
            Student std = new Student("Not Found", "N/A");
            studentList.add(studentMapper.studentToStudentDTO(std));
        }
        return studentList;
    }
}
