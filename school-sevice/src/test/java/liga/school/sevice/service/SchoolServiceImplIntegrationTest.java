package liga.school.sevice.service;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.domain.repository.SchoolRepository;
import liga.school.sevice.exception.SchoolNotFoundException;
import liga.school.sevice.exception.StudentNotFoundException;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
import liga.school.sevice.transport.dto.SchoolFindDto;
import liga.school.sevice.transport.dto.SchoolUpdateDto;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
@Transactional
public class SchoolServiceImplIntegrationTest {

    @Autowired
    private SchoolService schoolService;

    @MockBean
    private StudentService feignService;

    @Autowired
    SchoolRepository schoolRepository;

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAll() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder().build();

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(5, result.getContent().size());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllById() {
        Set<String> set = Stream.of("1", "2", "3").collect(toSet());
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);


        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByName() {
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByNameLike() {
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        SchoolOutComeDto schoolDto2 = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddress() {
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIds() {
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIdsMany() {
        SchoolOutComeDto schoolDto1 = SchoolOutComeDto.builder()
                .id(1L)
                .name("name12")
                .address("address11")
                .studentIds(new HashSet<>(Arrays.asList("1", "2", "3")))
                .build();
        SchoolOutComeDto schoolDto2 = SchoolOutComeDto.builder()
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

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Arrays.asList(schoolDto1, schoolDto2), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIdsEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("45"))
                .build();

        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.emptyList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetById() {
        SchoolOutComeDto schoolDto = SchoolOutComeDto.builder()
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
        Page<SchoolOutComeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Collections.singletonList(schoolDto), result.getContent());
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testGetByIdWithSchoolNotFound() {
        schoolService.getById(10L);
    }

    @Test
    public void testCreate() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        SchoolOutComeDto schoolDTO = schoolService.create(SchoolCreateDto.builder()
                .name("name45")
                .address("address45")
                .studentIds(new HashSet<>(Collections.singletonList("45")))
                .build());

        SchoolOutComeDto result = schoolService.getById(schoolDTO.getId());

        assertEquals(schoolDTO, result);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testCreateWithStudentNotFound() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(false);
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name1")
                .address("address")
                .studentIds(new HashSet<>(Collections.singletonList("2")))
                .build();

        schoolService.create(schoolCreateDto);
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdate() {
        SchoolOutComeDto schoolOutComeDto = schoolService.getById(1L);
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(schoolOutComeDto.getId())
                .name("ttt")
                .address(schoolOutComeDto.getAddress())
                .studentIds(schoolOutComeDto.getStudentIds())
                .build();
        schoolOutComeDto.setName("ttt");
        when(feignService.existsAllStudentsByIds(schoolUpdateDto.getStudentIds())).thenReturn(true);

        SchoolOutComeDto updatedSchoolDto = schoolService.update(schoolUpdateDto);

        assertEquals(schoolOutComeDto, updatedSchoolDto);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Sql("/scripts/initSchools.sql")
    public void testUpdateWithInvalidDataAccessApiUsageException() {
        SchoolUpdateDto schoolDto = SchoolUpdateDto.builder().build();

        schoolService.update(schoolDto);
    }

    @Test(expected = StudentNotFoundException.class)
    @Sql("/scripts/initSchools.sql")
    public void testUpdateWithStudentNotFoundException() {
        SchoolUpdateDto schoolDto = SchoolUpdateDto.builder()
                .id(1L)
                .studentIds(new HashSet<>(Collections.singletonList("1"))).build();
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        schoolService.update(schoolDto);
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateWithEmptyStudentIds() {
        SchoolOutComeDto schoolOutComeDto = schoolService.getById(1L);
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(schoolOutComeDto.getId())
                .name(schoolOutComeDto.getName())
                .address(schoolOutComeDto.getAddress())
                .studentIds(Sets.newHashSet())
                .build();
        schoolOutComeDto.setStudentIds(Sets.newHashSet());

        SchoolOutComeDto updatedSchoolDto = schoolService.update(schoolUpdateDto);

        assertEquals(schoolOutComeDto, updatedSchoolDto);
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testRemove() {
        List<School> all = schoolRepository.findAll();

        schoolService.remove(1L);

        List<School> afterRemove = schoolRepository.findAll();
        assertEquals(all.size() - 1, afterRemove.size());
    }

    @Test(expected = SchoolNotFoundException.class)
    @Sql("/scripts/initSchools.sql")
    public void testRemoveWithNullIdSchoolNotFound() {
        schoolService.remove(null);
    }

    @Test(expected = SchoolNotFoundException.class)
    @Sql("/scripts/initSchools.sql")
    public void testRemoveSchoolNotFound() {
        schoolService.remove(23L);
    }
}
