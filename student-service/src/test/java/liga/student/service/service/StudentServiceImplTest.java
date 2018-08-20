package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.transport.dto.*;
import liga.student.service.transport.mapper.StudentMapper;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StudentServiceImplTest {

    @Spy
    @InjectMocks
    private StudentServiceImpl studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper mapper;
    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void testGetAllTextSearch() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutcomeDto studentOutComeDto = StudentOutcomeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        List<Student> students = Lists.newArrayList(student);
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("n")
                .build();
        PageRequest pageable = PageRequest.of(0, 10);
        when(studentRepository.
                searchByNamesAndSurname(
                        eq("n"),
                        anyBoolean(),
                        any(Pageable.class)
                )
        ).thenReturn(students);
        List<StudentOutcomeDto> studentOutcomeDtos = Lists.newArrayList(studentOutComeDto);
        when(mapper.toDto(students)).thenReturn(studentOutcomeDtos);

        List<StudentOutcomeDto> result = studentService.getAll(
                searchDto,
                pageable
        );

        verify(studentRepository, times(1))
                .searchByNamesAndSurname(
                        eq("n"),
                        anyBoolean(),
                        any(Pageable.class));
        verify(mapper, times(1)).toDto(students);
        assertEquals(studentOutcomeDtos, result);
    }

    @Test
    public void testGetAll() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutcomeDto studentOutComeDto = StudentOutcomeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        when(mongoTemplate.find(any(Query.class), any())).thenReturn(Lists.newArrayList(student));
        when(mapper.toDto(Lists.newArrayList(student))).thenReturn(Lists.newArrayList(studentOutComeDto));

        List<StudentOutcomeDto> result = studentService.getAll(StudentFindDto.builder().build(), PageRequest.of(0,1));

        verify(mongoTemplate, times(1)).
                find(any(Query.class), any());
        verify(mapper, times(1)).toDto(Lists.newArrayList(student));
        assertEquals(Lists.newArrayList(studentOutComeDto), result);
    }


    @Test
    public void testGetById() {
        String id = "1";
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutcomeDto studentOutComeDto = StudentOutcomeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(student));
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        StudentOutcomeDto result = studentService.getById(id);

        verify(studentRepository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(student);
        assertEquals(studentOutComeDto, result);
    }

    @Test
    public void testCreate() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutcomeDto studentOutComeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(1)
                .build();
        when(mapper.toEntity(studentCreateDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        StudentOutcomeDto result = studentService.create(studentCreateDto);

        verify(mapper, times(1)).toEntity(studentCreateDto);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
        assertEquals(studentOutComeDto, result);
    }

    @Test
    public void testUpdate() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutcomeDto studentOutComeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(1)
                .build();
        doReturn(studentOutComeDto).when(studentService).getById("1");
        when(mapper.toEntity(studentOutComeDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        StudentOutcomeDto result = studentService.update(studentUpdateDto);

        verify(studentService, times(1)).getById("1");
        verify(mapper, times(1)).toEntity(studentOutComeDto);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
        assertEquals(studentOutComeDto, result);
    }

    @Test
    public void testRemove() {
        String id = "1";
        when(studentRepository.existsById(id)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(id);

        studentService.remove("1");

        verify(studentRepository, times(1)).existsById(id);
        verify(studentRepository, times(1)).deleteById(id);
    }


    @Test
    public void testExistByIds() {
        when(studentRepository.existsById("1")).thenReturn(true);
        when(studentRepository.existsById("2")).thenReturn(true);

        boolean result = studentService.existsByIds(Sets.newLinkedHashSet("1", "2"));

        verify(studentRepository, times(1)).existsById("1");
        verify(studentRepository, times(1)).existsById("2");
        assertTrue(result);
    }
}
