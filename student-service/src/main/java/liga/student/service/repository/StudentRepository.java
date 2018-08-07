package liga.student.service.repository;

import liga.student.service.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String>, QueryByExampleExecutor<Student> {

    @Query(value = "{$text : {$search :?0, $caseSensitive: ?1}}")
    List<Student> searchByNamesAndSurname(String search, boolean caseSensitive, Pageable pageable);

}
