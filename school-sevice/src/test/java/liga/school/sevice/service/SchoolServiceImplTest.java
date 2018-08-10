package liga.school.sevice.service;

import liga.school.sevice.transport.dto.PaginationSchoolDto;
import liga.school.sevice.transport.dto.SchoolDTO;
import liga.school.sevice.transport.dto.Sorter;
import liga.school.sevice.domain.entity.School;
import liga.school.sevice.transport.mapper.SchoolMapper;
import liga.school.sevice.domain.repository.SchoolRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

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
        List<String> studentIds = Collections.singletonList("1");
        School school = School.builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(studentIds).build();
        Sorter sorter = new Sorter(0, 1, Sort.Direction.ASC, "id");
        int page = sorter.getPage();
        int size = sorter.getSize();
        Sort.Direction sortDirection = sorter.getSortDirection();
        String sortBy = sorter.getSortBy();
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, sortBy);
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto.builder().sorter(sorter).build();
        Page<School> schoolPage = new PageImpl<>(Collections.singletonList(school), pageRequest, 1);
        when(schoolRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(schoolPage);
        when(mapper.toDto(Collections.singletonList(school))).thenReturn(Collections.singletonList(schoolDTO));

        schoolService.getAll(paginationSchoolDto);

        verify(schoolRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).toDto(Collections.singletonList(school));
    }

    @Test
    public void testGetById() {
        List<String> studentIds = Collections.singletonList("1");
        School school = School.builder()
                .id(1L).name("name").address("address").studentIds(studentIds).build();
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        when(schoolRepository.findById(schoolDTO.getId())).thenReturn(java.util.Optional.ofNullable(school));
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        schoolService.getById(schoolDTO.getId());

        verify(schoolRepository, times(1)).findById(schoolDTO.getId());
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testExistById() {
        Long id = 1L;
        when(schoolRepository.existsById(id)).thenReturn(true);

//        schoolService.existById(id);

        verify(schoolRepository, times(1)).existsById(id);
    }

    @Test
    public void testCreate() {
        List<String> studentIds = Collections.singletonList("1");
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        School school = School
                .builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(mapper.toEntity(schoolDTO)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        schoolService.create(schoolDTO);

        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(mapper, times(1)).toEntity(schoolDTO);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testUpdate() {
        List<String> studentIds = Collections.singletonList("1");
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(studentIds).build();
        School school = School
                .builder().id(1L).name("name").address("address").studentIds(studentIds).build();
//        doReturn(true).when(schoolService).existById(schoolDTO.getId());
        when(studentFeignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
        when(mapper.toEntity(schoolDTO)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(mapper.toDto(school)).thenReturn(schoolDTO);

        schoolService.update(schoolDTO);

//        verify(schoolService, times(1)).existById(schoolDTO.getId());
        verify(studentFeignService, times(1)).existsAllStudentsByIds(studentIds);
        verify(mapper, times(1)).toEntity(schoolDTO);
        verify(schoolRepository, times(1)).save(school);
        verify(mapper, times(1)).toDto(school);
    }

    @Test
    public void testRemove() {
        Long id = 1L;
//        doReturn(true).when(schoolService).existById(id);
        doNothing().when(schoolRepository).deleteById(id);

        schoolService.remove(id);

        verify(schoolRepository, times(1)).deleteById(id);
    }
}
