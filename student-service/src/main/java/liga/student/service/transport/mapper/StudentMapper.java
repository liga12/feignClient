package liga.student.service.transport.mapper;

import liga.student.service.domain.entity.Student;
import liga.student.service.transport.dto.StudentCreatrDto;
import liga.student.service.transport.dto.StudentOutComeDto;
import org.mapstruct.Mapper;

import java.util.List;

// TODO: 30.07.18 check without brackets in annotation
@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentOutComeDto toDto(Student student);

    List<StudentOutComeDto> toDto(List<Student> students);

    Student toEntity(StudentCreatrDto dto);

    Student toEntity(StudentOutComeDto dto);
}
