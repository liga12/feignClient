package liga.student.service.controller;

import liga.student.service.StudentClientService;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.service.MongoConfig;
import liga.student.service.service.StudentService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentClientService.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

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

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

//    @Test
//    public void testGetStudents() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
//        StudentDTO second = studentService.create(StudentDTO.builder().name("n2").surname("s2").age(20).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).build();
//
//        mockMvc.perform(post("/student/")
//                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(pagination)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(first.getId()))
//                .andExpect(jsonPath("$[0].name").value(first.getName()))
//                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
//                .andExpect(jsonPath("$[0].age").value(first.getAge()))
//                .andExpect(jsonPath("$[1].id").value(second.getId()))
//                .andExpect(jsonPath("$[1].name").value(second.getName()))
//                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
//                .andExpect(jsonPath("$[1].age").value(second.getAge()));
//    }
//
//    @Test
//    public void testGetStudentsWithNull() throws Exception {
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        PaginationStudentDto pagination = PaginationStudentDto.builder().sorter(sorter).build();
//        mockMvc.perform(post("/student/").contentType(MediaType.APPLICATION_JSON).content(mapToJson(pagination)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//    @Test
//    public void testGetStudentsTextSearch() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
//        StudentDTO second = studentService.create(StudentDTO.builder().name("n1").surname("s").age(20).build());
//        Sorter sorter = new Sorter(0, 2, Sort.Direction.ASC, "id");
//        StudentFindByTextSearchDto paginationSearch = StudentFindByTextSearchDto.builder()
//                .sorter(sorter).caseSensitive(false).text("n s").build();
//
//        mockMvc.perform(post("/student/textSearch")
//                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(paginationSearch)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(first.getId()))
//                .andExpect(jsonPath("$[0].name").value(first.getName()))
//                .andExpect(jsonPath("$[0].surname").value(first.getSurname()))
//                .andExpect(jsonPath("$[0].age").value(first.getAge()))
//                .andExpect(jsonPath("$[1].id").value(second.getId()))
//                .andExpect(jsonPath("$[1].name").value(second.getName()))
//                .andExpect(jsonPath("$[1].surname").value(second.getSurname()))
//                .andExpect(jsonPath("$[1].age").value(second.getAge()));
//    }
//
//    @Test
//    public void testGetStudentsTextSearchWithNull() throws Exception {
//        Sorter sorter = new Sorter(0, 1, Sort.Direction.ASC, "id");
//        StudentFindByTextSearchDto paginationSearch = StudentFindByTextSearchDto.builder()
//                .sorter(sorter).caseSensitive(false).text("n").build();
//        mockMvc.perform(post("/student/textSearch")
//                .contentType(MediaType.APPLICATION_JSON).content(mapToJson(paginationSearch)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//    @Test
//    public void testGetStudentById() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());
//
//        mockMvc.perform(get("/student/{id}", first.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(first.getId()))
//                .andExpect(jsonPath("$.name").value(first.getName()))
//                .andExpect(jsonPath("$.surname").value(first.getSurname()))
//                .andExpect(jsonPath("$.age").value(first.getAge()));
//    }
//
//    @Test
//    public void testGetStudentByIdWithStudentNotFoundException() throws Exception {
//        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
//
//        mockMvc.perform(get("/student/{id}", first.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("Student not found"));
//    }
//
//    @Test
//    public void testCreateStudent() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().name("n").surname("s").age(20).build());
//
//        mockMvc.perform(put("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(first.getId()))
//                .andExpect(jsonPath("$.name").value(first.getName()))
//                .andExpect(jsonPath("$.surname").value(first.getSurname()))
//                .andExpect(jsonPath("$.age").value(first.getAge()));
//    }
//
//    @Test
//    public void testUpdateStudent() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());
//
//        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(first.getId()))
//                .andExpect(jsonPath("$.name").value(first.getName()))
//                .andExpect(jsonPath("$.age").value(first.getAge()))
//                .andExpect(jsonPath("$.surname").value(first.getSurname()));
//    }
//
//    @Test
//    public void testUpdateStudentWithStudentNotFoundException() throws Exception {
//        StudentDTO first = StudentDTO.builder().id("1").name("n").surname("s").age(20).build();
//
//        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(mapToJson(first)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("Student not found"));
//    }
//
//    @Test
//    public void testDeleteStudent() throws Exception {
//        StudentDTO first = studentService.create(StudentDTO.builder().id("1").name("n").surname("s").age(20).build());
//
//        mockMvc.perform(delete("/student/{id}", first.getId()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteStudentWithStudentNotFoundException() throws Exception {
//        mockMvc.perform(delete("/student/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("Student not found"));
//    }
//
//    private String mapToJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }

    @Configuration
    @EnableAutoConfiguration
    @EnableEurekaServer
    static class EurekaServer {
    }
}
