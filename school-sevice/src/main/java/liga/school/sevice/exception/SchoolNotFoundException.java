package liga.school.sevice.exception;

public class SchoolNotFoundException extends RuntimeException {

    public SchoolNotFoundException() {
        super("School not found");
    }
}
