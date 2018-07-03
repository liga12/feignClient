package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO studentToStudentDTO(Student student);

    List<StudentDTO> studentToStudentDTO(List<Student> student);

    Student studentDTOToStudent(StudentDTO studentDTO);
}
