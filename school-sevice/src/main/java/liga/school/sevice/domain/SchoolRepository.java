package liga.school.sevice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
}
