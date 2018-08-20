package liga.student.service.controller;

import liga.student.service.StudentServiceApplication;
import liga.student.service.domain.entity.Student;
import liga.student.service.domain.repository.StudentRepository;
import liga.student.service.service.MongoConfig;
import liga.student.service.transport.dto.StudentCreateDto;
import liga.student.service.transport.dto.StudentUpdateDto;
import liga.student.service.transport.mapper.StudentMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static liga.student.service.util.Converter.mapToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StudentServiceApplication.class, MongoConfig.class})
@AutoConfigureMockMvc

public class StudentControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    StudentMapper mapper;

    @Before
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testGetStudents() throws Exception {
        Student student = Student.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        studentRepository.save(student);
        List<Student> students = studentRepository.findAll();

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(students.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[0].surname").value(students.get(0).getSurname()))
                .andExpect(jsonPath("$[0].age").value(students.get(0).getAge()));
    }

    @Test
    public void testGetStudentsWithNull() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetStudentsTextSearch() throws Exception {
        Student student = Student.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        Student student2 = Student.builder()
                .id("2")
                .name("n2")
                .surname("n")
                .age(20)
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        List<Student> students = studentRepository.findAll();

        mockMvc.perform(get("/students/textSearch?text=n"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(students.get(1).getId()))
                .andExpect(jsonPath("$[0].name").value(students.get(1).getName()))
                .andExpect(jsonPath("$[0].surname").value(students.get(1).getSurname()))
                .andExpect(jsonPath("$[0].age").value(students.get(1).getAge()))
                .andExpect(jsonPath("$[1].id").value(students.get(0).getId()))
                .andExpect(jsonPath("$[1].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[1].surname").value(students.get(0).getSurname()))
                .andExpect(jsonPath("$[1].age").value(students.get(0).getAge()));
    }

    @Test
    public void testGetStudentsTextSearchWithNull() throws Exception {
        mockMvc.perform(get("/students/textSearch"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetStudentsTextSearchWithTextEmpty() throws Exception {
        mockMvc.perform(get("/students/textSearch?text"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = Student.builder()
                .id("1")
                .name("n")
                .surname("n")
                .age(20)
                .build();
        studentRepository.save(student);
        Student studentById = studentRepository.findById("1").orElseThrow(AssertionError::new);

        mockMvc.perform(get("/students/{id}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentById.getId()))
                .andExpect(jsonPath("$.name").value(studentById.getName()))
                .andExpect(jsonPath("$.surname").value(studentById.getSurname()))
                .andExpect(jsonPath("$.age").value(studentById.getAge()));
    }

    @Test
    public void testGetStudentByIdWithStudentNotFoundException() throws Exception {

        mockMvc.perform(get("/students/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("n")
                .age(20)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(studentCreateDto.getName()))
                .andExpect(jsonPath("$.surname").value(studentCreateDto.getSurname()))
                .andExpect(jsonPath("$.age").value(studentCreateDto.getAge()));
    }

    @Test
    public void testCreateStudentWithNameNull() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name(null)
                .surname("n")
                .age(20)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithNameEmpty() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("")
                .surname("n")
                .age(20)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithSurnameNull() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .surname(null)
                .name("n")
                .age(20)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithSurnameEmpty() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("")
                .age(20)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithAgeLess1() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(0)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateStudentWithAgeMore120() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(121)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCreateStudentWithAgeNull() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(null)
                .build();

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateStudent() throws Exception {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(12)
                .build();
        studentRepository.save(student);
        Student studentById = studentRepository.findById(student.getId()).orElseThrow(AssertionError::new);
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id(studentById.getId())
                .name("n")
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentUpdateDto.getId()))
                .andExpect(jsonPath("$.name").value(studentUpdateDto.getName()))
                .andExpect(jsonPath("$.age").value(studentUpdateDto.getAge()))
                .andExpect(jsonPath("$.surname").value(studentUpdateDto.getSurname()));
    }

    @Test
    public void testUpdateStudentWithStudentNotFoundException() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    public void testUpdateStudentWithIdNull() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id(null)
                .name("n")
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithIdEmpty() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("")
                .name("n")
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithNameNull() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name(null)
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithNameEmpty() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("")
                .surname("s")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithSurnameNull() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname(null)
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithSurnameEmpty() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("")
                .age(20)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithAgeMore120() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname(null)
                .age(121)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithAgeLess1() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("")
                .age(0)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStudentWithAgeNull() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("")
                .age(null)
                .build();

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = Student.builder()
                .name("n")
                .surname("s")
                .age(12)
                .build();
        studentRepository.save(student);

        mockMvc.perform(delete("/students/{id}", student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteStudentWithStudentNotFoundException() throws Exception {
        mockMvc.perform(delete("/students/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

}
