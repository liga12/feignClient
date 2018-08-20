package liga.student.service.transport.mapper;

import liga.student.service.domain.entity.Student;
import liga.student.service.transport.dto.StudentCreateDto;
import liga.student.service.transport.dto.StudentOutcomeDto;
import org.mapstruct.Mapper;

import java.util.List;

// TODO: 30.07.18 check without brackets in annotation
@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentOutcomeDto toDto(Student student);

    List<StudentOutcomeDto> toDto(List<Student> students);

    Student toEntity(StudentCreateDto dto);

    Student toEntity(StudentOutcomeDto dto);
}
