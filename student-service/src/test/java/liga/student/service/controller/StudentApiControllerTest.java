package liga.student.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.service.StudentService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentApiController.class, secure = false)
public class StudentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

//    @Test
//    public void testGetStudentById() throws Exception {
//        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
//        when(studentService.existsByIds(Collections.singletonList(first.getId()))).thenReturn(true);
//
//        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
//                .content(mapToJson(Collections.singletonList(first.getId()))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("true"));
//
//        verify(studentService).existsByIds(Collections.singletonList(first.getId()));
//    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}