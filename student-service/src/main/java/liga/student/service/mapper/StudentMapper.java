package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto studentToStudentDto(Student student);
    List<StudentDto> studentToStudentDto(List<Student> student);
    Student studentDtoToStudent(StudentDto studentDto);
}
