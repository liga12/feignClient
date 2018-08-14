package liga.school.sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.school.sevice.service.SchoolService;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SchoolControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SchoolService schoolService;

    @InjectMocks
    private SchoolController schoolController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetSchools() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Collections.singletonList("1"));
        SchoolOutComeDto first = SchoolOutComeDto.builder()
                .id(1L)
                .name("name1")
                .address("address1")
                .studentIds(studentIds)
                .build();
        SchoolOutComeDto second = SchoolOutComeDto.builder()
                .id(1L)
                .name("name2")
                .address("address2")
                .studentIds(studentIds)
                .build();
        List<SchoolOutComeDto> schoolDtos = Arrays.asList(first, second);
        when(schoolService.getAll(any(SchoolFindDto.class), any(Pageable.class))).thenReturn(new PageImpl<>(schoolDtos));

        mockMvc.perform(get("/schools?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(first.getId()))
                .andExpect(jsonPath("$.content[0].name").value(first.getName()))
                .andExpect(jsonPath("$.content[0].address").value(first.getAddress()))
                .andExpect(jsonPath("$.content[0].studentIds[0]").value(first.getStudentIds().iterator().next()))
                .andExpect(jsonPath("$.content[1].id").value(second.getId()))
                .andExpect(jsonPath("$.content[1].name").value(second.getName()))
                .andExpect(jsonPath("$.content[1].address").value(second.getAddress()))
                .andExpect(jsonPath("$.content[1].studentIds[0]").value(second.getStudentIds().iterator().next()));

        verify(schoolService, times(1)).getAll(any(), any());
    }

    @Test
    public void testGetSchoolById() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Collections.singletonList("1"));
        SchoolOutComeDto first = SchoolOutComeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.getById(first.getId())).thenReturn(first);

        mockMvc.perform(get("/schools/{id}", first.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(first.getId()))
                .andExpect(jsonPath("$.name").value(first.getName()))
                .andExpect(jsonPath("$.address").value(first.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(first.getStudentIds().iterator().next()));

        verify(schoolService).getById(first.getId());
    }

    @Test
    public void testCreateSchool() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Collections.singletonList("1"));
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        SchoolOutComeDto outComeDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.create(schoolCreateDto)).thenReturn(outComeDto);

        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(outComeDto.getId()))
                .andExpect(jsonPath("$.name").value(outComeDto.getName()))
                .andExpect(jsonPath("$.address").value(outComeDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(outComeDto.getStudentIds().iterator().next()));

        verify(schoolService).create(schoolCreateDto);
    }

    @Test
    public void testUpdateSchool() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Collections.singletonList("1"));
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        SchoolOutComeDto outComeDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.update(schoolUpdateDto)).thenReturn(outComeDto);

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolUpdateDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolUpdateDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolUpdateDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(schoolUpdateDto.getStudentIds().iterator().next()));

        verify(schoolService).update(schoolUpdateDto);
    }

    @Test
    public void testDeleteSchool() throws Exception {
        HashSet<String> studentIds = new HashSet<>(Collections.singletonList("1"));
        SchoolOutComeDto first = SchoolOutComeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        doNothing().when(schoolService).remove(first.getId());

        mockMvc.perform(delete("/schools/{id}", first.getId()))
                .andExpect(status().isOk());

        verify(schoolService).remove(first.getId());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
