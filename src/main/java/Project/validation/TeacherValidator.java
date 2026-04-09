package Project.validation;

import Project.Exceptions.ValidationException;
import Project.service.TeacherService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static Project.Models.ConsoleColors.*;


public class TeacherValidator {

    static TeacherService teacherService;

    public TeacherValidator(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    public static boolean validateName(String name) {
        if (name == null || name.isBlank() || !name.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid name: Name cannot be null, blank, or contain special characters/numbers." + RESET);
        }
        return false;
    }

    public static void validateBirthDate(String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = LocalDate.parse(birthDate, formatter);
            int age = Period.between(birthLocalDate, LocalDate.now()).getYears();

            // Для викладачів можливо варто збільшити вік до 70-80, але залишив 60 як у тебе
            if (birthLocalDate.isAfter(LocalDate.now()) || age < 16 || age > 60) {
                throw new ValidationException(RED + "Invalid birth date: Age must be between 16 and 60." + RESET);
            }
        } catch (Exception e) {
            throw new ValidationException(RED + "Invalid birth date format: Expected YYYYMMDD." + RESET);
        }
    }

    public static boolean validateEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException(RED + "Invalid email format." + RESET);
        }
        return false;
    }

    public static boolean validatePhone(String phone) {
        if (phone == null || !phone.matches("\\d{10}")) {
            throw new ValidationException(RED + "Invalid phone: Must be exactly 10 digits." + RESET);
        }
        return false;
    }

    public static boolean validateFTE(double fte) {
        if (fte <= 0 || fte > 1.0) {
            throw new ValidationException(RED + "Invalid FTE: Must be between 0.1 and 1.0." + RESET);
        }
        return false;
    }

    public static boolean validatePositionOrDegree(String text) {
        if (text == null || text.isBlank() || !text.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid position or degree: Only letters and spaces allowed." + RESET);
        }
        return false;
    }

    public static boolean validateHireDate(String hireDate) {
        if (hireDate == null || !hireDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
            throw new ValidationException(RED + "Invalid hire date: Expected format YYYY.MM.DD (e.g., 2024.05.15)." + RESET);
        }
        return false;
    }

    public static boolean validateId(int id) {
        if (id <= 0) {
            throw new ValidationException(RED + "Teacher ID must be a positive number." + RESET);
        }
        return false;
    }
}