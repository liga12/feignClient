package liga.student.service.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    Boolean existsAllById(String... strings);

    List<Student> findByName(String name);

    List<Student> findBySurname(String surname);

    List<Student> findByAge(int age);
}
