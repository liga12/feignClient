package liga.school.sevice.transport.mapper;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
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

    @Test
    public void toDto() {
        School school = School.builder()
                .id(1L)
                .name("name")
                .address("address")
                .studentIds(new LinkedHashSet<>(Collections.singleton("1")))
                .build();

        SchoolOutComeDto schoolOutComeDto = schoolMapper.toDto(school);

        assertEquals(schoolOutComeDto.getId(), schoolOutComeDto.getId());
        assertEquals(schoolOutComeDto.getName(), schoolOutComeDto.getName());
        assertEquals(schoolOutComeDto.getAddress(), schoolOutComeDto.getAddress());
        assertEquals(schoolOutComeDto.getStudentIds(), schoolOutComeDto.getStudentIds());
    }

    @Test
    public void schoolCreateDtoToEntity() {
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
    public void schoolOutComeDtoToEntity() {
        SchoolOutComeDto schoolOutComeDto = SchoolOutComeDto.builder()
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