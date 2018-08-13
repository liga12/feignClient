package liga.school.sevice.service;

import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.transport.dto.SchoolDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SchoolServiceImplIntegrationTest {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Mock
    private StudentService feignService;

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder().build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(5, result.getContent().size());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllById() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2","3"))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(1L)
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }


    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByIdEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(10L)
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);


        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByName() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2","3"))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("me12")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByNameLike() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2","3"))
                .build();
        SchoolDto schoolDto2 = SchoolDto.builder()
                .id(1L)
                .name("name31")
                .address("address31")
                .studentIds(Sets.newLinkedHashSet("31", "32","33"))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("1")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Arrays.asList(schoolDto, schoolDto2), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByNameEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("ss")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddress() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2","3"))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .address("ress1")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddressLike() {
            PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
            SchoolFindDto schoolFindDto = SchoolFindDto
                    .builder()
                    .name("1")
                    .build();

            Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddressEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("ss")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

//    @Test
//    public void testGetAllByStudentIds() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Collections.singletonList("2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("address").studentIds(studentIds2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).studentIds(studentIds).build();
//        schoolFindDto.setName(schoolDTO.getName());
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByStudentIdsMany() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Arrays.asList("1", "2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService
//                .create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        SchoolDto schoolDto2 = schoolService
//                .create(SchoolDto.builder().name("supername").address("address").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).studentIds(studentIds2).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Arrays.asList(schoolDTO, schoolDto2), schools);
//    }
//
//    @Test
//    public void testGetAllByStudentIdsEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Collections.singletonList("2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        schoolService.create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("supername").address("address").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).studentIds(studentIds2).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndName() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("name").id(schoolDTO.getId()).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndNameEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("sa").id(schoolDTO.getId()).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndAddress() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).id(schoolDTO.getId()).address("dr").build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndAddressEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).id(schoolDTO.getId()).address("sa").build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndStudentIds() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Arrays.asList("1", "2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).id(schoolDTO.getId()).studentIds(studentIds).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByIdAndStudentIdsEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Arrays.asList("1", "2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).id(schoolDTO.getId()).studentIds(Collections.singletonList("3")).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndAddress() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("name").address("maddress").build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndAddressTwoItems() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        SchoolDto schoolDto2 = schoolService.
//                create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("name").address("address").build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Arrays.asList(schoolDTO, schoolDto2), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndAddressEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        schoolService.create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("1").address("y").build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndStudentIds() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Collections.singletonList("2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        schoolService.create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        SchoolDto schoolDto2 = schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("name").studentIds(studentIds2).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDto2), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndStudentIdsTwoItems() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Arrays.asList("1", "2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        SchoolDto schoolDto2 = schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("name").studentIds(studentIds2).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Arrays.asList(schoolDTO, schoolDto2), schools);
//    }
//
//    @Test
//    public void testGetAllByNameAndStudentIdsEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        schoolService.create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).name("1").studentIds(Collections.singletonList("3")).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//    @Test
//    public void testGetAllByAddressAndStudentIds() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Collections.singletonList("2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds2).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).address("m").studentIds(studentIds).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.singletonList(schoolDTO), schools);
//    }
//
//    @Test
//    public void testGetAllByAddressAndStudentIdsTwoItems() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIds2 = Arrays.asList("1", "2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIds2)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        SchoolDto schoolDto2 = schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).address("address").studentIds(studentIds2).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Arrays.asList(schoolDTO, schoolDto2), schools);
//    }
//
//    @Test
//    public void testGetAllByAddressAndStudentIdsEmpty() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        schoolService.create(SchoolDto.builder().name("name1").address("maddress").studentIds(studentIds).build());
//        schoolService.create(SchoolDto.builder().name("name2").address("saddress").studentIds(studentIds).build());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto
//                .builder().sorter(sorter).address("x").studentIds(Collections.singletonList("3")).build();
//
//        List<SchoolDto> schools = schoolService.getAll(schoolFindDto);
//
//        assertEquals(Collections.emptyList(), schools);
//    }
//
//
//    @Test
//    public void testGetById() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//
//        SchoolDto result = schoolService.getById(schoolDTO.getId());
//
//        assertEquals(schoolDTO, result);
//    }
//
//    @Test(expected = SchoolNotFoundException.class)
//    public void testGetByIdWithSchoolNotFound() {
//        schoolService.getById(1L);
//    }
//
//    @Test
//    public void testExistById() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//
////        boolean result = schoolService.existById(schoolDTO.getId());
//
////        assertTrue(result);
//    }
//
////    @Test(expected = SchoolNotFoundException.class)
////    public void testExistByIdWithSchoolNotFound() {
////        schoolService.existById(1L);
////    }
//
//    @Test
//    public void testCreate() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name1").address("address").studentIds(studentIds).build());
//
//        SchoolDto result = schoolService.getById(schoolDTO.getId());
//
//        assertEquals(schoolDTO, result);
//    }
//
//    @Test(expected = StudentNotFoundException.class)
//    public void testCreateWithStudentNotFound() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(false);
//
//        schoolService.create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//    }
//
//    @Test
//    public void testUpdate() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//        schoolDTO.setName("n");
//        schoolDTO.setAddress("s");
//
//        SchoolDto updatedSchoolDto = schoolService.update(schoolDTO);
//
//        assertEquals(schoolDTO, updatedSchoolDto);
//    }
//
//    @Test(expected = SchoolNotFoundException.class)
//    public void testUpdateWithSchoolNotFound() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//        schoolDTO.setId(22L);
//        schoolDTO.setName("n");
//        schoolDTO.setAddress("s");
//
//        schoolService.update(schoolDTO);
//    }
//
//    @Test(expected = StudentNotFoundException.class)
//    public void testUpdateWithStudentNotFound() {
//        List<String> studentIds = Collections.singletonList("1");
//        List<String> studentIdsUpdate = Collections.singletonList("2");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        when(feignService.existsAllStudentsByIds(studentIdsUpdate)).thenReturn(false);
//        SchoolDto schoolDTO = schoolService.
//                create(SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//        schoolDTO.setName("n");
//        schoolDTO.setAddress("s");
//        schoolDTO.setStudentIds(studentIdsUpdate);
//
//        schoolService.update(schoolDTO);
//    }
//
//    @Test
//    public void testRemove() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        SchoolDto schoolDTO = schoolService.create(
//                SchoolDto.builder().name("name").address("address").studentIds(studentIds).build());
//        schoolService.remove(schoolDTO.getId());
//        Sorter sorter = new Sorter(0, 10, Sort.Direction.ASC, "id");
//        SchoolFindDto schoolFindDto = SchoolFindDto.builder().sorter(sorter).build();
//
//        List<SchoolDto> all = schoolService.getAll(schoolFindDto);
//
//        assertEquals(0, all.size());
//    }
//
//    @Test(expected = SchoolNotFoundException.class)
//    public void testRemoveSchoolNotFound() {
//        List<String> studentIds = Collections.singletonList("1");
//        when(feignService.existsAllStudentsByIds(studentIds)).thenReturn(true);
//        schoolService.remove(1L);
//    }
}
