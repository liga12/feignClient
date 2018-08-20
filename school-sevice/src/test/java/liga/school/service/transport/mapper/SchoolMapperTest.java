package liga.school.service.transport.mapper;

import liga.school.service.domain.entity.School;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
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

    @Test
    public void testToDto() {
        School school = School.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(new LinkedHashSet<>(Collections.singleton("1")))
                .build();

        SchoolOutcomeDto schoolOutcomeDto = schoolMapper.toDto(school);

        assertEquals(schoolOutcomeDto.getId(), schoolOutcomeDto.getId());
        assertEquals(schoolOutcomeDto.getName(), schoolOutcomeDto.getName());
        assertEquals(schoolOutcomeDto.getAddress(), schoolOutcomeDto.getAddress());
        assertEquals(schoolOutcomeDto.getStudentIds(), schoolOutcomeDto.getStudentIds());
    }

    @Test
    public void testSchoolCreateDtoToEntity() {
        SchoolCreateDto schoolCreateDto = SchoolCreateDto.builder()
                .name("name")
                .address("address")
                .studentIds(new LinkedHashSet<>(Collections.singleton("1")))
                .build();

        School school = schoolMapper.toEntity(schoolCreateDto);

        assertEquals(schoolCreateDto.getName(), school.getName());
        assertEquals(schoolCreateDto.getAddress(), school.getAddress());
        assertEquals(schoolCreateDto.getStudentIds(), school.getStudentIds());
    }


    @Test
    public void TestSchoolOutComeDtoToEntity() {
        SchoolOutcomeDto schoolOutComeDto = SchoolOutcomeDto.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(new LinkedHashSet<>(Collections.singleton("1")))
                .build();

        School school = schoolMapper.toEntity(schoolOutComeDto);

        assertEquals(schoolOutComeDto.getName(), school.getName());
        assertEquals(schoolOutComeDto.getAddress(), school.getAddress());
        assertEquals(schoolOutComeDto.getStudentIds(), school.getStudentIds());
    }
}