package liga.school.sevice.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationSchoolDto {
    private Sorter sorter;
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;
}
