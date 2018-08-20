package liga.school.service.controller;

import liga.school.service.domain.entity.School;
import liga.school.service.domain.repository.SchoolRepository;
import liga.school.service.service.StudentService;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
import org.assertj.core.util.Sets;
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

import java.util.HashSet;
import java.util.Iterator;

import static liga.school.service.util.Converter.mapToJson;
import static org.junit.Assert.assertFalse;
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

    @Autowired
    SchoolRepository schoolRepository;

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetSchools() throws Exception {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        Iterator<String> iterator = school.getStudentIds().iterator();

        mockMvc.perform(get("/schools?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(school.getId()))
                .andExpect(jsonPath("$.content[0].name").value(school.getName()))
                .andExpect(jsonPath("$.content[0].address").value(school.getAddress()))
                .andExpect(jsonPath("$.content[0].studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.content[0].studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.content[0].studentIds[2]").value(iterator.next()));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetSchoolById() throws Exception {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        Iterator<String> iterator = school.getStudentIds().iterator();

        mockMvc.perform(get("/schools/{id}", school.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(school.getId()))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[2]").value(iterator.next()));
    }

    @Test
    public void testGetSchoolByIdWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(get("/schools/{id}", "13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    @Test
    public void testCreateSchool() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
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
    public void testCreateSchoolWithFalse() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
        SchoolOutcomeDto schoolOutComeDto = SchoolOutcomeDto.builder()
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
    public void testCreateSchoolWithNameNull() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name(null)
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
    public void testCreateSchoolWithNameEmpty() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
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
    public void testUpdateSchool() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        Iterator<String> iterator = schoolUpdateDto.getStudentIds().iterator();

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolUpdateDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolUpdateDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolUpdateDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[2]").value(iterator.next()));
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateSchoolWithEntityNotFoundException() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
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
    public void testUpdateSchoolWithStudentNotFoundException() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
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
    public void testUpdateSchoolWithIdNull() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(null)
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
    public void testUpdateSchoolWithIdNameNull() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .address("a")
                .name(null)
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
    public void testUpdateSchoolWithIdNameEmpty() throws Exception {
        HashSet<String> studentIds = Sets.newLinkedHashSet("1", "2", "3");
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
    public void deleteSchool() throws Exception {
        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        long schoolId = 1L;

        mockMvc.perform(delete("/schools/{id}", schoolId))
                .andExpect(status().isOk());

        assertFalse(schoolRepository.existsById(schoolId));
    }

    @Test
    public void deleteSchoolWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(delete("/schools/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));

    }
}

