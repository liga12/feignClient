package liga.school.sevice.mapper;

import liga.school.sevice.entity.School;
import liga.school.sevice.dto.SchoolDTO;
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
        mapperFactory.classMap(SchoolDTO.class, School.class)
                .byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public SchoolDTO toDto(final School school) {
        return mapper.map(school, SchoolDTO.class);
    }

    public List<SchoolDTO> toDto(final List<School> schools) {
        return mapper.mapAsList(schools, SchoolDTO.class);
    }

    public School toEntity(final SchoolDTO dto) {
        return mapper.map(dto, School.class);
    }
}

