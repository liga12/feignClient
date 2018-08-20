package liga.school.service.service;

import com.google.common.collect.Lists;
import liga.school.service.domain.entity.School;
import liga.school.service.domain.repository.SchoolRepository;
import liga.school.service.exception.SchoolNotFoundException;
import liga.school.service.exception.StudentNotFoundException;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolFindDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import liga.school.service.transport.dto.SchoolUpdateDto;
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

import java.util.Arrays;
import java.util.List;

import static liga.school.service.asserts.Asserts.assertEqualsSchoolAndDto;
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

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(schoolRepository.findAll(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllById() {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(1L)
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(Lists.newArrayList(school), result.getContent());
    }


    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByIdEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(10L)
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);


        assertEqualsSchoolAndDto(Lists.newArrayList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByName() {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("me12")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(Lists.newArrayList(school), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByNameLike() {
        List<School> schools = schoolRepository.findAllById(Arrays.asList(1L,3L));
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("1")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(schools, result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByNameEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("ddd")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Lists.newArrayList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddress() {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .address("ress1")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(Lists.newArrayList(school), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddressLike() {
        List<School> schools = schoolRepository.findAllById(Arrays.asList(1L,3L));
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .address("1")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(schools, result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByAddressEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .name("ss")
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Lists.newArrayList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIds() {
        School school = schoolRepository.findById(1L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("3"))
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(Lists.newArrayList(school), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIdsMany() {
        List<School> schools = schoolRepository.findAllById(Arrays.asList(1L,4L));
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("1"))
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(schools, result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetAllByStudentIdsEmpty() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .studentIds(Sets.newLinkedHashSet("45"))
                .build();

        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEquals(Lists.newArrayList(), result.getContent());
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testGetById() {
        School school = schoolRepository.findById(2L).orElseThrow(AssertionError::new);
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
        SchoolFindDto schoolFindDto = SchoolFindDto
                .builder()
                .id(2L)
                .build();
        Page<SchoolOutcomeDto> result = schoolService.getAll(schoolFindDto, pageable);

        assertEqualsSchoolAndDto(Lists.newArrayList(school), result.getContent());
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testGetByIdWithSchoolNotFound() {
        schoolService.getById(10L);
    }

    @Test
    public void testCreate() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(true);
        SchoolOutcomeDto schoolDTO = schoolService.create(SchoolCreateDto.builder()
                .name("name45")
                .address("address45")
                .studentIds(Sets.newLinkedHashSet("45"))
                .build());

        SchoolOutcomeDto result = schoolService.getById(schoolDTO.getId());

        assertEquals(schoolDTO, result);
    }

    @Test(expected = StudentNotFoundException.class)
    public void testCreateWithStudentNotFound() {
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(false);
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name1")
                .address("address")
                .studentIds(Sets.newLinkedHashSet("2"))
                .build();

        schoolService.create(schoolCreateDto);
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdate() {
        SchoolOutcomeDto schoolOutComeDto = schoolService.getById(1L);
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(schoolOutComeDto.getId())
                .name("ttt")
                .address(schoolOutComeDto.getAddress())
                .studentIds(schoolOutComeDto.getStudentIds())
                .build();
        schoolOutComeDto.setName("ttt");
        when(feignService.existsAllStudentsByIds(schoolUpdateDto.getStudentIds())).thenReturn(true);

        SchoolOutcomeDto updatedSchoolDto = schoolService.update(schoolUpdateDto);

        assertEquals(schoolOutComeDto, updatedSchoolDto);
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testUpdateWithSchoolNotFoundException() {
        SchoolUpdateDto schoolDto = SchoolUpdateDto.builder().id(22L).build();

        schoolService.update(schoolDto);
    }

    @Test(expected = StudentNotFoundException.class)
    @Sql("/scripts/initSchools.sql")
    public void testUpdateWithStudentNotFoundException() {
        SchoolUpdateDto schoolDto = SchoolUpdateDto.builder()
                .id(1L)
                .studentIds(Sets.newLinkedHashSet("1")).build();
        when(feignService.existsAllStudentsByIds(anySet())).thenReturn(false);

        schoolService.update(schoolDto);
    }

    @Test
    @Sql("/scripts/initSchools.sql")
    public void testUpdateWithEmptyStudentIds() {
        SchoolOutcomeDto schoolOutComeDto = schoolService.getById(1L);
        SchoolUpdateDto schoolUpdateDto = SchoolUpdateDto.builder()
                .id(schoolOutComeDto.getId())
                .name(schoolOutComeDto.getName())
                .address(schoolOutComeDto.getAddress())
                .studentIds(null)
                .build();
        schoolOutComeDto.setStudentIds(Sets.newHashSet());

        SchoolOutcomeDto updatedSchoolDto = schoolService.update(schoolUpdateDto);

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
    public void testRemoveWithNullIdSchoolNotFound() {
        schoolService.remove(null);
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testRemoveSchoolNotFound() {
        schoolService.remove(23L);
    }
}
