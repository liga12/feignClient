package liga.student.service.transport.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudentUpdateDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private Integer age;
}
