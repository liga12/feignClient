package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDTO;
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
        StudentDTO studentDTO = mapper.studentToStudentDTO(student);
        studentData(student, studentDTO);
    }

    @Test
    public void studentToStudentDto1() {
        Student student = new Student("12", "name", "surname", 23);
        Student student2 = new Student("122", "name2", "surname2", 22);
        List<Student> students = new ArrayList<>(Arrays.asList(student, student2));
        List<StudentDTO> studentDTOS = mapper.studentToStudentDTO(students);
        for (int i = 0; i < studentDTOS.size(); i++) {
            Student currentStudent = students.get(i);
            StudentDTO studentDTO = studentDTOS.get(i);
            studentData(currentStudent, studentDTO);
        }
    }

    private void studentData(Student student, StudentDTO studentDTO){
        assertEquals(student.getId(), studentDTO.getId());
        assertEquals(student.getName(), studentDTO.getName());
        assertEquals(student.getSurname(), studentDTO.getSurname());
        assertEquals(student.getAge(), studentDTO.getAge());
    }

    @Test
    public void studentDtoToStudent() {
        StudentDTO studentDTO = new StudentDTO("12", "name", "surname", 23);
        Student student = mapper.studentDTOToStudent(studentDTO);
        studentData(student, studentDTO);
    }
}