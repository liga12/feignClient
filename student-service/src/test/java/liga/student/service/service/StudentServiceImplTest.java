package liga.student.service.service;

import liga.student.service.dto.StudentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class StudentServiceImplTest {

    @Mock
    private StudentService studentService;

    @Test
    public void getAll() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.getAll()).thenReturn(Collections.singletonList(studentDTO));
        studentService.getAll();
        verify(studentService).getAll();
    }

    @Test
    public void getById() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.getById(studentDTO.getId())).thenReturn(studentDTO);
        studentService.getById(studentDTO.getId());
        verify(studentService).getById(studentDTO.getId());
    }

    @Test
    public void getByName() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.getByName(studentDTO.getName())).thenReturn(Collections.singletonList(studentDTO));
        studentService.getByName(studentDTO.getName());
        verify(studentService).getByName(studentDTO.getName());
    }

    @Test
    public void getBySurname() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.getBySurname(studentDTO.getSurname())).thenReturn(Collections.singletonList(studentDTO));
        studentService.getBySurname(studentDTO.getSurname());
        verify(studentService).getBySurname(studentDTO.getSurname());
    }

    @Test
    public void getByAge() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.getByAge(studentDTO.getAge())).thenReturn(Collections.singletonList(studentDTO));
        studentService.getByAge(studentDTO.getAge());
        verify(studentService).getByAge(studentDTO.getAge());
    }

    @Test
    public void create() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.create(studentDTO)).thenReturn(studentDTO);
        studentService.create(studentDTO);
        verify(studentService).create(studentDTO);
    }

    @Test
    public void update() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        when(studentService.update(studentDTO)).thenReturn(studentDTO);
        studentService.update(studentDTO);
        verify(studentService).update(studentDTO);
    }

    @Test
    public void remove() {
        StudentDTO studentDTO = StudentDTO.builder().name("name").surname("surname").age(25).build();
        doNothing().when(studentService).remove(studentDTO);
        studentService.remove(studentDTO);
        verify(studentService).remove(studentDTO);
    }
}
