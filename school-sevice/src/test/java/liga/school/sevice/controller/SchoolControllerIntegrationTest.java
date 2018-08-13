package liga.school.sevice.controller;

import liga.school.sevice.SchoolClientService;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.service.StudentService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

//    @Before
//    public void setUp() {
//        schoolRepository.deleteAll();
//    }
//
//    @Test
//    public void testGetStudents() throws Exception {
//        when(studentFeignService.existsAllStudentsByIds(any())).thenReturn(true);
//        SchoolDto first = schoolService.create(SchoolDto.builder().
//                name("n").address("a").studentIds(Collections.singletonList("1")).build());
//        SchoolDto second = schoolService.create(SchoolDto.builder().name("n1").
//                address("a1").studentIds(Collections.singletonList("2")).build());
//        List<SchoolDto> schoolDtos = Arrays.asList(first, second);
//
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
////        SchoolFindDto schoolFindDto = SchoolFindDto.builder().sorter(sorter).build();
//
////        mockMvc.perform(get("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(schoolFindDto)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$[0].id").value(first.getId()))
////                .andExpect(jsonPath("$[0].name").value(first.getName()))
////                .andExpect(jsonPath("$[0].address").value(first.getAddress()))
////                .andExpect(jsonPath("$[0].studentIds[0]").value(first.getStudentIds().get(0)))
////                .andExpect(jsonPath("$[1].id").value(second.getId()))
////                .andExpect(jsonPath("$[1].name").value(second.getName()))
////                .andExpect(jsonPath("$[1].address").value(second.getAddress()))
////                .andExpect(jsonPath("$[1].studentIds[0]").value(second.getStudentIds().get(0)));
//    }
//
//    @Test
//    @Sql(scripts = "/scripts/initSchools.sql")
//    public void testGetStudentById() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        School school = schoolRepository.getOne(1L);
////        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
////        SchoolDto first = schoolService.create(SchoolDto.builder().id(1L).
////                name("n").address("a").studentIds(studentIds).build());
//
//
//        mockMvc.perform(get("/schools/{id}", school.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(school.getId()))
//                .andExpect(jsonPath("$.name").value(school.getName()))
//                .andExpect(jsonPath("$.address").value(school.getAddress()));
////                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
//    }
//
//    @Test
//    public void testGetStudentByIdWithSchoolNotFoundException() throws Exception {
//        mockMvc.perform(get("/schools/{id}", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("School not found"));
//    }
//
//    @Test
//    public void testCreateStudent() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        SchoolDto school = SchoolDto.builder().
//                name("n").address("a").studentIds(studentIds).build();
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//
//        mockMvc.perform(put("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.name").value(school.getName()))
//                .andExpect(jsonPath("$.address").value(school.getAddress()))
//                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
//    }
//
//    @Test
//    public void testCreateStudentWithFalse() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        SchoolDto school = SchoolDto.builder().
//                name("n").address("a").studentIds(studentIds).build();
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(false);
//
//        mockMvc.perform(put("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.error").value("Student not found"));
//    }
//
//    @Test
//    public void testUpdateStudent() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto school = schoolService.create(SchoolDto.builder().id(1L).
//                name("n").address("a").studentIds(Collections.singletonList("1")).build());
//        school.setAddress("aaa");
//
//        mockMvc.perform(post("/schools").contentType(MediaType.APPLICATION_JSON).content(mapToJson(school)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(school.getId()))
//                .andExpect(jsonPath("$.name").value(school.getName()))
//                .andExpect(jsonPath("$.address").value(school.getAddress()))
//                .andExpect(jsonPath("$.studentIds[0]").value(school.getStudentIds().get(0)));
//    }
//
//    @Test
//    public void testUpdateStudentWithSchoolNotFoundException() throws Exception {
//        List<String> studentIds = Collections.singletonList("1");
//        SchoolDto school = SchoolDto.builder().id(1L).
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
//        SchoolDto school = schoolService.create(SchoolDto.builder().id(1L).
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
//        SchoolDto first = schoolService.create(SchoolDto.builder().id(1L).
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
//    private String mapToJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }

//    @Configuration
//    @EnableAutoConfiguration
//    @EnableEurekaServer
//    static class EurekaServer {
//    }
}

