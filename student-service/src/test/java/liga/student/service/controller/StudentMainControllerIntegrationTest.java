package liga.student.service.controller;

import liga.student.service.StudentClientService;
import liga.student.service.domain.StudentRepository;
import liga.student.service.dto.StudentDTO;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentMainControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;
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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private ParameterizedTypeReference<List<StudentDTO>> studentDTOList = new ParameterizedTypeReference<List<StudentDTO>>() {
    };

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void getStudents() {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        StudentDTO second = studentService.create(StudentDTO.builder().id("2").name("n2").surname("s2").age(22).build());
        ResponseEntity<List<StudentDTO>> response = testRestTemplate.exchange(getURL(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Arrays.asList(first, second));
    }

    @Test
    public void getStudentById() {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        ResponseEntity<StudentDTO> response = testRestTemplate.exchange(getURL() + "id/" + first.getId(), HttpMethod.GET, null, StudentDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), first);
        response = testRestTemplate.exchange(getURL() + "id/2", HttpMethod.GET, null, StudentDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(response.getBody());
    }

    @Test
    public void getStudentByName() {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        ResponseEntity<List<StudentDTO>> response = testRestTemplate
                .exchange(getURL() + "name/" + first.getName(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Collections.singletonList(first));
        response = testRestTemplate.exchange(getURL() + "name/n3", HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
    }

    @Test
    public void getStudentBySurname() {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        ResponseEntity<List<StudentDTO>> response = testRestTemplate
                .exchange(getURL() + "surname/" + first.getSurname(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Collections.singletonList(first));
        response = testRestTemplate.exchange(getURL() + "surname/s3", HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
    }

    @Test
    public void getStudentByAge() {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        ResponseEntity<List<StudentDTO>> response = testRestTemplate
                .exchange(getURL() + "age/" + first.getAge(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Collections.singletonList(first));
        response = testRestTemplate.exchange(getURL() + "age/21", HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
    }

    @Test
    public void createStudent() throws Exception {
        StudentDTO first = StudentDTO.builder().name("n").surname("s").age(20).build();
        ResponseEntity<StudentDTO> response = testRestTemplate
                .exchange(RequestEntity.put(new URI(getURL())).body(first), StudentDTO.class);
        ResponseEntity<StudentDTO> response2 = testRestTemplate.
                exchange(getURL() + "id/" + Objects
                        .requireNonNull(response.getBody()).getId(), HttpMethod.GET, null, StudentDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), response2.getBody());
        ResponseEntity<List<StudentDTO>> response3 = testRestTemplate.exchange(getURL(), HttpMethod.GET, null, studentDTOList);
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response3.getBody()).size(),  1);
    }

    @Test
    public void updateStudent() throws Exception {
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        first.setName("n2");
        ResponseEntity<StudentDTO> response = testRestTemplate
                .exchange(RequestEntity.post(new URI(getURL())).body(first), StudentDTO.class);
        ResponseEntity<List<StudentDTO>> response2 = testRestTemplate
                .exchange(getURL() + "name/" + first.getName(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Objects.requireNonNull(response2.getBody()).get(0));
         response2 = testRestTemplate
                .exchange(getURL() + "name/n" + first.getName(), HttpMethod.GET, null, studentDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response2.getBody(), new ArrayList<>());
        ResponseEntity<List<StudentDTO>> response3 = testRestTemplate.exchange(getURL(), HttpMethod.GET, null, studentDTOList);
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response3.getBody()).size(),  1);
    }

    @Test
    public void deleteStudent(){
        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
        ResponseEntity<StudentDTO> response = testRestTemplate
                .exchange(getURL() + "/"+first.getId(), HttpMethod.DELETE, null, StudentDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<StudentDTO> response2 = testRestTemplate.exchange(getURL() + "id/" + first.getId(), HttpMethod.GET, null, StudentDTO.class);
        then(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(response2.getBody());
        ResponseEntity<List<StudentDTO>> response3 = testRestTemplate.exchange(getURL(), HttpMethod.GET, null, studentDTOList);
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response3.getBody(),  new ArrayList<>());

    }

    private String getURL() {
        return "http://localhost:" + port + "/student/";
    }


    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}
