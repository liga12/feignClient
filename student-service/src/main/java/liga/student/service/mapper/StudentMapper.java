package liga.student.service.mapper;

import liga.student.service.domain.Student;
import liga.student.service.dto.StudentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Data
@NoArgsConstructor
public class StudentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public StudentDto studentToStudentDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    public List<StudentDto> studentToStudentDto(List<Student> students) {
        return students.stream().map(x->modelMapper.map(x,StudentDto.class)).collect(Collectors.toList());
    }

    public Student studentDtotoStudent(StudentDto dto) {
        return modelMapper.map(dto, Student.class);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
