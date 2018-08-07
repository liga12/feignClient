package liga.student.service.controller;

import liga.student.service.StudentClientService;
import liga.student.service.service.MongoConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentApiControllerIntegrationTest {

//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private StudentService studentService;
//    private static ConfigurableApplicationContext eurekaServer;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeClass
//    public static void startEureka() {
//        eurekaServer = SpringApplication.run(StudentControllerIntegrationTest.EurekaServer.class,
//                "--server.port=8761",
//                "--eureka.instance.leaseRenewalIntervalInSeconds=1");
//    }
//
//    @AfterClass
//    public static void closeEureka() {
//        eurekaServer.close();
//    }
//
//    @Before
//    public void setUp() {
//        studentRepository.deleteAll();
//    }
//
//    @Test
//    public void testGetStudentById() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
//
//        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
//                .content(mapToJson(Collections.singletonList(first.getId()))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(true));
//    }
//
//    @Test
//    public void testGetStudentByIdWithFalse() throws Exception {
//        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
//
//        mockMvc.perform(post("/student-api/").contentType(MediaType.APPLICATION_JSON)
//                .content(mapToJson(Collections.singletonList(first.getId()))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(false));
//    }
//
//    private String mapToJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }
//
//    @Configuration
//    @EnableAutoConfiguration
//    @EnableEurekaServer
//    static class EurekaServer {
//    }
}
