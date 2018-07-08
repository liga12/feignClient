package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDTO;
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
@WebMvcTest(value = SchoolController.class, secure = false)
public class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;

    @MockBean
    private StudentService studentFeignService;

    private final String DEFAULT_URL = "/school/";

    private SchoolDTO first;
    private SchoolDTO second;


    @Before
    public void setUp(){
        first = new SchoolDTO(1L, "name", "address", new ArrayList<>(Collections.singleton("1")));
        second = new SchoolDTO(2L, "name2", "address2", new ArrayList<>(Collections.singleton("2")));
    }

    @Test
    public void getSchools() throws Exception {
        List<SchoolDTO> schoolDTOS = new ArrayList<>(Arrays.asList(first, second));
        when(schoolService.getAll()).thenReturn(schoolDTOS);
        checkController(createMvcResultGet(DEFAULT_URL), schoolDTOS);
    }

    @Test
    public void getSchoolById() throws Exception {
        when(schoolService.getById(1L)).thenReturn(first);
        checkController(createMvcResultGet(DEFAULT_URL + "id/1"), first);
        MvcResult mvcResult = mockMvc.perform(get(DEFAULT_URL + "id/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat("").isEqualTo(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getSchoolByName() throws Exception {
        List<SchoolDTO> schoolDTOS = new ArrayList<>(Collections.singleton(first));
        when(schoolService.getByName("name")).thenReturn(schoolDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "name/name"), schoolDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "name/name2"), new int[]{});
    }

    @Test
    public void getSchoolByAddress() throws Exception {
        List<SchoolDTO> schoolDTOS = new ArrayList<>(Collections.singleton(first));
        when(schoolService.getByAddress("address")).thenReturn(schoolDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "address/address"), schoolDTOS);
        checkController(createMvcResultGet(DEFAULT_URL + "address/address2"), new int[]{});
    }

    @Test
    public void createSchool() throws Exception {
        when(schoolService.create(first)).thenReturn(first);
        when(studentFeignService.getStudentById("1")).thenReturn(new StudentDTO());
        checkController(createMvcResultPut(first), first);
        assertThat("").isEqualTo(createMvcResultPut(second).getResponse().getContentAsString());
    }

    @Test
    public void updateSchool() throws Exception {
        when(schoolService.getById(first.getId())).thenReturn(first);
        when(schoolService.update(first)).thenReturn(first);
        when(studentFeignService.getStudentById("1")).thenReturn(new StudentDTO());
        checkController(createMvcResultPost(first), first);
        assertThat("").isEqualTo(createMvcResultPost(second).getResponse().getContentAsString());
    }

    @Test
    public void deleteSchool() throws Exception {
        when(schoolService.getById(1L)).thenReturn(first);
        checkController(createMvcResultDelete("1"), first);
        assertThat("").isEqualTo(createMvcResultDelete("2").getResponse().getContentAsString());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private MvcResult createMvcResultDelete(String URL) throws Exception {
        return  mockMvc.perform(delete(DEFAULT_URL+URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private MvcResult createMvcResultPut(SchoolDTO schoolDTO) throws Exception {
        return  mockMvc.perform(put(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolDTO)))
                .andReturn();
    }

    private MvcResult createMvcResultPost(SchoolDTO schoolDTO) throws Exception {
        return  mockMvc.perform(post(DEFAULT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolDTO)))
                .andReturn();
    }
    private MvcResult createMvcResultGet(String URL) throws Exception {
        return mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private void checkController(MvcResult mvcResult, Object obj) throws Exception {
            assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(mapToJson(obj));
    }
}