package liga.student.service.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private String id;
    private String name;
    private String surname;
    private Integer age;

    public StudentDTO(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}