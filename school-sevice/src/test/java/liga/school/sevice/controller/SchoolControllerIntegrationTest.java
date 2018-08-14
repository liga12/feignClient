package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.service.StudentService;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SchoolControllerIntegrationTest {

    @MockBean
    private StudentService studentFeignService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetStudents() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();

        Iterator<String> iterator = schoolOutComeDto.getStudentIds().iterator();
        mockMvc.perform(get("/schools?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(schoolOutComeDto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(schoolOutComeDto.getName()))
                .andExpect(jsonPath("$.content[0].address").value(schoolOutComeDto.getAddress()))
                .andExpect(jsonPath("$.content[0].studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.content[0].studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.content[0].studentIds[2]").value(iterator.next()));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetStudentById() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();

        Iterator<String> iterator = schoolOutComeDto.getStudentIds().iterator();
        mockMvc.perform(get("/schools/{id}", schoolOutComeDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolOutComeDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolOutComeDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolOutComeDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[2]").value(iterator.next()));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetStudentByIdWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(get("/schools/{id}", "13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    @Test
    public void testCreateStudent() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();

        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        Iterator<String> iterator = schoolCreateDto.getStudentIds().iterator();
        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(schoolCreateDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolCreateDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[2]").value(iterator.next()));
    }

    @Test
    public void testCreateStudentWithFalse() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(false);

        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolOutComeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    public void testCreateStudentWithNameNull() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .address("address11")
                .studentIds(studentIds)
                .build();

        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithNameEmpty() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("")
                .address("address11")
                .studentIds(studentIds)
                .build();

        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudent() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolUpdateDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolUpdateDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolUpdateDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(schoolUpdateDto.getStudentIds().iterator().next()));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudentWithEntityNotFoundException() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(11L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }


    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudentWithStudentNotFoundException() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudentWithIdNull() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudentWithIdNameNull() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .address("a")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateStudentWithIdNameEmpty() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void deleteStudent() throws Exception {
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(true);

        mockMvc.perform(delete("/schools/{id}",1L ))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(delete("/schools/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}

