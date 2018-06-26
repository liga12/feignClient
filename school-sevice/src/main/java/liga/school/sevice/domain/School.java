package liga.school.sevice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "school")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "school",cascade = CascadeType.ALL)
    private List<Student> students;


}
