package liga.student.service.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDaoImpl implements StudentDao {

    @Override
    public Map<String, List<Student>> getStudents() {
        Map<String, List<Student>> schooDB = new HashMap<>();
        List<Student> lst = new ArrayList<>();
        Student std = new Student("Sajal", "Class IV");
        lst.add(std);
        std = new Student("Lokesh", "Class V");
        lst.add(std);
        schooDB.put("abcschool", lst);
        lst = new ArrayList<>();
        std = new Student("Kajal", "Class III");
        lst.add(std);
        std = new Student("Sukesh", "Class VI");
        lst.add(std);
        schooDB.put("xyzschool", lst);
        return schooDB;
    }
}
