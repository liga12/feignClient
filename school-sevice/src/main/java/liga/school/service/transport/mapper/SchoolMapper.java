package liga.school.service.transport.mapper;

import liga.school.service.domain.entity.School;
import liga.school.service.transport.dto.SchoolCreateDto;
import liga.school.service.transport.dto.SchoolOutcomeDto;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class SchoolMapper {

    private final static MapperFacade mapper;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(SchoolOutcomeDto.class, School.class)
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public SchoolOutcomeDto toDto(final School school) {
        return mapper.map(school, SchoolOutcomeDto.class);
    }

    public School toEntity(final SchoolCreateDto dto) {
        return mapper.map(dto, School.class);
    }

    public School toEntity(final SchoolOutcomeDto dto) {
        return mapper.map(dto, School.class);
    }
}

