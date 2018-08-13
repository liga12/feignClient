package liga.school.sevice.transport.mapper;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.transport.dto.SchoolDto;
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

    public SchoolDto toDto(final School school) {
        return mapper.map(school, SchoolDto.class);
    }

     List<SchoolDto> toDto(final List<School> schools) {
        return mapper.mapAsList(schools, SchoolDto.class);
    }

    public School toEntity(final SchoolDto dto) {
        return mapper.map(dto, School.class);
    }
}

