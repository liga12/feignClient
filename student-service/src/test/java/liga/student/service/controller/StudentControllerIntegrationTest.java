package liga.student.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.student.service.StudentClientService;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
import liga.student.service.exception.StudentNotFoundException;
import liga.student.service.service.MongoConfig;
import liga.student.service.service.StudentService;
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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;
    private static ConfigurableApplicationContext eurekaServer;

    @Autowired
    private MockMvc mockMvc;

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

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetStudents() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        StudentDTO second = studentService.create(StudentDTO.builder().name("n2").surname("s2").age(20).build());

        mockMvc.perform(get("/student/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()))
                .andExpect(jsonPath("$[1].id").value(second.getId()))
                .andExpect(jsonPath("$[1].name").value(second.getName()))
                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
                .andExpect(jsonPath("$[1].age").value(second.getAge()));
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()))
                .andExpect(jsonPath("$.age").value(first.getAge()));
    }

    @Test
    public void testGetStudentByIdAPI() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student-api/{id}", first.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()))
                .andExpect(jsonPath("$.age").value(first.getAge()));
    }

    @Test(expected = StudentNotFoundException.class)
    public void testGetStudentByIdWithStudentNotFound() throws Exception {

        mockMvc.perform(get("/student/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentByName() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student/name/{name}", first.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));
    }

    @Test
    public void testGetStudentBySurname() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student/surname/{surname}", first.getSurname()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student/age/{age}", first.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(first.getId()))
                .andExpect(jsonPath("$[0].name").value(first.getName()))
                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
                .andExpect(jsonPath("$[0].age").value(first.getAge()));
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());

        mockMvc.perform(put("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()))
                .andExpect(jsonPath("$.age").value(first.getAge()));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.age").value(first.getAge()))
                .andExpect(jsonPath("$.surname").value(first.getSurname()));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());

        mockMvc.perform(delete("/student/{id}", first.getId()))
                .andExpect(status().isOk());
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
