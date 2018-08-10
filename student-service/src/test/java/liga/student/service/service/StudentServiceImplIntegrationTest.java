package liga.student.service.service;

import liga.student.service.StudentClientService;
import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.PaginationStudentSearchTextDto;
import liga.student.service.transport.dto.Sorter;
import liga.student.service.transport.dto.StudentDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MongoConfig.class, StudentClientService.class})
public class StudentServiceImplIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetAllTextSearch() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n").build();

        List<StudentDTO> all = studentService.getAll(paginationSearch);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllTextSearchWithEmpty() {
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n s").build();

        List<StudentDTO> all = studentService.getAll(paginationSearch);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllTextSearchWithOneItems() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("n2").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n").build();

        List<StudentDTO> all = studentService.getAll(paginationSearch);

        assertEquals(Collections.singletonList(studentDTO), all);
    }

    @Test
    public void testGetAllTextSearchWithCaseSensitiveTrue() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("N1").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(true).text("n1").build();

        List<StudentDTO> all = studentService.getAll(paginationSearch);

        assertEquals(Collections.singletonList(studentDTO), all);
    }

    @Test
    public void testGetAllTextSearchWithTwoFields() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n s").build();

        List<StudentDTO> all = studentService.getAll(paginationSearch);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }


    @Test
    public void testGetAll() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithId() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).id(studentDTO.getId()).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.singletonList(studentDTO), all);
    }

    @Test
    public void testGetAllWithIdEmpty() {
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).id("1L").build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithName() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).name("n").build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithNameEmpty() {
        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("s").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).name("n").build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithSurname() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).surname("s").build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithSurnameEmpty() {
        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("s").surname("s").age(1).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).surname("n").build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithStartAge() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(1).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithStartAgeEmpty() {
        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(3).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithEndAge() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).endAge(5).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithEndAgeEmpty() {
        studentService.create(StudentDTO.builder().name("gg").surname("s").age(3).build());
        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).endAge(1).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithStartAgeAndEndAge() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(1).endAge(9).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
    }

    @Test
    public void testGetAllWithStartAgeAndEndAgeEmpty() {
        studentService.create(StudentDTO.builder().name("gg").surname("s").age(3).build());
        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().
                sorter(sorter).startAge(1).endAge(1).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllWithNameAndSurnameStartAgeAndEndAge() {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("qw").age(1).build());
        studentService.create(StudentDTO.builder().name("a").surname("q").age(4).build());
        studentService.create(StudentDTO.builder().name("fd").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("f").surname("s2").age(2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().
                sorter(sorter).name("n").surname("q").startAge(1).endAge(1).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.singletonList(studentDTO), all);
    }

    @Test
    public void testGetAllWithNameAndSurnameStartAgeAndEndAgeEmpty() {
        studentService.create(StudentDTO.builder().name("n").surname("qw").age(1).build());
        studentService.create(StudentDTO.builder().name("a").surname("q").age(4).build());
        studentService.create(StudentDTO.builder().name("fd").surname("s").age(1).build());
        studentService.create(StudentDTO.builder().name("f").surname("s2").age(2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().
                sorter(sorter).name("n").surname("k").startAge(1).endAge(1).build();

        List<StudentDTO> all = studentService.getAll(pagination);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetById() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());

        StudentDTO all = studentService.getById(studentDTO.getId());

        assertEquals(studentDTO, all);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testGetByIdWithStudentNotFound() {
        studentService.getById("1");
    }

    @Test
    public void testExistById() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());

        assertTrue(studentService.existsById(studentDTO.getId()));
    }

    @Test(expected = StudentNotFoundException.class)
    public void testExistByIdWithFalse() {
        studentService.existsById("1");
    }

    @Test
    public void testExistByIds() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertTrue(studentService.existsByIds(Collections.singletonList(studentDTO.getId())));
    }

    @Test
    public void testExistByIdsWithFalse() {
        List<String> ids = Collections.singletonList("1");

        boolean result = studentService.existsByIds(ids);

        assertFalse(result);
    }

    @Test
    public void testCreate() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        assertTrue(studentService.existsById(studentDTO.getId()));
    }

    @Test
    public void testUpdate() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        studentDTO.setName("n");
        studentDTO.setSurname("s");
        studentDTO.setAge(20);

        StudentDTO updatedStudentDTO = studentService.update(studentDTO);

        assertEquals(studentDTO, updatedStudentDTO);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testUpdateWithStudentNotFound() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        studentDTO.setName("n");
        studentDTO.setSurname("s");
        studentDTO.setAge(20);

        studentService.update(StudentDTO.builder().id("23").build());
    }


    @Test
    public void testRemove() {
        StudentDTO studentDTO = studentService.
                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        studentService.remove(studentDTO.getId());

        List<Student> all = studentRepository.findAll();

        assertTrue(all.isEmpty());
    }
}