package liga.school.service.exception;

public class SchoolNotFoundException extends RuntimeException {

    public SchoolNotFoundException() {
        super("School not found");
    }
}
