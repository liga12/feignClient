package liga.school.service.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private String address;

    private Set<String> studentIds;
}
