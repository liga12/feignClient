package liga.school.sevice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;
}
