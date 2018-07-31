package liga.school.sevice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationSchoolDto {
    Sorter sorter;
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;

    public PaginationSchoolDto(Sorter sorter) {
        this.sorter = sorter;
    }
}
