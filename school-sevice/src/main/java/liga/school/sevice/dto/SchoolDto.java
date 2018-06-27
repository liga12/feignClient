package liga.school.sevice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto {
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;

    public SchoolDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public SchoolDto(Long id, String name, String address) {
        this.name = name;
        this.address = address;
        this.id = id;
    }


    public SchoolDto(String name, String address, List<String> studentIds) {
        this.name = name;
        this.address = address;
        this.studentIds = studentIds;
    }
}
