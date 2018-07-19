package liga.school.sevice.controller;

import liga.school.sevice.SchoolClientService;
import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import liga.student.service.dto.StudentDTO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolClientService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SchoolControllerIntegrationTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private StudentService studentFeignService;

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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    private ParameterizedTypeReference<List<SchoolDTO>> shoolDTOList = new ParameterizedTypeReference<List<SchoolDTO>>() {
    };

    @Before
    public void setUp() {
        schoolRepository.deleteAll();
    }

    @Test
    public void getStudents() {
        SchoolDTO first = createSchoolDTO();
        SchoolDTO second = schoolService.create(SchoolDTO.builder().name("n2").address("a2").studentIds(Collections.singletonList("2")).build());
        ResponseEntity<List<SchoolDTO>> response = testRestTemplate.exchange(getURL(), HttpMethod.GET, null, shoolDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Arrays.asList(first, second));
        checkValueInDB(2);
    }

    @Test
    public void getStudentById() {
        SchoolDTO first = createSchoolDTO();
        ResponseEntity<SchoolDTO> response = testRestTemplate
                .exchange(getURL() + "id/" + first.getId(), HttpMethod.GET, null, SchoolDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), first);
        response = testRestTemplate.exchange(getURL() + "id/2", HttpMethod.GET, null, SchoolDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(response.getBody());
        checkValueInDB(1);
    }

    @Test
    public void getStudentByName() {
        SchoolDTO first = createSchoolDTO();
        checkCollection(first, "name/" + first.getName(), "name/n3");
        checkValueInDB(1);
    }

    @Test
    public void getStudentByAddress() {
        SchoolDTO first = createSchoolDTO();
        checkCollection(first, "address/" + first.getAddress(), "address/s3");
        checkValueInDB(1);
    }

    @Test
    public void createStudent() throws Exception {
        SchoolDTO first = createSchoolDTO();
        when(studentFeignService.getStudentById(first.getStudentIds().get(0))).thenReturn(new StudentDTO());
        ResponseEntity<StudentDTO> response = testRestTemplate
                .exchange(RequestEntity.put(new URI(getURL())).body(first), StudentDTO.class);
        ResponseEntity<StudentDTO> response2 = testRestTemplate.
                exchange(getURL() + "id/" + Objects
                        .requireNonNull(response.getBody()).getId(), HttpMethod.GET, null, StudentDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), response2.getBody());
        checkValueInDB(1);
    }

    @Test
    public void updateStudent() throws Exception {
        SchoolDTO first = createSchoolDTO();
        first.setName("n2");
        when(studentFeignService.getStudentById(first.getStudentIds().get(0))).thenReturn(new StudentDTO());
        ResponseEntity<SchoolDTO> response = testRestTemplate
                .exchange(RequestEntity.post(new URI(getURL())).body(first), SchoolDTO.class);
        ResponseEntity<List<SchoolDTO>> response2 = testRestTemplate
                .exchange(getURL() + "name/" + first.getName(), HttpMethod.GET, null, shoolDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Objects.requireNonNull(response2.getBody()).get(0));
        response2 = testRestTemplate
                .exchange(getURL() + "name/n" + first.getName(), HttpMethod.GET, null, shoolDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response2.getBody(), new ArrayList<>());
        checkValueInDB(1);
    }

    @Test
    public void deleteStudent() {
        SchoolDTO first = createSchoolDTO();
        ResponseEntity<SchoolDTO> response = testRestTemplate
                .exchange(getURL() + "/" + first.getId(), HttpMethod.DELETE, null, SchoolDTO.class);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<SchoolDTO> response2 = testRestTemplate.
                exchange(getURL() + "id/" + first.getId(), HttpMethod.GET, null, SchoolDTO.class);
        then(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(response2.getBody());
        ResponseEntity<List<SchoolDTO>> response3 = testRestTemplate.
                exchange(getURL(), HttpMethod.GET, null, shoolDTOList);
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response3.getBody(), new ArrayList<>());
    }

    private void checkValueInDB(int size) {
        ResponseEntity<List<SchoolDTO>> response3 = testRestTemplate.
                exchange(getURL(), HttpMethod.GET, null, shoolDTOList);
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response3.getBody()).size(), size);
    }

    private SchoolDTO createSchoolDTO() {
        return schoolService.create(SchoolDTO.builder().name("n").address("a").studentIds(Collections.singletonList("1")).build());
    }

    private void checkCollection(SchoolDTO schoolDTO, String firstURL, String secondURL) {
        ResponseEntity<List<SchoolDTO>> response = testRestTemplate
                .exchange(getURL() + firstURL, HttpMethod.GET, null, shoolDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), Collections.singletonList(schoolDTO));
        response = testRestTemplate.exchange(getURL() + secondURL, HttpMethod.GET, null, shoolDTOList);
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
    }

    private String getURL() {
        return "http://localhost:" + port + "/school/";
    }


    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}
