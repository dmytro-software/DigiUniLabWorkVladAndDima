package Project.validation;

import Project.service.TeacherService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class TeacherValidator {

    static TeacherService teacherService;

    public TeacherValidator(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    public static boolean validateName(String name) {
        if (name == null || name.isBlank() || !name.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Invalid name: Name cannot be null, blank, or contain special characters/numbers.");
        }
        return false;
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

    public static boolean validateEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        return false;
    }

    public static boolean validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone: Must be exactly 10 digits.");
        }
        return false;
    }

    public static boolean validateFTE(double fte) {
        if (fte <= 0 || fte > 1.0) {
            throw new IllegalArgumentException("Invalid FTE: Must be between 0.1 and 1.0.");
        }
        return false;
    }

    public static boolean validatePositionOrDegree(String text) {
        if (text == null || text.isBlank() || !text.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Invalid position or degree: Only letters and spaces allowed.");
        }
        return false;
    }

    public static boolean validateHireDate(String hireDate) {
        if (hireDate == null || !hireDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
            throw new IllegalArgumentException("Invalid hire date: Expected format YYYY.MM.DD (e.g., 2024.05.15).");
        }
        return false;
    }

    public static boolean validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Department ID must be a positive number.");
        }
        return false;
    }
}