package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.SchoolClientService;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolClientService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SchoolControllerIntegrationTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private StudentService studentFeignService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchoolService schoolService;
    private static ConfigurableApplicationContext eurekaServer;

    @BeforeClass
    public static void startEureka() {
        eurekaServer = SpringApplication.run(EurekaServer.class,
                "--server.port=8761",
                "--eureka.instance.leaseRenewalIntervalInSeconds=1");
    }

    @AfterClass
    public static void closeEureka() {
        eurekaServer.close();
    }

    @Before
    public void setUp() {
        schoolRepository.deleteAll();
    }

    @Test
    public void testGetStudents() throws Exception {
        SchoolDTO first = schoolService.create(SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build());
        SchoolDTO second = schoolService.create(SchoolDTO.builder().id(2L).name("n1").
                address("a1").studentIds(Collections.singletonList("2")).build());

        mockMvc.perform(get("/school/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].address").value(first.getAddress()))
                .andExpect(jsonPath("$[0].studentIds[0]").value(first.getStudentIds().get(0)))
                .andExpect(jsonPath("$[1].id").value(second.getId()))
                .andExpect(jsonPath("$[1].name").value(second.getName()))
                .andExpect(jsonPath("$[1].address").value(second.getAddress()))
                .andExpect(jsonPath("$[1].studentIds[0]").value(second.getStudentIds().get(0)));
    }


    @Test
    public void testGetStudentById() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO first = schoolService.create(SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(studentIds).build());


        mockMvc.perform(get("/school/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.address").value(first.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(first.getStudentIds().get(0)));
    }

    @Test
    public void testGetStudentByIdWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(get("/school/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    @Test
    public void testCreateStudent() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        SchoolDTO school = SchoolDTO.builder().
                name("n").address("a").studentIds(studentIds).build();
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(true);

        mockMvc.perform(put("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
    }

    @Test
    public void testCreateStudentWithFalse() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        SchoolDTO school = SchoolDTO.builder().
                name("n").address("a").studentIds(studentIds).build();
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(false);

        mockMvc.perform(put("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO school = schoolService.create(SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(Collections.singletonList("1")).build());
        school.setAddress("aaa");

        mockMvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(school.getId()))
                .andExpect(jsonPath("$.name").value(school.getName()))
                .andExpect(jsonPath("$.address").value(school.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
    }

    @Test
    public void testUpdateStudentWithSchoolNotFoundException() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        SchoolDTO school = SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds( studentIds).build();
        school.setAddress("dd");
        mockMvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    @Test
    public void testUpdateStudentWithStudentNotFoundException() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIdsUpdate = Collections.singletonList("2");
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(studentFeignService.existsStudentsByIds(studentIdsUpdate)).thenReturn(false);
        SchoolDTO school = schoolService.create(SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(studentIds).build());
        school.setStudentIds(studentIdsUpdate);

        mockMvc.perform(post("/school").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    public void deleteStudent() throws Exception {
        List<String> studentIds = Collections.singletonList("1");
        when(studentFeignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO first = schoolService.create(SchoolDTO.builder().id(1L).
                name("n").address("a").studentIds(studentIds).build());

        mockMvc.perform(delete("/school/{id}", first.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentWithSchoolNotFoundException() throws Exception {
        mockMvc.perform(delete("/school/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("School not found"));
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}

