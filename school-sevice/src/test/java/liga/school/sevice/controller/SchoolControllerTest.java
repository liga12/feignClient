package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SchoolController.class, secure = false)
public class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;

    @MockBean
    private StudentService studentService;

//    @Test
//    public void testGetSchools() throws Exception {
//        SchoolDTO first = SchoolDTO.builder().id(1L).
//                name("n").address("a").studentIds(Collections.singletonList("1")).build();
//        SchoolDTO second = SchoolDTO.builder().id(2L).name("n1").
//                address("a1").studentIds(Collections.singletonList("2")).build();
//        List<SchoolDTO> schoolDTOS = Arrays.asList(first, second);
//        Sorter sorter = new Sorter(1,10, Sort.Direction.ASC,"id");
//        PaginationSchoolDto paginationSchoolDto = new PaginationSchoolDto(sorter);
//        when(schoolService.getAll(paginationSchoolDto)).thenReturn(schoolDTOS);
//
//        mockMvc.perform(post("/school/").contentType(MediaType.APPLICATION_JSON).content("{\"sorter\":{\"page\":1,\"size\":10,\"sortDirection\":\"ASC\",\"sortBy\":\"id\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(first.getId()))
//                .andExpect(jsonPath("$[0].name").value(first.getName()))
//                .andExpect(jsonPath("$[0].address").value(first.getAddress()))
//                .andExpect(jsonPath("$[0].studentIds[0]").value(first.getStudentIds().get(0)))
//                .andExpect(jsonPath("$[1].id").value(second.getId()))
//                .andExpect(jsonPath("$[1].name").value(second.getName()))
//                .andExpect(jsonPath("$[1].address").value(second.getAddress()))
//                .andExpect(jsonPath("$[1].studentIds[0]").value(second.getStudentIds().get(0)));
//
//        verify(schoolService).getAll(paginationSchoolDto);
//    }
//
    @Test
    public void testGetSchoolById() throws Exception {
        SchoolDTO first = SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build();
        when(schoolService.getById(first.getId())).thenReturn(first);

        mockMvc.perform(get("/school/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.address").value(first.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(first.getStudentIds().get(0)));

        verify(schoolService).getById(first.getId());
    }

    @Test
    public void testCreateSchool() throws Exception {
        SchoolDTO first = SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build();
        when(schoolService.create(first)).thenReturn(first);
        when(studentService.existsStudentsByIds(first.getStudentIds())).
                thenReturn(true);

        mockMvc.perform(put("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.address").value(first.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(first.getStudentIds().get(0)));
        verify(schoolService).create(first);
    }

    @Test
    public void testUpdateSchool() throws Exception {
        SchoolDTO first = SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build();
        when(schoolService.update(first)).thenReturn(first);
        when(studentService.existsStudentsByIds(first.getStudentIds())).
                thenReturn(true);

        mockMvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.address").value(first.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(first.getStudentIds().get(0)));

        verify(schoolService).update(first);
    }

    @Test
    public void testDeleteSchool() throws Exception {
        SchoolDTO first = SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build();
        doNothing().when(schoolService).remove(first.getId());

        mockMvc.perform(delete("/school/{id}", first.getId()))
                .andExpect(status().isOk());

        verify(schoolService).remove(first.getId());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
