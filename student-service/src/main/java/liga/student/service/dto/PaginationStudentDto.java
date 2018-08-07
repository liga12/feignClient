package liga.student.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationStudentDto {
    private Sorter sorter;
    private String id;
    private String name;
    private String surname;
    private Integer startAge;
    private Integer endAge;
}
