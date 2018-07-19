package liga.student.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    public void testGetStudents() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        StudentDTO second = StudentDTO.builder().id("1").name("n2").surname("s2").age(20).build();
        List<StudentDTO> studentDTOS = Arrays.asList(first, second);
        when(studentService.getAll()).thenReturn(studentDTOS);

        mockMvc.perform(get("/student/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()))
                .andExpect(jsonPath("$[1].id").value(second.getId()))
                .andExpect(jsonPath("$[1].name").value(second.getName()))
                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
                .andExpect(jsonPath("$[1].age").value(second.getAge()));

        verify(studentService).getAll();
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getById(first.getId())).thenReturn(first);

        mockMvc.perform(get("/student/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()))
                .andExpect(jsonPath("$.age").value(first.getAge()));

        verify(studentService).getById(first.getId());
    }

    @Test
    public void testGetStudentByName() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getByName(first.getName())).thenReturn(Collections.singletonList(first));

        mockMvc.perform(get("/student/name/{name}", first.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));

        verify(studentService).getByName(first.getName());
    }

    @Test
    public void testGetStudentBySurname() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getBySurname(first.getSurname())).thenReturn(Collections.singletonList(first));

        mockMvc.perform(get("/student/surname/{surname}", first.getSurname()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));

        verify(studentService).getBySurname(first.getSurname());
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getByAge(first.getAge())).thenReturn(Collections.singletonList(first));

        mockMvc.perform(get("/student/age/{age}", first.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));

        verify(studentService).getByAge(first.getAge());
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().name("n").surname("s").age(20).build();
        when(studentService.create(first)).thenReturn(first);

        mockMvc.perform(put("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()))
                .andExpect(jsonPath("$.age").value(first.getAge()));

        verify(studentService).create(first);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getById(first.getId())).thenReturn(first);
        when(studentService.update(first)).thenReturn(first);

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.age").value(first.getAge()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()));

        verify(studentService).getById(first.getId());
        verify(studentService).update(first);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getById(first.getId())).thenReturn(first);
        doNothing().when(studentService).remove(first);

        mockMvc.perform(delete("/student/{id}", first.getId()))
                .andExpect(status().isOk());

        verify(studentService).getById(first.getId());
        verify(studentService).remove(first);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}