package liga.school.sevice.service;

import liga.school.sevice.dto.PaginationSchoolDto;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.dto.Sorter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolServiceImplITest {

    @Mock
    private SchoolService schoolService;


    @Test
    public void testGetAll() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        Sorter sorter = new Sorter(0, 1, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = new PaginationSchoolDto(sorter);
        when(schoolService.getAll(paginationSchoolDto)).thenReturn(Collections.singletonList(schoolDTO));
        schoolService.getAll(paginationSchoolDto);
        verify(schoolService).getAll(paginationSchoolDto);
    }

    @Test
    public void testGetById() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.getById(schoolDTO.getId())).thenReturn(schoolDTO);
        schoolService.getById(schoolDTO.getId());
        verify(schoolService).getById(schoolDTO.getId());
    }

    @Test
    public void testExistById() {
        Long id = 1L;
        when(schoolService.existById(id)).thenReturn(true);
        schoolService.existById(id);
        verify(schoolService).existById(id);
    }


    @Test
    public void testCreate() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.create(schoolDTO)).thenReturn(schoolDTO);
        schoolService.create(schoolDTO);
        verify(schoolService).create(schoolDTO);
    }

    @Test
    public void testUpdate() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.update(schoolDTO)).thenReturn(schoolDTO);
        schoolService.update(schoolDTO);
        verify(schoolService).update(schoolDTO);
    }

    @Test
    public void testRemove() {
        Long id = 1L;
        doNothing().when(schoolService).remove(id);
        schoolService.remove(id);
        verify(schoolService).remove(id);
    }
}
