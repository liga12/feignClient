package liga.student.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationStudentDto {
    private Sorter sorter;
    private String id;
    private String name;
    private String surname;
    private Integer age;
}
