package liga.student.service.service;

import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.PaginationStudentSearchTextDto;
import liga.student.service.transport.dto.StudentDTO;
import liga.student.service.transport.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        Student student = Student.builder().name("n").surname("s").age(1).build();
        StudentDTO studentDTO = StudentDTO.builder().name("n").surname("s").age(1).build();
        Sorter sorter = new Sorter(0, 1, Sort.Direction.ASC, "id");
        int page = sorter.getPage();
        int size = sorter.getSize();
        Sort.Direction sortDirection = sorter.getSortDirection();
        String sortBy = sorter.getSortBy();
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, sortBy);
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n").build();
        List<Student> studentPage = Collections.singletonList(student);
        when(studentRepository.
                searchByNamesAndSurname(paginationSearch.getText(), paginationSearch.getCaseSensitive(), pageRequest)).
                thenReturn(Collections.singletonList(student));
        when(mapper.toDto(studentPage)).thenReturn(Collections.singletonList(studentDTO));

        studentService.getAll(paginationSearch);

        verify(studentRepository, times(1)).
                searchByNamesAndSurname(paginationSearch.getText(), paginationSearch.getCaseSensitive(), pageRequest);
        verify(mapper, times(1)).toDto(Collections.singletonList(student));
    }

    @Test
    public void testGetAll() {
        Student student = Student.builder().name("n").surname("s").age(1).build();
        StudentDTO studentDTO = StudentDTO.builder().name("n").surname("s").age(1).build();
        Sorter sorter = new Sorter(0, 1, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).build();
        List<Student> studentPage = Collections.singletonList(student);
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(student));
        when(mapper.toDto(studentPage)).thenReturn(Collections.singletonList(studentDTO));

        studentService.getAll(pagination);

        verify(mongoTemplate, times(1)).
                find(any(), any());
        verify(mapper, times(1)).toDto(Collections.singletonList(student));
    }


    @Test
    public void testGetById() {
        StudentDTO studentDTO = StudentDTO.builder().id("1").name("n").surname("s").age(1).build();
        Student student = Student.builder().id("1").name("n").surname("s").age(1).build();
        when(studentRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(student));
        when(mapper.toDto(student)).thenReturn(studentDTO);

        studentService.getById(studentDTO.getId());

        verify(studentService, times(1)).getById(studentDTO.getId());
        verify(mapper, times(1)).toDto(student);
    }

    @Test
    public void testExistById() {
        when(studentRepository.existsById("1")).thenReturn(true);

        studentService.existsById("1");

        verify(studentRepository, times(1)).existsById("1");
    }

    @Test
    public void testExistByIds() {
        when(studentRepository.existsById(any())).thenReturn(true);

        studentService.existsByIds(Arrays.asList("1","2"));

        verify(studentRepository, times(2)).existsById(any());
    }

    @Test
    public void testCreate() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        Student student = Student.builder().id("1").name("n").surname("s").age(1).build();
        when(mapper.studentDTOToStudent(studentDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentDTO);

        studentService.create(studentDTO);

        verify(mapper, times(1)).studentDTOToStudent(studentDTO);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
    }

    @Test
    public void testUpdate() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        Student student = Student.builder().id("1").name("n").surname("s").age(1).build();
        doReturn(true).when(studentService).existsById(any());
        when(mapper.studentDTOToStudent(studentDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(mapper.toDto(student)).thenReturn(studentDTO);

        studentService.update(studentDTO);

        verify(studentService, times(1)).existsById(any());
        verify(mapper, times(1)).studentDTOToStudent(studentDTO);
        verify(studentRepository, times(1)).save(student);
        verify(mapper, times(1)).toDto(student);
    }

    @Test
    public void testRemove() {
        doReturn(true).when(studentService).existsById(any());

        studentService.remove("1");

        verify(studentService, times(1)).existsById(any());
    }
}
