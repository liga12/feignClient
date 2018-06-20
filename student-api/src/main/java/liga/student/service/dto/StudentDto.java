package liga.student.service.dto;

import org.springframework.stereotype.Component;

@Component
public class StudentDto {

    private String name;
    private String className;

    public StudentDto(){}

    public StudentDto(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
