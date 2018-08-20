package liga.student.service.controller;

import com.google.common.collect.Lists;
import liga.student.service.service.StudentService;
import liga.student.service.transport.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static liga.student.service.util.Converter.mapToJson;


@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetStudents() throws Exception {
        StudentOutcomeDto studentOutcomeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        StudentOutcomeDto studentOutcomeDto2 = StudentOutcomeDto.builder()
                .id("1")
                .name("n2")
                .surname("s2")
                .age(20).build();
        when(studentService.getAll(any(StudentFindDto.class), any(Pageable.class)))
                .thenReturn(Lists.newArrayList(studentOutcomeDto, studentOutcomeDto2));

        mockMvc.perform(get("/students")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentOutcomeDto.getId()))
                .andExpect(jsonPath("$[0].name").value(studentOutcomeDto.getName()))
                .andExpect(jsonPath("$[0].surname").value(studentOutcomeDto.getSurname()))
                .andExpect(jsonPath("$[0].age").value(studentOutcomeDto.getAge()))
                .andExpect(jsonPath("$[1].id").value(studentOutcomeDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(studentOutcomeDto2.getName()))
                .andExpect(jsonPath("$[1].surname").value(studentOutcomeDto2.getSurname()))
                .andExpect(jsonPath("$[1].age").value(studentOutcomeDto2.getAge()));

        verify(studentService, times(1)).getAll(any(StudentFindDto.class), any(Pageable.class));
    }

    @Test
    public void testGetStudentsTextSearch() throws Exception {
        StudentOutcomeDto studentOutcomeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        StudentOutcomeDto studentOutcomeDto2 = StudentOutcomeDto.builder()
                .id("1")
                .name("n2")
                .surname("s2")
                .age(20).build();
        when(studentService.getAll(any(StudentFindByTextSearchDto.class), any(Pageable.class)))
                .thenReturn(Lists.newArrayList(studentOutcomeDto, studentOutcomeDto2));

        mockMvc.perform(get("/students/textSearch?text=n")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(studentOutcomeDto.getId()))
                .andExpect(jsonPath("$[0].name").value(studentOutcomeDto.getName()))
                .andExpect(jsonPath("$[0].surname").value(studentOutcomeDto.getSurname()))
                .andExpect(jsonPath("$[0].age").value(studentOutcomeDto.getAge()))
                .andExpect(jsonPath("$[1].id").value(studentOutcomeDto2.getId()))
                .andExpect(jsonPath("$[1].name").value(studentOutcomeDto2.getName()))
                .andExpect(jsonPath("$[1].surname").value(studentOutcomeDto2.getSurname()))
                .andExpect(jsonPath("$[1].age").value(studentOutcomeDto2.getAge()));

        verify(studentService, times(1)).getAll(
                any(StudentFindByTextSearchDto.class),
                any(Pageable.class)
        );
    }

    @Test
    public void testGetStudentById() throws Exception {
        StudentOutcomeDto studentOutcomeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        when(studentService.getById(studentOutcomeDto.getId())).thenReturn(studentOutcomeDto);

        mockMvc.perform(get("/students/{id}", studentOutcomeDto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentOutcomeDto.getId()))
                .andExpect(jsonPath("$.name").value(studentOutcomeDto.getName()))
                .andExpect(jsonPath("$.surname").value(studentOutcomeDto.getSurname()))
                .andExpect(jsonPath("$.age").value(studentOutcomeDto.getAge()));

        verify(studentService, times(1)).getById(studentOutcomeDto.getId());
    }

    @Test
    public void testCreateStudent() throws Exception {
        StudentCreateDto studentCreateDto = StudentCreateDto.builder()
                .name("n")
                .surname("s")
                .age(20)
                .build();
        StudentOutcomeDto studentOutcomeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        when(studentService.create(studentCreateDto)).thenReturn(studentOutcomeDto);

        mockMvc.perform(put("/students").contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentCreateDto))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentOutcomeDto.getId()))
                .andExpect(jsonPath("$.name").value(studentOutcomeDto.getName()))
                .andExpect(jsonPath("$.surname").value(studentOutcomeDto.getSurname()))
                .andExpect(jsonPath("$.age").value(studentOutcomeDto.getAge()));

        verify(studentService, times(1)).create(studentCreateDto);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        StudentUpdateDto studentUpdateDto = StudentUpdateDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        StudentOutcomeDto studentOutcomeDto = StudentOutcomeDto.builder()
                .id("1")
                .name("n")
                .surname("s")
                .age(20)
                .build();
        when(studentService.update(studentUpdateDto)).thenReturn(studentOutcomeDto);

        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(studentUpdateDto))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentOutcomeDto.getId()))
                .andExpect(jsonPath("$.name").value(studentOutcomeDto.getName()))
                .andExpect(jsonPath("$.surname").value(studentOutcomeDto.getSurname()))
                .andExpect(jsonPath("$.age").value(studentOutcomeDto.getAge()));

        verify(studentService, times(1)).update(studentUpdateDto);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        String id = "1";
        doNothing().when(studentService).remove(id);

        mockMvc.perform(delete("/students/{id}", id))
                .andExpect(status().isOk());

        verify(studentService).remove(id);
    }
}