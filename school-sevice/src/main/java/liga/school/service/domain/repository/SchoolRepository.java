package liga.school.service.domain.repository;

import liga.school.service.domain.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
}
