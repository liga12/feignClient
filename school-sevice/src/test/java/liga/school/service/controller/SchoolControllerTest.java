package liga.school.service.controller;

import liga.school.service.service.SchoolService;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
import liga.school.service.util.Converter;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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

import java.util.Iterator;
import java.util.LinkedHashSet;

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
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolOutcomeDto schoolOutcomeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("name1")
                .address("address1")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolOutcomeDto2 = SchoolOutcomeDto.builder()
                .id(1L)
                .name("name2")
                .address("address2")
                .studentIds(studentIds)
                .build();
        when(schoolService.getAll(any(SchoolFindDto.class), any(Pageable.class))).
                thenReturn(new PageImpl<>(Lists.newArrayList(schoolOutcomeDto, schoolOutcomeDto2)));
        Iterator<String> iterator = schoolOutcomeDto.getStudentIds().iterator();
        Iterator<String> iterator2 = schoolOutcomeDto2.getStudentIds().iterator();

        mockMvc.perform(get("/schools?id=1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(schoolOutcomeDto.getId()))
                .andExpect(jsonPath("$.content[0].name").value(schoolOutcomeDto.getName()))
                .andExpect(jsonPath("$.content[0].address").value(schoolOutcomeDto.getAddress()))
                .andExpect(jsonPath("$.content[0].studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.content[0].studentIds[1]").value(iterator.next()))
                .andExpect(jsonPath("$.content[1].id").value(schoolOutcomeDto2.getId()))
                .andExpect(jsonPath("$.content[1].name").value(schoolOutcomeDto2.getName()))
                .andExpect(jsonPath("$.content[1].address").value(schoolOutcomeDto2.getAddress()))
                .andExpect(jsonPath("$.content[1].studentIds[0]").value(iterator2.next()))
                .andExpect(jsonPath("$.content[1].studentIds[1]").value(iterator2.next()));

        verify(schoolService, times(1)).getAll(any(SchoolFindDto.class), any(Pageable.class));
    }

    @Test
    public void testGetSchoolById() throws Exception {
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolOutcomeDto schoolOutcomeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.getById(schoolOutcomeDto.getId())).thenReturn(schoolOutcomeDto);
        Iterator<String> iterator = schoolOutcomeDto.getStudentIds().iterator();

        mockMvc.perform(get("/schools/{id}", schoolOutcomeDto.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolOutcomeDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolOutcomeDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolOutcomeDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()));

        verify(schoolService).getById(schoolOutcomeDto.getId());
    }

    @Test
    public void testCreateSchool() throws Exception {
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto outComeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.create(schoolCreateDto)).thenReturn(outComeDto);
        Iterator<String> iterator = schoolCreateDto.getStudentIds().iterator();

        mockMvc.perform(put("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Converter.mapToJson(schoolCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(outComeDto.getId()))
                .andExpect(jsonPath("$.name").value(outComeDto.getName()))
                .andExpect(jsonPath("$.address").value(outComeDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()));

        verify(schoolService).create(schoolCreateDto);
    }

    @Test
    public void testUpdateSchool() throws Exception {
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolOutcomeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("n")
                .address("a")
                .studentIds(studentIds)
                .build();
        when(schoolService.update(schoolUpdateDto)).thenReturn(schoolOutcomeDto);
        Iterator<String> iterator = schoolUpdateDto.getStudentIds().iterator();

        mockMvc.perform(post("/schools")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Converter.mapToJson(schoolUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolUpdateDto.getId()))
                .andExpect(jsonPath("$.name").value(schoolUpdateDto.getName()))
                .andExpect(jsonPath("$.address").value(schoolUpdateDto.getAddress()))
                .andExpect(jsonPath("$.studentIds[0]").value(iterator.next()))
                .andExpect(jsonPath("$.studentIds[1]").value(iterator.next()));

        verify(schoolService).update(schoolUpdateDto);
    }

    @Test
    public void testDeleteSchool() throws Exception {
        LinkedHashSet<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolOutcomeDto first = SchoolOutcomeDto.builder()
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
}
