package Project.Models;

import Project.Annotation.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static Project.Models.ConsoleColors.*;

public record University(
        @DisplayName(value = "Name of University")
        String universityName,

        @DisplayName(value = "Short Name of University")
        String universityShortName,

        @DisplayName(value = "City")
        String city,

        @DisplayName(value = "Address")
        String universityAddress,

        List<Faculty> faculties
) {
    public University(String universityName, String universityShortName, String city, String universityAddress) {
        this(universityName, universityShortName, city, universityAddress, new ArrayList<>());
    }
    public void printInfo() {
        System.out.println(CYAN_BOLD + "\n== UNIVERSITY INFO ==" + RESET);
        System.out.println(CYAN + " ℹ " + RESET + "Університет: " + universityName + " (" + universityShortName + ")");
        System.out.println(CYAN + " ℹ " + RESET + "Адреса: " + city + ", " + universityAddress);
        System.out.println(CYAN_BOLD + "==========================================\n" + RESET);

        if (faculties == null || faculties.isEmpty()) {
            System.out.println(YELLOW + " ⚠ " + RESET + "Немає факультетів\n");
        } else {
            for (Faculty f : faculties) {
                System.out.println(CYAN + "[ Faculty ID: " + f.getIdFaculty() + " ]" + RESET);
                System.out.println("Name: " + f.getFacultyName());
                System.out.println("Short Name: " + f.getFacultyShortName());
                System.out.println("Head: " + f.getHeadOfFaculty());
                System.out.println("Contact number of faculty: " + f.getContactsOfFaculty());
                System.out.println(CYAN + "------------------------------------------" + RESET);

                if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                    System.out.println(YELLOW + "   ℹ " + RESET + "No departments.");
                } else {
                    System.out.println(CYAN_BOLD + "   Departments:" + RESET);
                    for (Department d : f.getDepartments()) {
                        System.out.println("      " + CYAN + "•" + RESET + " ID: " + d.getIdDepartment()
                                + " | Name: " + d.getDepartmentName()
                                + " | Head: " + d.getHeadOfDepartment()
                                + " | Room: " + d.getRoomNumber());
                    }
                }

                System.out.println(CYAN_BOLD + "\n==========================================\n" + RESET);
            }
        }
    }
}