package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto studentToStudentDto(Student student);
    List<StudentDto> studentToStudentDto(List<Student> student);
    Student studentDtoToStudent(StudentDto studentDto);
}
