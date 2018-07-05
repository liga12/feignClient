package liga.student.service.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentRelationController.class, secure = false)
public class StudentRelationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    public void getStudentById() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.getById(Mockito.anyString())).thenReturn(first);

        MvcResult mvcResult = mockMvc.perform(get("/student/id/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String expectedJson = mapToJson(first);
        String outputInJson = mvcResult.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);

    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}