package liga.school.sevice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PaginationSchoolDto {
    Sorter sorter;
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;
}
