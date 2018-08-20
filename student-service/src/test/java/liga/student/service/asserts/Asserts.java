package liga.student.service.asserts;

import liga.student.service.transport.dto.StudentOutcomeDto;

import java.util.List;

import static org.junit.Assert.assertEquals;

public interface Asserts {

    static void assertEqualStudentAndDto(List<StudentOutcomeDto> firstDto, List<StudentOutcomeDto> secondDto) {
        if (firstDto.size()!=secondDto.size()){
           throw new  AssertionError();
        }
        for (int i = 0; i < firstDto.size()  ; i++){
            assertEquals(firstDto.get(i).getId(), secondDto.get(i).getId());
            assertEquals(firstDto.get(i).getName(), secondDto.get(i).getName());
            assertEquals(firstDto.get(i).getSurname(), secondDto.get(i).getSurname());
            assertEquals(firstDto.get(i).getAge(), secondDto.get(i).getAge());
        }
    }
}
