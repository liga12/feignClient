package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.transport.dto.StudentFindByTextSearchDto;
import liga.student.service.transport.dto.StudentFindDto;
import liga.student.service.transport.dto.StudentOutComeDto;
import liga.student.service.transport.mapper.StudentMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetAllTextSearch() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("s")
                .surname("n")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(false)
                .build();
        List<StudentOutComeDto> students = studentMapper.toDto(Arrays.asList(student2, student));
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(students, all);
    }

    @Test
    public void testGetAllTextSearchWithEmpty() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("s")
                .surname("n")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("a")
                .caseSensitive(false)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testGetAllTextSearchWithOneItems() {
        Student student = Student.builder()
                .name("n")
                .surname("d")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutComeDto> students = studentMapper.toDto(Collections.singletonList(student));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("d")
                .caseSensitive(false)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(students, all);
    }

    @Test
    public void testGetAllTextSearchWithCaseSensitiveTrue() {
        Student student = Student.builder()
                .name("N")
                .surname("d")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutComeDto> students = studentMapper.toDto(Collections.singletonList(student2));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(true)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(students, all);
    }

    @Test
    public void testGetAllTextSearchWithTwoFields() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n")
                .surname("n")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutComeDto> students = studentMapper.toDto(Arrays.asList(student2, student));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(true)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(students, all);
    }

    @Test
    public void testGetAll() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n")
                .surname("n")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutComeDto> students = studentMapper.toDto(Arrays.asList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutComeDto> all = studentService.getAll(searchDto, pageable);

        assertEquals(students, all);
    }

//    @Test
//    public void testGetAllWithId() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).id(studentDTO.getId()).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.singletonList(studentDTO), all);
//    }
//
//    @Test
//    public void testGetAllWithIdEmpty() {
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).id("1L").build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithName() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s").age(1).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).name("n").build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
//    }
//
//    @Test
//    public void testGetAllWithNameEmpty() {
//        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("s").surname("s").age(1).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).name("n").build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithSurname() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(1).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).surname("s").build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
//    }
//
//    @Test
//    public void testGetAllWithSurnameEmpty() {
//        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("s").surname("s").age(1).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).surname("n").build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithStartAge() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(1).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
//    }
//
//    @Test
//    public void testGetAllWithStartAgeEmpty() {
//        studentService.create(StudentDTO.builder().name("gg").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(3).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithEndAge() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).endAge(5).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
//    }
//
//    @Test
//    public void testGetAllWithEndAgeEmpty() {
//        studentService.create(StudentDTO.builder().name("gg").surname("s").age(3).build());
//        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).endAge(1).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithStartAgeAndEndAge() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("s").age(1).build());
//        StudentDTO studentDTO2 = studentService.create(StudentDTO.builder().name("n1").surname("s2").age(4).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).startAge(1).endAge(9).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Arrays.asList(studentDTO, studentDTO2), all);
//    }
//
//    @Test
//    public void testGetAllWithStartAgeAndEndAgeEmpty() {
//        studentService.create(StudentDTO.builder().name("gg").surname("s").age(3).build());
//        studentService.create(StudentDTO.builder().name("s").surname("s").age(2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().
//                sorter(sorter).startAge(1).endAge(1).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetAllWithNameAndSurnameStartAgeAndEndAge() {
//        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("n").surname("qw").age(1).build());
//        studentService.create(StudentDTO.builder().name("a").surname("q").age(4).build());
//        studentService.create(StudentDTO.builder().name("fd").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("f").surname("s2").age(2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().
//                sorter(sorter).name("n").surname("q").startAge(1).endAge(1).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.singletonList(studentDTO), all);
//    }
//
//    @Test
//    public void testGetAllWithNameAndSurnameStartAgeAndEndAgeEmpty() {
//        studentService.create(StudentDTO.builder().name("n").surname("qw").age(1).build());
//        studentService.create(StudentDTO.builder().name("a").surname("q").age(4).build());
//        studentService.create(StudentDTO.builder().name("fd").surname("s").age(1).build());
//        studentService.create(StudentDTO.builder().name("f").surname("s2").age(2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().
//                sorter(sorter).name("n").surname("k").startAge(1).endAge(1).build();
//
//        List<StudentDTO> all = studentService.getAll(pagination);
//
//        assertEquals(Collections.emptyList(), all);
//    }
//
//    @Test
//    public void testGetById() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//
//        StudentDTO all = studentService.getById(studentDTO.getId());
//
//        assertEquals(studentDTO, all);
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
//
//        assertTrue(studentService.validateExistingById(studentDTO.getId()));
//    }
//
//    @Test(expected = StudentNotFoundException.class)
//    public void testExistByIdWithFalse() {
//        studentService.validateExistingById("1");
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
//    public void testCreate() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        assertTrue(studentService.validateExistingById(studentDTO.getId()));
//    }
//
//    @Test
//    public void testUpdate() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        studentDTO.setName("n");
//        studentDTO.setSurname("s");
//        studentDTO.setAge(20);
//
//        StudentDTO updatedStudentDTO = studentService.update(studentDTO);
//
//        assertEquals(studentDTO, updatedStudentDTO);
//    }
//
//    @Test(expected = StudentNotFoundException.class)
//    public void testUpdateWithStudentNotFound() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        studentDTO.setName("n");
//        studentDTO.setSurname("s");
//        studentDTO.setAge(20);
//
//        studentService.update(StudentDTO.builder().id("23").build());
//    }
//
//
//    @Test
//    public void testRemove() {
//        StudentDTO studentDTO = studentService.
//                create(StudentDTO.builder().name("name").surname("surname").age(25).build());
//        studentService.remove(studentDTO.getId());
//
//        List<Student> all = studentRepository.findAll();
//
//        assertTrue(all.isEmpty());
//    }
}