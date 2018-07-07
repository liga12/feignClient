package liga.student.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentMainController.class, secure = false)
public class StudentMainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    private final String DEFAULT_URL = "/student/";

    private StudentDTO first;
    private StudentDTO second;

    @Before
    public void setUp() {
          first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
          second = StudentDTO.builder().id("2").name("n2").surname("s2").age(22).build();
    }

    @Test
    public void getStudents() throws Exception {

        List<StudentDTO> studentDTOS = new ArrayList<>(Arrays.asList(first, second));
        when(studentService.getAll()).thenReturn(studentDTOS);
        checkController(createMvcResultGet(DEFAULT_URL), studentDTOS, true);
    }

    @Test
    public void getStudentByName() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByName("n")).thenReturn(studentDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "name/n"), studentDTOS, true);
        checkController(createMvcResultGet(DEFAULT_URL + "name/n2"), studentDTOS, false);
    }

    @Test
    public void getStudentBySurname() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getBySurname("s")).thenReturn(studentDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "surname/s"), studentDTOS, true);
        checkController(createMvcResultGet(DEFAULT_URL + "surname/s2"), studentDTOS, false);
    }

    @Test
    public void getStudentByAge() throws Exception {
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByAge(20)).thenReturn(studentDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "age/20"), studentDTOS, true);
        checkController(createMvcResultGet(DEFAULT_URL + "age/25"), studentDTOS, false);
    }

    @Test
    public void createStudent() throws Exception {
        when(studentService.create(first)).thenReturn(first);
        MvcResult mvcResult = mockMvc.perform(put(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(first)))
                .andReturn();
        checkController(mvcResult, first, true);
        mvcResult = mockMvc.perform(put(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(second)))
                .andExpect(status().isOk())
                .andReturn();
        checkController(mvcResult, second, false);
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentService.getById(first.getId())).thenReturn(first);
        when(studentService.update(first)).thenReturn(first);
        MvcResult mvcResult = mockMvc.perform(post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(first)))
                .andReturn();
        checkController(mvcResult, first, true);
        mvcResult = mockMvc.perform(post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapToJson(second)))
                .andReturn();
        assertThat("").isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void deleteStudent() throws Exception {
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
        assertThat("").isEqualTo(mvcResult.getResponse().getContentAsString());
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