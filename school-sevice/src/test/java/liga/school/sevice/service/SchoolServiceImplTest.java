package liga.school.sevice.service;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import liga.school.sevice.transport.mapper.SchoolMapper;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Set;

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
        Set<String> studentIds = Sets.newLinkedHashSet("1");
        School school = School.builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        SchoolOutComeDto schoolDTO = SchoolOutComeDto.builder()
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(schoolRepository.findAll(
                any(Specification.class),
                any(Pageable.class))).thenReturn(new PageImpl(Collections.singletonList(school)));
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        schoolService.getAll(new SchoolFindDto(), PageRequest.of(0, 10));

        verify(schoolRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testGetById() {
        Set<String> studentIds = Sets.newLinkedHashSet("1");
        School school = School.builder()
                .id(1L).name("name").address("address").studentIds(studentIds).build();
        SchoolOutComeDto schoolDTO = SchoolOutComeDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(schoolRepository.findById(schoolDTO.getId())).thenReturn(java.util.Optional.ofNullable(school));
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        schoolService.getById(schoolDTO.getId());

        verify(schoolRepository, times(1)).findById(schoolDTO.getId());
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testCreate() {
        Set<String> studentIds = Sets.newLinkedHashSet("1");
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name")
                .address("address")
                .studentIds(studentIds).build();
        School school = School.builder()
                .id(1L).name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder().id(1L).name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(mapper.toEntity(schoolCreateDto)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolOutComeDto);

        schoolService.create(schoolCreateDto);

        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(mapper, times(1)).toEntity(schoolCreateDto);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testUpdate() {
        Set<String> studentIds = Sets.newLinkedHashSet("1");
        SchoolUpdateDto schoolDTO = SchoolUpdateDto.builder()
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
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(studentIds)
                .build();
        when(schoolRepository.getOne(schoolDTO.getId())).thenReturn(school);
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolOutComeDto);

        schoolService.update(schoolDTO);

        verify(schoolRepository, times(1)).getOne(schoolDTO.getId());
        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
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
