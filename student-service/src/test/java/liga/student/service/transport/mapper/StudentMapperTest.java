package liga.student.service.transport.mapper;

import liga.student.service.domain.entity.Student;
import liga.student.service.transport.dto.StudentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper mapper;

    private Student student;

    @Before
    public void setUp() {
        student = new Student("12", "name", "surname", 23);
    }

    @Test
    public void studentToStudentDto() {
        checkStudentData(student, mapper.toDto(student));
    }

    @Test
    public void studentToStudentDto1() {
        Student student2 = new Student("122", "name2", "surname2", 22);
        List<Student> students = new ArrayList<>(Arrays.asList(student, student2));
        List<StudentDTO> studentDTOS = mapper.toDto(students);
        for (int i = 0; i < studentDTOS.size(); i++) {
            checkStudentData(students.get(i), studentDTOS.get(i));
        }
    }

    @Test
    public void studentDtoToStudent() {
        StudentDTO studentDTO = new StudentDTO("12", "name", "surname", 23);
        checkStudentData(mapper.studentDTOToStudent(studentDTO), studentDTO);
    }

    private void checkStudentData(Student student, StudentDTO studentDTO) {
        assertEquals(student.getId(), studentDTO.getId());
        assertEquals(student.getName(), studentDTO.getName());
        assertEquals(student.getSurname(), studentDTO.getSurname());
        assertEquals(student.getAge(), studentDTO.getAge());
    }
}