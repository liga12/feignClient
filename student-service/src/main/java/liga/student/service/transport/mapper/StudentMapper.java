package liga.student.service.transport.mapper;

import liga.student.service.domain.entity.Student;
import liga.student.service.transport.dto.StudentDTO;
import org.mapstruct.Mapper;

import java.util.List;

// TODO: 30.07.18 check without brackets in annotation
@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO studentToStudentDTO(Student student);

    List<StudentDTO> studentToStudentDTO(List<Student> student);

    Student studentDTOToStudent(StudentDTO studentDTO);
}
