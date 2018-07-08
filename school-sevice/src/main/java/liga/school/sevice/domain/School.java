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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "studentIds",joinColumns = @JoinColumn(name = "school_id"))
    private List<String> studentIds;


}
