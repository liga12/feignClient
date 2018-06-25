package liga.student.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String id;
    private String name;
    private String surname;
    private int age;

    public StudentDto(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
