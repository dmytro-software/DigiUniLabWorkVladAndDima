package Project.validation;

import Project.Exceptions.ValidationException;

import static Project.Models.ConsoleColors.*;

public class FacultyValidator {

    public static boolean validateFacultyName(String facultyName) {
        if (facultyName == null || facultyName.isBlank() || !facultyName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid faculty name: It cannot be empty and should contain only letters and spaces." + RESET);
        }
        return true;
    }

    public static boolean validateFacultyShortName(String facultyShortName) {
        if (facultyShortName == null || facultyShortName.isBlank() || !facultyShortName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid faculty short name: Please provide a valid short name (only letters allowed)." + RESET);
        }
        return true;
    }

    public static boolean validateHeadOfFaculty(String headOfFaculty) {
        if (headOfFaculty == null || headOfFaculty.isBlank() || !headOfFaculty.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid head of faculty name: Please enter a valid name consisting of letters and spaces." + RESET);
        }
        return true;
    }

    public static boolean validatePhoneNumber(String phone) {
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            throw new ValidationException(RED + "Invalid phone: Number must start with '0' and contain exactly 10 digits." + RESET);
        }
        return true;
    }

    public static boolean validateId(long id){
        if(id<=0){
            throw new ValidationException(RED + "Faculty ID must be a positive number." + RESET);
        }
        return true;
    }
}
