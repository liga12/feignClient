package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.SchoolClientService;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolClientService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class SchoolControllerIntegrationTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private StudentService studentFeignService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchoolService schoolService;
//    private static ConfigurableApplicationContext eurekaServer;

//    @BeforeClass
//    public static void startEureka() {
//        eurekaServer = SpringApplication.run(EurekaServer.class,
//                "--server.port=8761",
//                "--eureka.instance.leaseRenewalIntervalInSeconds=1");
//    }

//    @AfterClass
//    public static void closeEureka() {
//        eurekaServer.close();
//    }

    //
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
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
                .name("name12")
                .address("address11")
                .studentIds(studentIds)
                .build();

        when(studentFeignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        Iterator<String> iterator = schoolOutComeDto.getStudentIds().iterator();
        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolOutComeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(schoolOutComeDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolOutComeDto.getAddress()))
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

//    @Test
//    @Sql("/scripts/initSchools.sql")
//    public void testUpdateStudent() throws Exception {
//        HashSet<String> studentIds = new HashSet<>(Arrays.asList("1", "2", "3"));
//        SchoolUpdateDto schoolOutComeDto = SchoolUpdateDto.builder()
//                .id(1L)
//                .name("name12")
//                .address("address11")
//                .studentIds(studentIds)
//                .build();
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolOutComeDto school = schoolService.create(SchoolOutComeDto.builder().id(1L).
//                name("n").address("a").studentIds(Collections.singletonList("1")).build());
//        school.setAddress("aaa");
//
//        mockMvc.perform(post("/schools")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(school.getId()))
//                .andExpect(jsonPath("$.name").value(school.getName()))
//                .andExpect(jsonPath("$.address").value(school.getAddress()))
//                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
//    }

    //    @Test
//    public void testUpdateStudentWithSchoolNotFoundException() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        SchoolOutComeDto school = SchoolOutComeDto.builder().id(1L).
//                name("n").address("a").studentIds( studentIds).build();
//        school.setAddress("dd");
//        mockMvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("School not found"));
//    }
//
//    @Test
//    public void testUpdateStudentWithStudentNotFoundException() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIdsUpdate = Collections.singletonList("2");
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(studentFeignService.existsAllStudentsByIds(studentIdsUpdate)).thenReturn(false);
//        SchoolOutComeDto school = schoolService.create(SchoolOutComeDto.builder().id(1L).
//                name("n").address("a").studentIds(studentIds).build());
//        school.setStudentIds(studentIdsUpdate);
//
//        mockMvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("Student not found"));
//    }
//
//    @Test
//    public void deleteStudent() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolOutComeDto first = schoolService.create(SchoolOutComeDto.builder().id(1L).
//                name("n").address("a").studentIds(studentIds).build());
//
//        mockMvc.perform(delete("/schools/{id}", first.getId()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void deleteStudentWithSchoolNotFoundException() throws Exception {
//        mockMvc.perform(delete("/schools/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("School not found"));
//    }
//
    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

//    @Configuration
//    @EnableAutoConfiguration
//    @EnableEurekaServer
//    static class EurekaServer {
//    }
}

