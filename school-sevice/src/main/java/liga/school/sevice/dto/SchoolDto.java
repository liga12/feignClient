package liga.school.sevice.dto;

import liga.school.sevice.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private List<Student> students;

    public SchoolDto(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public SchoolDto(Long id, String name, String address) {
        this.name = name;
        this.address = address;
        this.id = id;
    }
}
