package liga.school.sevice.service;

import liga.school.sevice.dto.SchoolDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolServiceImplITest {

    @Mock
    private SchoolService schoolService;

    @MockBean
    private StudentService studentFeignService;


    @Test
    public void testGetAll() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.getAll()).thenReturn(Collections.singletonList(schoolDTO));
        schoolService.getAll();
        verify(schoolService).getAll();
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
    public void testGetByName() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.getByName(schoolDTO.getName())).thenReturn(Collections.singletonList(schoolDTO));
        schoolService.getByName(schoolDTO.getName());
        verify(schoolService).getByName(schoolDTO.getName());
    }

    @Test
    public void testGetByAddress() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.getByAddress(schoolDTO.getAddress())).thenReturn(Collections.singletonList(schoolDTO));
        schoolService.getByAddress(schoolDTO.getAddress());
        verify(schoolService).getByAddress(schoolDTO.getAddress());
    }

    @Test
    public void testCreate() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.create(schoolDTO)).thenReturn(schoolDTO);
        when(studentFeignService.getStudentById("1")).thenReturn("1");
        schoolService.create(schoolDTO);
        verify(schoolService).create(schoolDTO);
    }

    @Test
    public void testUpdate() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().name("name").address("address").studentIds(Collections.singletonList("1")).build();
        when(schoolService.update(schoolDTO)).thenReturn(schoolDTO);
        when(studentFeignService.getStudentById("1")).thenReturn("1");
        schoolService.update(schoolDTO);
        verify(schoolService).update(schoolDTO);
    }

    @Test
    public void testRemove() {
        SchoolDTO schoolDTO = SchoolDTO
                .builder().id(1L).name("name").address("address").studentIds(Collections.singletonList("1")).build();
        doNothing().when(schoolService).remove(schoolDTO);
        schoolService.remove(schoolDTO);
        verify(schoolService).remove(schoolDTO);
    }
}
