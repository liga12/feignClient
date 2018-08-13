package liga.school.sevice.transport.mapper;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.transport.dto.SchoolDto;
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
        school = new School(1L, "name", "address", new LinkedHashSet<>(Collections.singleton("1")));
    }

    @Test
    public void schoolToSchoolDto() {
        checkStudentData(school, schoolMapper.toDto(school));
    }

    @Test
    public void schoolToSchoolDtoList() {
        School school = new School(2L, "name2", "address2", new LinkedHashSet<>(Collections.singleton("2")));
        List<School> schools = new ArrayList<>(Arrays.asList(school, this.school));
        List<SchoolDto> schoolDtos = schoolMapper.toDto(schools);
        for (int i = 0; i < schoolDtos.size(); i++) {
            checkStudentData(schools.get(i), schoolDtos.get(i));
        }
    }

    @Test
    public void schoolDtoToSchool() {
        SchoolDto schoolDTO = new SchoolDto(1L, "name", "address", new LinkedHashSet<>(Collections.singleton("1")));
        checkStudentData(schoolMapper.toEntity(schoolDTO), schoolDTO);
    }

    private void checkStudentData(School school, SchoolDto schoolDTO) {
        assertEquals(school.getId(), schoolDTO.getId());
        assertEquals(school.getName(), schoolDTO.getName());
        assertEquals(school.getAddress(), schoolDTO.getAddress());
        assertEquals(school.getStudentIds(), schoolDTO.getStudentIds());
    }
}