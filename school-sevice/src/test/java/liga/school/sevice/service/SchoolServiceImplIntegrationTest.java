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

import java.util.Arrays;
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

    @Test
    public void testGetAll() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.
                create(SchoolDTO.builder().name("name2").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllById() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(schoolDTO.getId()).build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }


    @Test
    public void testGetAllByIdEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("supername").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(3L).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByName() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name(schoolDTO.getName()).build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameLike() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService
                .create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService
                .create(SchoolDTO.builder().name("supername").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("supername").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("2").build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddress() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address2").studentIds(studentIds).build());
        schoolService.
                create(SchoolDTO.builder().name("supername").address("superaddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("2").build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddressLike() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address2").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.
                create(SchoolDTO.builder().name("supername").address("superaddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("address").build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddressEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("address2").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("supername").address("superad").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("y").build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByStudentIds() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Collections.singletonList("2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("address").studentIds(studentIds2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).studentIds(studentIds).build();
        paginationSchoolDto.setName(schoolDTO.getName());
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByStudentIdsMany() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Arrays.asList("1", "2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService
                .create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService
                .create(SchoolDTO.builder().name("supername").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).studentIds(studentIds2).build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByStudentIdsEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Collections.singletonList("2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("supername").address("address").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).studentIds(studentIds2).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndName() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").id(schoolDTO.getId()).build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndNameEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("sa").id(schoolDTO.getId()).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndAddress() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(schoolDTO.getId()).address("dr").build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndAddressEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(schoolDTO.getId()).address("sa").build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndStudentIds() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Arrays.asList("1", "2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(schoolDTO.getId()).studentIds(studentIds).build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByIdAndStudentIdsEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Arrays.asList("1", "2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("address").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).id(schoolDTO.getId()).studentIds(Collections.singletonList("3")).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }
    @Test
    public void testGetAllByNameAndAddress() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").address("maddress").build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameAndAddressTwoItems() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.
                create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").address("address").build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameAndAddressEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("1").address("y").build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameAndStudentIds() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Collections.singletonList("2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").studentIds(studentIds2).build();
        assertEquals(Collections.singletonList(schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameAndStudentIdsTwoItems() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Arrays.asList("1", "2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("name").studentIds(studentIds2).build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByNameAndStudentIdsEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).name("1").studentIds(Collections.singletonList("3")).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddressAndStudentIds() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Collections.singletonList("2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds2).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("m").studentIds(studentIds).build();
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddressAndStudentIdsTwoItems() {
        List<String> studentIds = Collections.singletonList("1");
        List<String> studentIds2 = Arrays.asList("1", "2");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        when(feignService.existsStudentsByIds(studentIds2)).thenReturn(true);
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        SchoolDTO schoolDTO2 = schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("address").studentIds(studentIds2).build();
        assertEquals(Arrays.asList(schoolDTO, schoolDTO2), schoolService.getAll(paginationSchoolDto));
    }

    @Test
    public void testGetAllByAddressAndStudentIdsEmpty() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);
        schoolService.create(SchoolDTO.builder().name("name1").address("maddress").studentIds(studentIds).build());
        schoolService.create(SchoolDTO.builder().name("name2").address("saddress").studentIds(studentIds).build());
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto
                .builder().sorter(sorter).address("x").studentIds(Collections.singletonList("3")).build();
        assertEquals(Collections.emptyList(), schoolService.getAll(paginationSchoolDto));
    }


    @Test
    public void testGetById() {
        List<String> studentIds = Collections.singletonList("1");
        when(feignService.existsStudentsByIds(studentIds)).thenReturn(true);

        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(studentIds).build());

        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

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
        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
        PaginationSchoolDto paginationSchoolDto = PaginationSchoolDto.builder().sorter(sorter).build();
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
