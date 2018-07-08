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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
        checkController(getMvcResultGet(DEFAULT_URL), studentDTOS);
    }

    @Test
    public void getStudentByName() throws Exception {
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByName("n")).thenReturn(studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "name/n"), studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "name/n2"), new int[]{});
    }

    @Test
    public void getStudentBySurname() throws Exception {
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getBySurname("s")).thenReturn(studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "surname/s"), studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "surname/s2"), new int[]{});
    }

    @Test
    public void getStudentByAge() throws Exception {
        List<StudentDTO> studentDTOS = new ArrayList<>(Collections.singleton(first));
        when(studentService.getByAge(20)).thenReturn(studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "age/20"), studentDTOS);
        checkController(getMvcResultGet(DEFAULT_URL + "age/25"), new int[]{});
    }

    @Test
    public void createStudent() throws Exception {
        when(studentService.create(first)).thenReturn(first);
        checkController(getMvcResultPut(first), first);
        assertThat("").isEqualTo(getMvcResultPut(second).getResponse().getContentAsString());
    }

    @Test
    public void updateStudent() throws Exception {
        when(studentService.getById(first.getId())).thenReturn(first);
        when(studentService.update(first)).thenReturn(first);
        when(studentService.update(first)).thenReturn(first);
        checkController(getMvcResultPost(first), first);
        assertThat("").isEqualTo(getMvcResultPost(second).getResponse().getContentAsString());
    }

    @Test
    public void deleteStudent() throws Exception {
        when(studentService.getById("1")).thenReturn(first);
        checkController(getMvcResultDelete("1"), first);
        assertThat("").isEqualTo(getMvcResultDelete("2").getResponse().getContentAsString());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private MvcResult getMvcResultGet(String URL) throws Exception {
        return mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }
    private MvcResult getMvcResultPut(StudentDTO studentDTO) throws Exception {
        return  mockMvc.perform(put(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentDTO)))
                .andReturn();
    }

    private MvcResult getMvcResultPost(StudentDTO studentDTO) throws Exception {
        return  mockMvc.perform(post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentDTO)))
                .andReturn();
    }

    private MvcResult getMvcResultDelete(String URL) throws Exception {
        return  mockMvc.perform(delete(DEFAULT_URL+URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private void checkController(MvcResult mvcResult, Object obj) throws Exception {
            assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(mapToJson(obj));
    }


}