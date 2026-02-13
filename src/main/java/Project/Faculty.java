package Project;

import java.util.Arrays;

public class Faculty {

    private final int idFaculty;
    private String facultyName;
    private String facultyShortName;
    private String headOfFaculty;
    private String contactsOfFaculty;

    private Department[] departments = new Department[1];
    private int numberOfDepartments;

    public Faculty(int idFaculty, String facultyName, String facultyShortName,
                   String headOfFaculty, String contactsOfFaculty) {
        this.idFaculty = idFaculty;
        this.facultyName = facultyName;
        this.facultyShortName = facultyShortName;
        this.headOfFaculty = headOfFaculty;
        this.contactsOfFaculty = contactsOfFaculty;
    }

    public Department[] getDepartments() {
        return Arrays.copyOf(departments, numberOfDepartments);
    }

    public int getIdFaculty() {
        return idFaculty;
    }


    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyShortName() {
        return facultyShortName;
    }

    public void setFacultyShortName(String facultyShortName) {
        this.facultyShortName = facultyShortName;
    }

    public String getHeadOfFaculty() {
        return headOfFaculty;
    }

    public void setHeadOfFaculty(String headOfFaculty) {
        this.headOfFaculty = headOfFaculty;
    }

    public String getContactsOfFaculty() {
        return contactsOfFaculty;
    }

    public void setContactsOfFaculty(String contactsOfFaculty) {
        this.contactsOfFaculty = contactsOfFaculty;
    }

    public void addDepartment(Department department) {
        if (numberOfDepartments >= departments.length) {
            departments = Arrays.copyOf(departments, numberOfDepartments + 1);
        }
        departments[numberOfDepartments++] = department;
    }


    public boolean removeDepartment(int id) {
        for (int i = 0; i < numberOfDepartments; i++) {
            if (departments[i].getIdDepartment() == id) {
                // shift elements left
                for (int j = i; j < numberOfDepartments - 1; j++) {
                    departments[j] = departments[j + 1];
                }
                departments[numberOfDepartments - 1] = null;
                numberOfDepartments--;
                return true; // successfully removed
            }
        }
        return false; // department not found
    }

    public void editDepartment(int departmentId, String name, String head, Integer roomNumber) {
        for (int i = 0; i < numberOfDepartments; i++) {
            if (departments[i].getIdDepartment() == departmentId) {
                if (name != null && !name.isBlank()) {
                    departments[i].setDepartmentName(name);
                }
                if (head != null && !head.isBlank()) {
                    departments[i].setHeadOfDepartment(head);
                }
                if (roomNumber != null && roomNumber > 0) {
                    departments[i].setRoomNumberOfDepartment(roomNumber);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Department with ID " + departmentId + " not found");
    }


    public Department findDepartmentById(int id) {
        for (int i = 0; i < numberOfDepartments; i++) {
            if (departments[i].getIdDepartment() == id) {
                return departments[i];
            }
        }
        throw new IllegalArgumentException("Department not found");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("============================================================\n");
        sb.append(String.format("Faculty ID: %d\n", idFaculty));
        sb.append("============================================================\n");

        sb.append(String.format("  Повна назва:  %s\n", facultyName));
        sb.append(String.format("  Абревіатура:  %s\n", facultyShortName));
        sb.append(String.format("  Декан:        %s\n", headOfFaculty));
        sb.append(String.format("  Контакти:     %s\n", contactsOfFaculty));

        sb.append("------------------------------------------------------\n");
        sb.append(String.format("  Кількість кафедр: %d\n", numberOfDepartments));
        sb.append("  СПИСОК КАФЕДР:\n");
        if (numberOfDepartments == 0) {
            sb.append("На даний момент кафедр немає\n");
        } else {
            for (int i = 0; i < numberOfDepartments; i++) {
                sb.append(String.format("    %2d. %-25s | Head: %s | Room: %d\n",
                        i + 1,
                        departments[i].getDepartmentName(),
                        departments[i].getHeadOfDepartment(),
                        departments[i].getRoomNumberOfDepartment()));
            }
        }

        sb.append("============================================================\n");
        return sb.toString();
    }
}
