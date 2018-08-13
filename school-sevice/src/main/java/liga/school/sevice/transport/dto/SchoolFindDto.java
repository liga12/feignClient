package liga.school.sevice.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Pageable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolFindDto {
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;
}
