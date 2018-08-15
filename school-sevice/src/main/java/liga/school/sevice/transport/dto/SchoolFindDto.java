package liga.school.sevice.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolFindDto {

    private Long id;

    private String name;

    private String address;

    private Set<String> studentIds;
}
