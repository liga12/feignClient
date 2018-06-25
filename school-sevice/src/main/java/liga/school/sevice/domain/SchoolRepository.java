package liga.school.sevice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School,Long> {
    List<School> getByName(String name);

    List<School> getByAddress(String address);
}
