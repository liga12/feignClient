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
public class SchoolDTO {
    private Long id;
    private String name;
    private String address;
    private List<String> studentIds;

    public SchoolDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public SchoolDTO(Long id, String name, String address) {
        this.name = name;
        this.address = address;
        this.id = id;
    }


    public SchoolDTO(String name, String address, List<String> studentIds) {
        this.name = name;
        this.address = address;
        this.studentIds = studentIds;
    }
}
