package Project.validation;

public class DepartmentValidator {
    public static void validateDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }

        if (departmentName.length() < 3) {
            throw new IllegalArgumentException("Department name is too short (min 3 characters).");
        }

        if (!departmentName.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Department name contains invalid characters.");
        }

        if (!departmentName.matches(".*[a-zA-Zа-яА-ЯіїєґІЇЄҐ].*")) {
            throw new IllegalArgumentException("Department name must contain at least one letter.");
        }
    }

    public static void validateHeadOfDepartment(String headOfDepartment) {
        if (headOfDepartment == null || headOfDepartment.isBlank() || !headOfDepartment.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
            throw new IllegalArgumentException("Invalid head of department: Please provide a valid name.");
        }
    }

    public static void validateRoomNumber(int roomNumber) {
        if (roomNumber < 1 || roomNumber > 999) {
            throw new IllegalArgumentException("Invalid room number: Must be between 1 and 999.");
        }
    }

    public static void validateFacultyId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Department ID must be a positive number.");
        }
    }
}
