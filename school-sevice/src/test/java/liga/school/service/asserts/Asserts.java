package liga.school.service.asserts;

import liga.school.service.domain.entity.School;
import liga.school.service.transport.dto.SchoolOutcomeDto;

import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualsSchoolAndDto(List<School> school, List<SchoolOutcomeDto> dto) {
        if (school.size()!=dto.size()){
           throw new  AssertionError();
        }
        for (int i = 0; i < school.size()  ; i++){
            assertEquals(school.get(i).getId(), dto.get(i).getId());
            assertEquals(school.get(i).getName(), dto.get(i).getName());
            assertEquals(school.get(i).getAddress(), dto.get(i).getAddress());
            assertEquals(school.get(i).getStudentIds(), dto.get(i).getStudentIds());
        }
    }
}
