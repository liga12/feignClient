package liga.student.service.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentFindByTextSearchDto {

    @NotBlank
    private String text;

    private Boolean caseSensitive;


}
