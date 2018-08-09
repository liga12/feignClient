package liga.student.service.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sorter {
    private int page;
    private int size;
    private Sort.Direction sortDirection;
    private String sortBy;
}
