package liga.school.sevice.mapper;

import liga.school.sevice.domain.School;
import liga.school.sevice.dto.SchoolDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@NoArgsConstructor
public class SchoolMapper {

    @Autowired
    private ModelMapper modelMapper;

    public SchoolDto schoolToSchoolDto(School school) {
        return modelMapper.map(school, SchoolDto.class);
    }

    public List<SchoolDto> schoolToSchoolDto(List<School> students) {
        return students.stream().map(x->modelMapper.map(x,SchoolDto.class)).collect(Collectors.toList());
    }

    public School schoolDtoToSchool(SchoolDto dto) {
        return modelMapper.map(dto, School.class);
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

