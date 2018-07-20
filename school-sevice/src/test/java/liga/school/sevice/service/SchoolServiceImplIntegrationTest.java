package liga.school.sevice.service;

import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
import liga.school.sevice.exception.SchoolNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolServiceImplIntegrationTest {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Before
    public void setUp() {
        schoolRepository.deleteAll();
    }

    @Test
    public void testGetAll() {
        for (int i = 0; i < 10; i++) {
            schoolService.create(
                    SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        }
        assertEquals(10, schoolService.getAll().size());
    }

    @Test
    public void testGetById() {
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    @Test(expected = SchoolNotFoundException.class)
    public void testGetByIdWithSchoolNotFound() {
        schoolService.getById(1L);
    }

    @Test
    public void testGetByName() {
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getByName(schoolDTO.getName()));
    }

    @Test
    public void testGetByAddress() {
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getByAddress(schoolDTO.getAddress()));
    }

    @Test
    public void testCreate() {
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    @Test
    public void testUpdate() {
        SchoolDTO schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
        schoolDTO.setName("n");
        schoolDTO.setAddress("s");
        schoolDTO.setStudentIds(Collections.singletonList("2"));
        SchoolDTO updatedSchoolDTO = schoolService.update(schoolDTO);
        assertEquals(schoolDTO, updatedSchoolDTO);
    }

    @Test
    public void testRemove() {
        for (int i = 0; i < 10; i++) {
            SchoolDTO schoolDTO = schoolService.create(
                    SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
            if (i == 9)
                schoolService.remove(schoolDTO);
        }
        assertEquals(9, schoolService.getAll().size());
    }
}