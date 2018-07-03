package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper mapper;

    @Test
    public void studentToStudentDto() {
        Student student = new Student("12", "name", "surname", 23);
        StudentDto studentDto = mapper.studentToStudentDto(student);
        studentData(student,studentDto);
    }

    @Test
    public void studentToStudentDto1() {
        Student student = new Student("12", "name", "surname", 23);
        Student student2 = new Student("122", "name2", "surname2", 22);
        List<Student> students = new ArrayList<>(Arrays.asList(student, student2));
        List<StudentDto> studentDtos = mapper.studentToStudentDto(students);
        for (int i = 0; i < studentDtos.size(); i++) {
            Student currentStudent = students.get(i);
            StudentDto studentDto = studentDtos.get(i);
            studentData(currentStudent,studentDto);
        }
    }

    private void studentData(Student student, StudentDto studentDto){
        assertEquals(student.getId(), studentDto.getId());
        assertEquals(student.getName(), studentDto.getName());
        assertEquals(student.getSurname(), studentDto.getSurname());
        assertEquals(student.getAge(), studentDto.getAge());
    }

    @Test
    public void studentDtoToStudent() {
        StudentDto studentDto = new StudentDto("12", "name", "surname", 23);
        Student student = mapper.studentDtoToStudent(studentDto);
        studentData(student,studentDto);
    }
}