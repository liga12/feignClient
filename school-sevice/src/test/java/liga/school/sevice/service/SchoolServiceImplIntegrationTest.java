package liga.school.sevice.service;

import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.PaginationSchoolDto;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.dto.Sorter;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolServiceImplIntegrationTest {

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private StudentService feignService;

    @Before
    public void setUp() {
        schoolRepository.deleteAll();
    }
//Todo create test
//    @Test
//    public void testGetAllByName() {
//        SchoolDTO schoolDTO = schoolService.
//                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
//        SchoolDTO schoolDTO2 = schoolService.
//                create(SchoolDTO.builder().name("name2").address("address2").studentIds(Collections.singletonList("1")).build());
//        Sorter sorter = new Sorter(1,10, Sort.Direction.ASC, "id" );
//        PaginationSchoolDto paginationSchoolDto = new PaginationSchoolDto(sorter);
//        assertEquals(1, schoolService.getAll().size());
//    }

    @Test
    public void testGetById() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);

        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());

        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    //
    @Test(expected = SchoolNotFoundException.class)
    public void testGetByIdWithSchoolNotFound() {
        schoolService.getById(1L);
    }

    @Test
    public void testCreate() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    @Test(expected = StudentNotFoundException.class)
    public void testCreateWithStudentNotFound() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(false);
        schoolService.create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
    }

    @Test
    public void testUpdate() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        schoolDTO.setName("n");
        schoolDTO.setAddress("s");
        SchoolDTO updatedSchoolDTO = schoolService.update(schoolDTO);
        assertEquals(schoolDTO, updatedSchoolDTO);
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testUpdateWithSchoolNotFound() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        schoolDTO.setId(22L);
        schoolDTO.setName("n");
        schoolDTO.setAddress("s");
        schoolService.update(schoolDTO);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testUpdateWithStudentNotFound() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIdsUpdate = Collections.singletonList("2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIdsUpdate)).thenReturn(false);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        schoolDTO.setName("n");
        schoolDTO.setAddress("s");
        schoolDTO.setStudentIds(studentIdsUpdate);
        schoolService.update(schoolDTO);
    }

    @Test
    public void testRemove() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.create(
                SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        schoolService.remove(schoolDTO.getId());
        Sorter sorter = new Sorter(0,10, Sort.Direction.ASC,"id");
        PaginationSchoolDto paginationSchoolDto = new PaginationSchoolDto(sorter);
        List<SchoolDTO> all = schoolService.getAll(paginationSchoolDto);
        assertEquals(0, all.size());
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testRemoveSchoolNotFound() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.remove(1L);
    }
}
