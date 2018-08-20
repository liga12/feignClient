package liga.student.service.controller;

import liga.student.service.service.StudentService;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.LinkedHashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static liga.student.service.util.Converter.mapToJson;

@RunWith(MockitoJUnitRunner.class)
public class StudentApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentApiController studentApiController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentApiController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testExistsAllStudentsByIds() throws Exception {
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        when(studentService.existsByIds(studentIds)).thenReturn(true);

        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentIds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));

        verify(studentService, times(1)).existsByIds(studentIds);
    }
}