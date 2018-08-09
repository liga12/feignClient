package liga.school.sevice.domain.repository;

import liga.school.sevice.domain.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
}
