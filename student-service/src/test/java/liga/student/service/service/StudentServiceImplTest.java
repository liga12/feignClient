package liga.student.service.service;

import liga.student.service.MongoConfig;
import liga.student.service.StudentClientService;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, StudentClientService.class})
public class StudentServiceImplTest {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void getAll() {
        for (int i = 0; i < 10; i++) {
            StudentDTO studentDTO = StudentDTO.builder()
                    .name("n").surname("s").age(25).build();
            studentService.create(studentDTO);
        }
        assertEquals(10, studentService.getAll().size());
    }

    @Test
    public void getById() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertEquals(studentDTO, studentService.getById(studentDTO.getId()));
    }

    @Test
    public void getByName() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertEquals(Collections.singletonList(studentDTO), studentService.getByName(studentDTO.getName()));
    }

    @Test
    public void getBySurname() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertEquals(Collections.singletonList(studentDTO), studentService.getBySurname(studentDTO.getSurname()));
    }

    @Test
    public void getByAge() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertEquals(Collections.singletonList(studentDTO), studentService.getByAge(studentDTO.getAge()));
    }

    @Test
    public void create() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertEquals(studentDTO, studentService.getById(studentDTO.getId()));
    }

    @Test
    public void update() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        studentDTO.setName("n");
        studentDTO.setSurname("s");
        studentDTO.setAge(20);
        StudentDTO updatedStudentDTO = studentService.update(studentDTO);
        assertEquals(studentDTO, updatedStudentDTO);
    }

    @Test
    public void remove() {
        for (int i = 0; i < 10; i++) {
            StudentDTO studentDTO = StudentDTO.builder()
                    .name("n").surname("s").age(25).build();
            StudentDTO studentDTOCreated = studentService.create(studentDTO);
            if (i == 9)
                studentService.remove(studentDTOCreated);
        }
        assertEquals(9, studentService.getAll().size());
    }
}