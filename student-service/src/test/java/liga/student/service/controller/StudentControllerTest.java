package liga.student.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.service.StudentService;
import liga.student.service.transport.dto.PaginationStudentDto;
import liga.student.service.transport.dto.PaginationStudentSearchTextDto;
import liga.student.service.transport.dto.Sorter;
import liga.student.service.transport.dto.StudentDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        Sorter sorter = new Sorter(0, 2, Sort.Direction.ASC, "id");
        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).build();
        when(studentService.getAll(pagination)).thenReturn(studentDTOS);

        mockMvc.perform(post("/student/")
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(pagination)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()))
                .andExpect(jsonPath("$[1].id").value(second.getId()))
                .andExpect(jsonPath("$[1].name").value(second.getName()))
                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
                .andExpect(jsonPath("$[1].age").value(second.getAge()));

        verify(studentService).getAll(pagination);
    }

    @Test
    public void testGetStudentsTextSearch() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        StudentDTO second = StudentDTO.builder().id("1").name("n2").surname("s2").age(20).build();
        List<StudentDTO> studentDTOS = Arrays.asList(first, second);
        Sorter sorter = new Sorter(0, 2, Sort.Direction.ASC, "id");
        PaginationStudentSearchTextDto paginationSearch = PaginationStudentSearchTextDto.builder()
                .sorter(sorter).caseSensitive(false).text("n").build();
        when(studentService.getAll(paginationSearch)).thenReturn(studentDTOS);

        mockMvc.perform(post("/student/textSearch")
                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(paginationSearch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()))
                .andExpect(jsonPath("$[1].id").value(second.getId()))
                .andExpect(jsonPath("$[1].name").value(second.getName()))
                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
                .andExpect(jsonPath("$[1].age").value(second.getAge()));

        verify(studentService).getAll(paginationSearch);
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
        when(studentService.update(first)).thenReturn(first);

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.age").value(first.getAge()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()));

        verify(studentService).update(first);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        String id = "1";
        doNothing().when(studentService).remove(id);

        mockMvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk());

        verify(studentService).remove(id);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}