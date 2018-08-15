package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.transport.dto.*;
import liga.student.service.transport.mapper.StudentMapper;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentFindByTextSearchDto searchDto = StudentFindByTextSearchDto.builder()
                .text("1")
                .caseSensitive(true)
                .build();
        List<Student> students = Collections.singletonList(student);
        PageRequest pageable = PageRequest.of(0, 10);
        when(studentRepository.
                searchByNamesAndSurname(
                        searchDto.getText(),
                        searchDto.getCaseSensitive(),
                        pageable
                )
        ).thenReturn(students);
        when(mapper.toDto(students)).thenReturn(Collections.singletonList(studentOutComeDto));

        studentService.getAll(searchDto, pageable);

        verify(studentRepository, times(1))
                .searchByNamesAndSurname(searchDto.getText(),
                        searchDto.getCaseSensitive(),
                        pageable);
        verify(mapper, times(1)).toDto(students);
    }

    @Test
    public void testGetAll() {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentFindDto findDto = StudentFindDto.builder().build();
        PageRequest pageable = PageRequest.of(0, 10);
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(student));
        when(mapper.toDto(Collections.singletonList(student))).thenReturn(Collections.singletonList(studentOutComeDto));

        studentService.getAll(findDto, pageable);

        verify(mongoTemplate, times(1)).
                find(any(), any());
        verify(mapper, times(1)).toDto(Collections.singletonList(student));
    }


    @Test
    public void testGetById() {
        String id = "1";
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .name("n")
                .surname("s")
                .age(1)
                .build();
        when(studentRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(student));
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        studentService.getById(id);

        verify(studentRepository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(student);
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
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(1)
                .build();
        when(mapper.toEntity(studentCreateDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        studentService.create(studentCreateDto);

        verify(mapper, times(1)).toEntity(studentCreateDto);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
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
        StudentOutComeDto studentOutComeDto = StudentOutComeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(1)
                .build();
        doReturn(studentOutComeDto).when(studentService).getById("1");
        when(mapper.toEntity(studentOutComeDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentOutComeDto);

        studentService.update(studentUpdateDto);

        verify(studentService, times(1)).getById("1");
        verify(mapper, times(1)).toEntity(studentOutComeDto);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
    }

    @Test
    public void testRemove() {
        String id= "1";
        when(studentRepository.existsById(id)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(id);

        studentService.remove("1");

        verify(studentRepository, times(1)).existsById(id);
        verify(studentRepository, times(1)).deleteById(id);
    }


    @Test
    public void testExistByIds() {
        Set<String> ids = Sets.newLinkedHashSet("1", "2");
        when(studentRepository.existsById("1")).thenReturn(true);
        when(studentRepository.existsById("2")).thenReturn(true);

        studentService.existsByIds(ids);

        verify(studentRepository, times(1)).existsById("1");
        verify(studentRepository, times(1)).existsById("2");
    }
}
