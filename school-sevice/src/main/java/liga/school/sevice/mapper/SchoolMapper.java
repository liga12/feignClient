package liga.school.sevice.mapper;

import liga.school.sevice.domain.School;
import liga.school.sevice.dto.SchoolDto;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class SchoolMapper {

    private final static MapperFacade mapper;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(SchoolDto.class, School.class)
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public SchoolDto schoolToSchoolDto(final School school) {
        SchoolDto schoolDto = mapper.map(school, SchoolDto.class);
        return schoolDto;
    }

    public List<SchoolDto> schoolToSchoolDto(final List<School> schools) {
        List<SchoolDto> schoolDto = mapper.mapAsList(schools, SchoolDto.class);
        return schoolDto;
    }

    public School schoolDtoToSchool(final SchoolDto dto) {
        School school = mapper.map(dto, School.class);
        return school;
    }
}

