package liga.school.sevice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class School {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private List<String> students;


}
