package liga.student.service.transport.mapper;

import liga.student.service.domain.entity.Student;
import liga.student.service.transport.dto.StudentCreateDto;
import liga.student.service.transport.dto.StudentOutComeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentMapperTest {

    @Autowired
    private StudentMapper mapper;

    @Test
    public void testStudentToStudentOutComeDto() {
        Student student = Student.builder()
                .id("1")
                .name("name")
                .surname("surname")
                .age(20)
                .build();

        StudentOutComeDto studentOutComeDto = mapper.toDto(student);

        assertEquals(student.getId(), studentOutComeDto.getId());
        assertEquals(student.getName(), studentOutComeDto.getName());
        assertEquals(student.getSurname(), studentOutComeDto.getSurname());
        assertEquals(student.getAge(), studentOutComeDto.getAge());
    }

    @Test
    public void testStudentsToStudentOutComeDtos() {
        Student student = Student.builder()
                .id("1")
                .name("name")
                .surname("surname")
                .age(20)
                .build();
        List<Student> students = Arrays.asList(student, student);

        List<StudentOutComeDto> studentOutComeDtos = mapper.toDto(students);

        for (StudentOutComeDto studentOutComeDto : studentOutComeDtos) {
            assertEquals(student.getId(), studentOutComeDto.getId());
            assertEquals(student.getName(), studentOutComeDto.getName());
            assertEquals(student.getSurname(), studentOutComeDto.getSurname());
            assertEquals(student.getAge(), studentOutComeDto.getAge());
        }
    }

    @Test
    public void testStudentCreateDtoToStudent() {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("name")
                .surname("surname")
                .age(20)
                .build();

        Student student = mapper.toEntity(studentCreateDto);

        assertEquals(student.getName(), studentCreateDto.getName());
        assertEquals(student.getSurname(), studentCreateDto.getSurname());
        assertEquals(student.getAge(), studentCreateDto.getAge());
    }

    @Test
    public void testStudentOutComeDtoToStudent() {
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .name("name")
                .surname("surname")
                .age(20)
                .build();

        Student student = mapper.toEntity(studentOutComeDto);

        assertEquals(student.getId(), studentOutComeDto.getId());
        assertEquals(student.getName(), studentOutComeDto.getName());
        assertEquals(student.getSurname(), studentOutComeDto.getSurname());
        assertEquals(student.getAge(), studentOutComeDto.getAge());
    }
}