package liga.school.sevice.service;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.transport.dto.SchoolDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/scripts/initSchools.sql")
@Transactional
public class SchoolServiceImplIntegrationTest {

    @Autowired
    private SchoolService schoolService;

    @MockBean
    private StudentService feignService;

    @Autowired
    SchoolRepository schoolRepository;

    @Test
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder().build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(5, result.getContent().size());
    }

    @Test
    public void testGetAllById() {
        Set<String> set = Stream.of("1", "2", "3").collect(toSet());
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(set)
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
    public void testGetAllByName() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2", "3"))
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
    public void testGetAllByNameLike() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        SchoolDto schoolDto2 = SchoolDto.builder()
                .id(3L)
                .name("name31")
                .address("address31")
                .studentIds(new HashSet<>(Arrays.asList("31", "32", "33")))
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
    public void testGetAllByAddress() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(Sets.newLinkedHashSet("1", "2", "3"))
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
    public void testGetAllByAddressEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("ss")
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    public void testGetAllByStudentIds() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("3"))
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test
    public void testGetAllByStudentIdsMany() {
        SchoolDto schoolDto1 = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        SchoolDto schoolDto2 = SchoolDto.builder()
                .id(4L)
                .name("name42")
                .address("address45")
                .studentIds(new HashSet<>(Arrays.asList("1", "2")))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("1"))
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Arrays.asList(schoolDto1, schoolDto2), result.getContent());
    }

    @Test
    public void testGetAllByStudentIdsEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("45"))
                .build();

        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    public void testGetById() {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(1L)
                .build();
        Page<SchoolDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testGetByIdWithSchoolNotFound() {
        schoolService.getById(10L);
    }

    @Test
    public void testCreate() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        List<School> all = schoolRepository.findAll();
        SchoolDto schoolDTO = schoolService.create(SchoolDto.builder()
                .id(45L)
                .name("name1")
                .address("address")
                .studentIds(new HashSet<>(Collections.singletonList("2")))
                .build());

        SchoolDto result = schoolService.getById(schoolDTO.getId());

        assertEquals(schoolDTO, result);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testCreateWithStudentNotFound() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(false);
        schoolService.create(SchoolDto.builder()
                .name("name1")
                .address("address")
                .studentIds(new HashSet<>(Collections.singletonList("2")))
                .build());

    }

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
