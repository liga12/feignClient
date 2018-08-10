package liga.student.service.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationStudentSearchTextDto {
    private Sorter sorter;
    private String text;
    private Boolean caseSensitive;


}
