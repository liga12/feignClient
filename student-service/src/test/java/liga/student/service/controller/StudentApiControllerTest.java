package liga.student.service.controller;

import liga.student.service.dto.StudentDTO;
import liga.student.service.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentApiController.class, secure = false)
public class StudentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
        when(studentService.existsById(first.getId())).thenReturn(first);

        mockMvc.perform(get("/student-api/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("1"));

        verify(studentService).existsById(first.getId());
    }
}