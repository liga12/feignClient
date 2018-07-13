package liga.student.service.controller;

import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.Assert.assertEquals;

import liga.student.service.StudentClientService;
import liga.student.service.dto.StudentDTO;
import liga.student.service.service.MongoConfig;
import liga.student.service.service.StudentService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentMainControllerIntegrationTest {

    @Autowired
    private StudentService studentService;
    static ConfigurableApplicationContext eurekaServer;

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

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldRegisterClientInEurekaServer() throws InterruptedException {
        StudentDTO studentDTO = studentService.create(StudentDTO.builder().name("name").surname("surname").age(25).build());
        // registration has to take place...
        Thread.sleep(3000);
        System.out.println(port);

        ResponseEntity<String> response = this.testRestTemplate.getForEntity("http://localhost:" + port + "/student/", String.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody().toString(),StudentDTO.builder().name("name").surname("surname").age(25).build().toString());
//        then(response.getBody()).contains("a-bootiful-client");
    }


    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}
