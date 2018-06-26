package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  StudentMapperT {
      StudentDto studentToStudentDto(Student student);
      List<StudentDto> studentToStudentDto(List<Student> students);
     Student studentDtoToStuden(StudentDto studentDto);

}
