package liga.student.service.service;

import liga.student.service.domain.Student;
import liga.student.service.domain.StudentDao;
import liga.student.service.dto.StudentDto;
import liga.student.service.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentDao studentDao;

    @Override
    public Map<String, List<StudentDto>> createStudentsDto() {
        Map<String, List<StudentDto>> studentsDto = new HashMap<>();
        Map<String, List<Student>> students = studentDao.getStudents();
        List<StudentDto> lst;
        for (Map.Entry<String, List<Student>> stringListEntry : students.entrySet()) {
            lst=new ArrayList<>();
            String shoolName = stringListEntry.getKey();
            List<Student> value = stringListEntry.getValue();
            for (Student student : value) {
                lst.add(studentMapper.studentToStudentDTO(student));
            }
            studentsDto.put(shoolName,lst);
        }
        return studentsDto;
    }
}
