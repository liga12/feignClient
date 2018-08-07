package liga.student.service.service;

import liga.student.service.StudentClientService;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, StudentClientService.class})
public class StudentServiceImplIntegrationTest {

//    @Autowired
//    private StudentService studentService;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Before
//    public void setUp() {
//        studentRepository.deleteAll();
//    }
//
//    @Test
//    public void testGetAll() {
//        for (int i = 0; i < 10; i++) {
//            StudentDTO studentDTO = StudentDTO.builder()
//                    .name("n").surname("s").age(25).build();
//            studentService.create(studentDTO);
//        }
//        assertEquals(10, studentService.getAll().size());
//    }
//
//    @Test
//    public void testGetAllWithNull() {
//        assertEquals(0, studentService.getAll().size());
//    }
//
//    @Test
//    public void testGetById() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(studentDTO, studentService.getById(studentDTO.getId()));
//    }
//
//    @Test(expected = StudentNotFoundException.class)
//    public void testGetByIdWithStudentNotFound() {
//        studentService.getById("1");
//    }
//
//    @Test
//    public void testExistById() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertTrue(studentService.existsById(studentDTO.getId()));
//    }
//
//    @Test
//    public void testExistByIdWithFalse() {
//        assertFalse(studentService.existsById("1"));
//    }
//
//    @Test
//    public void testExistByIds() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertTrue(studentService.existsByIds(Collections.singletonList(studentDTO.getId())));
//    }
//
//    @Test
//    public void testExistByIdsWithFalse() {
//        List<String> ids = Collections.singletonList("1");
//
//        boolean result = studentService.existsByIds(ids);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void testGetByName() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.singletonList(studentDTO), studentService.getByName(studentDTO.getName()));
//    }
//
//    @Test
//    public void testGetByNameWithNull() {
//        studentService.create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.emptyList(), studentService.getByName("name2"));
//    }
//
//    @Test
//    public void testGetBySurname() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.singletonList(studentDTO), studentService.getBySurname(studentDTO.getSurname()));
//    }
//
//    @Test
//    public void testGetBySurnameWithNull() {
//        studentService.create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.emptyList(), studentService.getBySurname("s2"));
//    }
//
//    @Test
//    public void testGetByAge() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.singletonList(studentDTO), studentService.getByAge(studentDTO.getAge()));
//    }
//
//    @Test
//    public void testGetByAgeWithNull() {
//        studentService.create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertEquals(Collections.emptyList(), studentService.getByAge(20));
//    }
//
//    @Test
//    public void testCreate() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertTrue(studentService.existsById(studentDTO.getId()));
//    }
//
//    @Test
//    public void testUpdate() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        studentDTO.setName("n");
//        studentDTO.setSurname("s");
//        studentDTO.setAge(20);
//        StudentDTO updatedStudentDTO = studentService.update(studentDTO);
//        assertEquals(studentDTO, updatedStudentDTO);
//    }
//
//    @Test
//    public void testRemove() {
//        for (int i = 0; i < 10; i++) {
//            StudentDTO studentDTO = StudentDTO.builder()
//                    .name("n").surname("s").age(25).build();
//            StudentDTO studentDTO1 = studentService.create(studentDTO);
//            if (i == 9)
//                studentService.remove(studentDTO1);
//        }
//        assertEquals(9, studentService.getAll().size());
//    }
}