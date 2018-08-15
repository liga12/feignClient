package liga.student.service.transport.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class StudentCreateDto {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private Integer age;
}
