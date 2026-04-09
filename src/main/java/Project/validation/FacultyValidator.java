package Project.validation;

import Project.Exceptions.ValidationException;

public class FacultyValidator {

    public static void validateFacultyName(String facultyName) {
        if (facultyName == null || facultyName.isBlank() || !facultyName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException("Invalid faculty name: It cannot be empty and should contain only letters and spaces.");
        }
    }

    public static void validateFacultyShortName(String facultyShortName) {
        if (facultyShortName == null || facultyShortName.isBlank() || !facultyShortName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException("Invalid faculty short name: Please provide a valid short name (only letters allowed).");
        }
    }

    public static void validateHeadOfFaculty(String headOfFaculty) {
        if (headOfFaculty == null || headOfFaculty.isBlank() || !headOfFaculty.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException("Invalid head of faculty name: Please enter a valid name consisting of letters and spaces.");
        }
    }

    public static void validatePhoneNumber(String phone) {
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            throw new ValidationException("Invalid phone: Number must start with '0' and contain exactly 10 digits.");
        }
    }

    public static void validateId(long id){
        if(id<=0){
            throw new ValidationException("Faculty ID must be a positive number.");
        }
    }
}
