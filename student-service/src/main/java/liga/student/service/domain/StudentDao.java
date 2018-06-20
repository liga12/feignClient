package liga.student.service.domain;

import java.util.List;
import java.util.Map;

public interface StudentDao {

    Map<String, List<Student>> getStudents();
}
