package Project.validation;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class StudentValidator {
    public static void validatePib(String pib) {
        if (pib == null || pib.isBlank() || !pib.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Invalid name: Name cannot be null, blank, or contain special characters/numbers.");
        }
    }

    public static void validateBirthDate(String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = LocalDate.parse(birthDate, formatter);
            int age = Period.between(birthLocalDate, LocalDate.now()).getYears();

            if (birthLocalDate.isAfter(LocalDate.now()) || age < 16 || age > 60) {
                throw new IllegalArgumentException("Invalid birth date: Age must be between 16 and 60.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid birth date format: Expected YYYYMMDD.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    public static void validatePhoneNumber(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone: Must be exactly 10 digits.");
        }
    }

    public static void valideCourse(int course) {
        if(course < 1 || course > 5){
            throw new IllegalArgumentException("Invalid course: Must be between 1 and 4.");
        }
    }

    public static void valideGroup(int group) {
        if(group < 1 || group > 4) {
            throw new IllegalArgumentException("Invalid group: Group must be between 1 and 4.");
        }
    }

    public static void valideFormOfEducation(String formOfEducation) {
        if (formOfEducation == null || formOfEducation.isBlank() || !formOfEducation.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Invalid form of education: Form of education cannot be null, blank, or contain special characters/numbers.");
        }
    }

    public static void valideStudentStatus(String studentStatus) {
        if(studentStatus == null || studentStatus.isBlank() || !studentStatus.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")){
            throw new IllegalArgumentException("Invalid student status: Student status cannot be null, blank, or contain special characters/numbers.");

        }
    }

    public static void validateEnrollmentYear(int enrollmentYear) {
        int currentYear = java.time.LocalDate.now().getYear();

        if (enrollmentYear < 1990 || enrollmentYear > currentYear) {
            throw new IllegalArgumentException("Invalid enrollment year: Must be between 1990 and " + currentYear + ".");
        }
    }

    public static boolean validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Department ID must be a positive number.");
        }
        return false;
    }
}
