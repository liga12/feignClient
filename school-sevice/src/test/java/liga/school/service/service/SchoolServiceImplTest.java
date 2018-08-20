package liga.school.service.service;

import liga.school.service.domain.entity.School;
import liga.school.service.domain.repository.SchoolRepository;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
import liga.school.service.transport.mapper.SchoolMapper;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SchoolServiceImplTest {
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private SchoolMapper mapper;
    @Mock
    private StudentService studentFeignService;

    @Spy
    @InjectMocks
    private SchoolServiceImpl schoolService;

    @Test
    public void testGetAll() {
        Set<String> studentIds = Sets.newLinkedHashSet("1","2");
        School school = School.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolDTO = SchoolOutcomeDto.builder()
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        PageImpl<School> page = new PageImpl(Lists.newArrayList(school));
        when(schoolRepository.findAll(
                any(Specification.class),
                any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        Page<SchoolOutcomeDto> result = schoolService.getAll(new SchoolFindDto(), PageRequest.of(0, 100));

        verify(schoolRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).toDto(school);
        assertEquals( page.map(mapper::toDto), result);

    }

    @Test
    public void testGetById() {
        Set<String> studentIds = Sets.newLinkedHashSet("1","2");
        School school = School.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolDTO = SchoolOutcomeDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(schoolRepository.findById(schoolDTO.getId())).thenReturn(java.util.Optional.ofNullable(school));
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        SchoolOutcomeDto result = schoolService.getById(schoolDTO.getId());

        verify(schoolRepository, times(1)).findById(schoolDTO.getId());
        verify(mapper, times(1)).toDto(school);
        assertEquals(schoolDTO, result);
    }

    @Test
    public void testCreate() {
        Set<String> studentIds = Sets.newLinkedHashSet("1", "2");
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name")
                .address("address")
                .studentIds(studentIds).build();
        School school = School.builder()
                .id(1L).name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolOutComeDto = SchoolOutcomeDto.builder().id(1L).name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(mapper.toEntity(schoolCreateDto)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolOutComeDto);

        SchoolOutcomeDto result = schoolService.create(schoolCreateDto);

        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(mapper, times(1)).toEntity(schoolCreateDto);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
        assertEquals(schoolOutComeDto, result);
    }

    @Test
    public void testUpdate() {
        Set<String> studentIds = Sets.newLinkedHashSet("1","2");
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        School school = School.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        SchoolOutcomeDto schoolOutComeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        doReturn(schoolOutComeDto).when(schoolService).getById(schoolUpdateDto.getId());
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(mapper.toEntity(schoolOutComeDto)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolOutComeDto);

        SchoolOutcomeDto result = schoolService.update(schoolUpdateDto);

        verify(schoolService, times(1)).getById(schoolUpdateDto.getId());
        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(mapper, times(1)).toEntity(schoolOutComeDto);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
        assertEquals(schoolOutComeDto, result);
    }

    @Test
    public void testRemove() {
        Long id = 1L;
        when(schoolRepository.existsById(id)).thenReturn(true);
        doNothing().when(schoolRepository).deleteById(id);

        schoolService.remove(id);

        verify(schoolRepository, times(1)).existsById(id);
        verify(schoolRepository, times(1)).deleteById(id);
    }
}
