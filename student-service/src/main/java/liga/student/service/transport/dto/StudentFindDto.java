package liga.student.service.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentFindDto {

    private String id;
    private String name;
    private String surname;
    private Integer startAge;
    private Integer endAge;
}
