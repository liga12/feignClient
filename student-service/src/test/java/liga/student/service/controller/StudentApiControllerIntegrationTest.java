package liga.student.service.controller;

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
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentApiControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;
    private static ConfigurableApplicationContext eurekaServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void startEureka() {
        eurekaServer = SpringApplication.run(StudentControllerIntegrationTest.EurekaServer.class,
                "--server.port=8761",
                "--eureka.instance.leaseRenewalIntervalInSeconds=1");
    }

    @AfterClass
    public static void closeEureka() {
        eurekaServer.close();
    }

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());

        mockMvc.perform(get("/student-api/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testGetStudentByIdWithStudentNotFoundException() throws Exception {
        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();

        mockMvc.perform(get("/student-api/{id}", first.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}
