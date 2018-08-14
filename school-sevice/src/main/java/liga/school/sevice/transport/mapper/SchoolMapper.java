package liga.school.sevice.transport.mapper;

import liga.school.sevice.domain.entity.School;
import liga.school.sevice.transport.dto.SchoolCreateDto;
import liga.school.sevice.transport.dto.SchoolOutComeDto;
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
        mapperFactory.classMap(SchoolOutComeDto.class, School.class)
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public SchoolOutComeDto toDto(final School school) {
        return mapper.map(school, SchoolOutComeDto.class);
    }

     List<SchoolOutComeDto> toDto(final List<School> schools) {
        return mapper.mapAsList(schools, SchoolOutComeDto.class);
    }

    public School toEntity(final SchoolCreateDto dto) {
        return mapper.map(dto, School.class);
    }

    public School toEntity(final SchoolOutComeDto dto) {
        return mapper.map(dto, School.class);
    }
}

