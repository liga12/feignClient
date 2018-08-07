package liga.school.sevice.mapper;

import liga.school.sevice.entity.School;
import liga.school.sevice.dto.SchoolDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolMapperTest {

    @Autowired
    private SchoolMapper schoolMapper;
    private School school;

    @Before
    public void setUp() {
        school = new School(1L, "name", "address", new ArrayList<>(Collections.singleton("1")));
    }

    @Test
    public void schoolToSchoolDto() {
        checkStudentData(school, schoolMapper.toDto(school));
    }

    @Test
    public void schoolToSchoolDtoList() {
        School school = new School(2L, "name2", "address2", new ArrayList<>(Collections.singleton("2")));
        List<School> schools = new ArrayList<>(Arrays.asList(school, this.school));
        List<SchoolDTO> schoolDTOs = schoolMapper.toDto(schools);
        for (int i = 0; i < schoolDTOs.size(); i++) {
            checkStudentData(schools.get(i), schoolDTOs.get(i));
        }
    }

    @Test
    public void schoolDtoToSchool() {
        SchoolDTO schoolDTO = new SchoolDTO(1L, "name", "address", new ArrayList<>(Collections.singleton("1")));
        checkStudentData(schoolMapper.toEntity(schoolDTO), schoolDTO);
    }

    private void checkStudentData(School school, SchoolDTO schoolDTO) {
        assertEquals(school.getId(), schoolDTO.getId());
        assertEquals(school.getName(), schoolDTO.getName());
        assertEquals(school.getAddress(), schoolDTO.getAddress());
        assertEquals(school.getStudentIds(), schoolDTO.getStudentIds());
    }
}