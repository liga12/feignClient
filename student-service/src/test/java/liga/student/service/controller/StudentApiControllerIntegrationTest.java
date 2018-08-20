package liga.student.service.controller;

import liga.student.service.StudentServiceApplication;
import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.service.MongoConfig;
import org.assertj.core.util.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static liga.student.service.util.Converter.mapToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentServiceApplication.class, MongoConfig.class})
@AutoConfigureMockMvc
public class StudentApiControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetStudentByIds() throws Exception {
        Student student = Student.builder().
                name("n")
                .surname("s")
                .age(20)
                .build();
        Student student2 = Student.builder()
                .name("n")
                .surname("s")
                .age(20)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);

        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(Sets.newLinkedHashSet(student.getId(), student2.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testGetStudentByIdsWithFalse() throws Exception {
        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(Sets.newLinkedHashSet("1"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testGetStudentByIdsWithNull() throws Exception {
        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetStudentByIdsWithEmpty() throws Exception {
        mockMvc.perform(post("/student-api").contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(Sets.newLinkedHashSet())))
                .andExpect(status().isOk());
    }

}

