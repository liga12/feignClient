package liga.student.service.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface StudentRepository extends MongoRepository<Student, String>, QueryByExampleExecutor<Student> {

}
