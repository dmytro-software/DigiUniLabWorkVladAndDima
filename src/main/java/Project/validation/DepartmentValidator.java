package Project.validation;

import Project.Exceptions.ValidationException;
import static Project.Models.ConsoleColors.*;

public class DepartmentValidator {
    public static boolean validateDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isBlank()) {
            throw new ValidationException(RED + "Department name cannot be empty." + RESET);
        }

        if (departmentName.length() < 3) {
            throw new ValidationException(RED + "Department name is too short (min 3 characters)." + RESET);
        }

        if (!departmentName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Department name contains invalid characters." + RESET);
        }

        if (!departmentName.matches(".*[a-zA-Zа-яА-ЯіїєґІЇЄҐ].*")) {
            throw new ValidationException(RED + "Department name must contain at least one letter." + RESET);
        }
        return true;
    }

    public static boolean validateHeadOfDepartment(String headOfDepartment) {
        if (headOfDepartment == null || headOfDepartment.isBlank() || !headOfDepartment.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new ValidationException(RED + "Invalid head of department: Please provide a valid name." + RESET);
        }
        return true;
    }

    public static boolean validateRoomNumber(int roomNumber) {
        if (roomNumber < 1 || roomNumber > 999) {
            throw new ValidationException(RED + "Invalid room number: Must be between 1 and 999." + RESET);
        }
        return true;
    }

    public static boolean validateDepartmentId(int id) {
        if (id <= 0) {
            throw new ValidationException(RED + "Department ID must be a positive number." + RESET);
        }
        return true;
    }

}
