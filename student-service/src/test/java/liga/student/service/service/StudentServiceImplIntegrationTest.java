package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.transport.dto.*;
import liga.student.service.transport.mapper.StudentMapper;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static liga.student.service.asserts.Asserts.assertEqualStudentAndDto;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    StudentMapper mapper;

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
        List<Student> students = studentRepository.findAll();
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(false)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);
        Collections.reverse(result);

        assertEqualStudentAndDto(mapper.toDto(students), result);
    }

    @Test
    public void testGetAllTextSearchWithEmpty() {
        Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student.builder()
                .name("s")
                .surname("n")
                .age(1)
                .build();
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("a")
                .caseSensitive(false)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(Lists.emptyList(), result);
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
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("d")
                .caseSensitive(false)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
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
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student2));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(true)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
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
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student2, student));
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .caseSensitive(true)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
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
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllById() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student saveStudent = studentRepository.save(student);
        StudentFindDto searchDto = StudentFindDto.builder()
                .id(saveStudent.getId())
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(mapper.toDto(
                Lists.newArrayList(
                        saveStudent)
                ),
                result);
    }

    @Test
    public void testGetAllWithIdEmpty() {
        StudentFindDto searchDto = StudentFindDto.builder()
                .id("1")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(Lists.newArrayList(), result);
    }

    @Test
    public void testGetAllWithName() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n1")
                .surname("n")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .name("n")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithNameEmpty() {
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList());
        StudentFindDto searchDto = StudentFindDto.builder()
                .name("n")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithSurname() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n1")
                .surname("ns")
                .age(1)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .surname("s")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithSurnameEmpty() {
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList());
        StudentFindDto searchDto = StudentFindDto.builder()
                .surname("n")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithStartAge() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n1")
                .surname("ns")
                .age(2)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .startAge(1)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithStartAgeEmpty() {
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList());
        StudentFindDto searchDto = StudentFindDto.builder()
                .startAge(1)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithEndAge() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n1")
                .surname("ns")
                .age(2)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .endAge(2)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithEndAgeEmpty() {
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList());
        StudentFindDto searchDto = StudentFindDto.builder()
                .endAge(1)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithStartAgeAndEndAge() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student student2 = Student.builder()
                .name("n1")
                .surname("ns")
                .age(2)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student, student2));
        StudentFindDto searchDto = StudentFindDto.builder()
                .startAge(1)
                .endAge(2)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetAllWithStartAgeAndEndAgeEmpty() {
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList());
        StudentFindDto searchDto = StudentFindDto.builder()
                .startAge(1)
                .endAge(2)
                .build();
        PageRequest pageable = PageRequest.of(0, 10);

        List<StudentOutcomeDto> result = studentService.getAll(searchDto, pageable);

        assertEqualStudentAndDto(students, result);
    }

    @Test
    public void testGetById() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student save = studentRepository.save(student);
        List<StudentOutcomeDto> students = studentMapper.toDto(Lists.newArrayList(student));

        StudentOutcomeDto result = studentService.getById(save.getId());

        assertEqualStudentAndDto(students, Lists.newArrayList(result));
    }

    @Test(expected = StudentNotFoundException.class)
    public void testGetByIdWithStudentNotFound() {
        studentService.getById("1");
    }

    @Test
    public void testExistByIds() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student save = studentRepository.save(student);
        Student student1 = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student save1 = studentRepository.save(student1);
        Set<String> ids = Sets.newLinkedHashSet(save.getId(), save1.getId());

        boolean result = studentService.existsByIds(ids);

        assertTrue(result);
    }

    @Test
    public void testExistByIdsWithFalse() {
        boolean result = studentService.existsByIds(Sets.newLinkedHashSet("1"));

        assertFalse(result);
    }

    @Test
    public void testCreate() {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();

        StudentOutcomeDto result = studentService.create(studentCreateDto);

        assertEquals(studentService.getById(result.getId()), result );
    }

    @Test
    public void testUpdate() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        Student save = studentRepository.save(student);
        StudentUpdateDto updateDto = StudentUpdateDto.builder()
                .id(save.getId())
                .name("n")
                .surname("ss")
                .age(1)
                .build();

        StudentOutcomeDto result = studentService.update(updateDto);

        assertEquals(studentService.getById(result.getId()), result);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testUpdateWithStudentNotFound() {
        StudentUpdateDto updateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("ss")
                .age(1)
                .build();

        studentService.update(updateDto);
    }

    @Test
    public void testRemove() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        String id = studentRepository.save(student).getId();

        studentService.remove(id);

        assertFalse(studentRepository.existsById(id));
    }

    @Test(expected = StudentNotFoundException.class)
    public void testRemoveWithStudentNotFound() {

        studentService.remove("1");
    }
}