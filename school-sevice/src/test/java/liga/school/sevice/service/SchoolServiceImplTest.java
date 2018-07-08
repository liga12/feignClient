package liga.school.sevice.service;

import liga.school.sevice.domain.SchoolRepository;
import liga.school.sevice.dto.SchoolDTO;
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
public class SchoolServiceImplTest {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    private SchoolDTO schoolDTO;

    @Before
    public void setUp() {
        schoolRepository.deleteAll();
        schoolDTO = schoolService.
                create(SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
    }

    @Test
    public void getAll() {
        for (int i = 0; i < 9; i++) {
            schoolService.create(
                    SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
        }
        assertEquals(10, schoolService.getAll().size());
    }

    @Test
    public void getById() {
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    @Test
    public void getByName() {
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getByName(schoolDTO.getName()));
    }

    @Test
    public void getByAddress() {
        assertEquals(Collections.singletonList(schoolDTO), schoolService.getByAddress(schoolDTO.getAddress()));
    }

    @Test
    public void create() {
        assertEquals(schoolDTO, schoolService.getById(schoolDTO.getId()));
    }

    @Test
    public void update() {
        schoolDTO.setName("n");
        schoolDTO.setAddress("s");
        schoolDTO.setStudentIds(Collections.singletonList("2"));
        SchoolDTO updatedSchoolDTO = schoolService.update(schoolDTO);
        assertEquals(schoolDTO, updatedSchoolDTO);
    }

    @Test
    public void remove() {
        for (int i = 0; i < 9; i++) {
            SchoolDTO schoolDTO = schoolService.create(
                    SchoolDTO.builder().name("name").address("address").studentIds(Collections.singletonList("1")).build());
            if (i == 8)
                schoolService.remove(schoolDTO);
        }
        assertEquals(9, schoolService.getAll().size());
    }
}