package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    ModelMapper modelMapper = new ModelMapper();

    public StudentMapper() {}

    public StudentDto studentToStudentDTO(Student student){
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        return studentDto;
    }
    public Student StudentDTOtoStudent(StudentDto dto){
        Student student = modelMapper.map(dto, Student.class);
        return student;
    }
}
