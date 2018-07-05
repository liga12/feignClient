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
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentMainController.class, secure = false)
public class StudentMainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    public void getStudents() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        StudentDTO second = StudentDTO.builder().id("2").name("n2").surname("s2").age(22).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Arrays.asList(first, second));
        when(studentService.getAll()).thenReturn(studentDTOS);
        String URL = "/student/";
        checkController(createMvcResultGet(URL), studentDTOS, true);
    }


    @Test
    public void getStudentByName() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByName("n")).thenReturn(studentDTOS);
        String URL = "/student/name/n";
        checkController(createMvcResultGet(URL), studentDTOS, true);
        URL = "/student/name/n2";
        checkController(createMvcResultGet(URL), studentDTOS, false);
    }

    @Test
    public void getStudentBySurname() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getBySurname("s")).thenReturn(studentDTOS);
        String URL = "/student/surname/s";
        checkController(createMvcResultGet(URL), studentDTOS, true);
        URL = "/student/surname/s2";
        checkController(createMvcResultGet(URL), studentDTOS, false);
    }

    @Test
    public void getStudentByAge() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByAge(20)).thenReturn(studentDTOS);
        String URL = "/student/age/20";
        checkController(createMvcResultGet(URL), studentDTOS, true);
        URL = "/student/age/s2";
        checkController(createMvcResultGet(URL), studentDTOS, false);
    }

    @Test
    public void createStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.create(first)).thenReturn(first);
        String URL = "/student/";
        MvcResult mvcResult = mockMvc.perform(put(URL).content(this.mapToJson(first))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        checkController(mvcResult, first, true);
    }

    @Test
    public void updateStudent() throws Exception {
    }

    @Test
    public void deleteStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getById("1")).thenReturn(first);
        String URL = "/student/1";
        MvcResult mvcResult = mockMvc.perform(delete(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        checkController(mvcResult, first, true);
         URL = "/student/2";
         mvcResult = mockMvc.perform(delete(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        checkController(mvcResult, first, false);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private MvcResult createMvcResultGet(String URL) throws Exception {
        return mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private void checkController(MvcResult mvcResult, Object obj, Boolean equals) throws Exception {
        String expectedJson = this.mapToJson(obj);
        String outputInJson = mvcResult.getResponse().getContentAsString();
        if (equals)
            assertThat(outputInJson).isEqualTo(expectedJson);
        else
            assertThat(outputInJson).isNotEqualTo(expectedJson);
    }
}