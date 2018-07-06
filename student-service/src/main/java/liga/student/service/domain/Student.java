package liga.student.service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class Student {
    @Id
    private String id;
    private String name;
    private String surname;
    private Integer age;
}




