package Project.Models;

import java.util.ArrayList;
import java.util.List;

public class University {
    private String universityName;
    private String universityShortName;
    private String city;
    private String universityAddress;

    private List<Faculty> faculties = new ArrayList<>();

    public University(String universityName, String universityShortName, String city, String universityAddress) {
        this.universityName = universityName;
        this.universityShortName = universityShortName;
        this.city = city;
        this.universityAddress = universityAddress;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }
    public void printInfo() {
        System.out.println("Університет: " + universityName + " (" + universityShortName + ")");
        System.out.println("Адреса: " + city + ", " + universityAddress);
        System.out.println("\n==========================================");

        if (faculties == null || faculties.isEmpty()) {
            System.out.println("Немає факультетів");
        } else {
            for (Faculty f : faculties) {
                System.out.println("Faculty ID: " + f.getIdFaculty());
                System.out.println("Name: " + f.getFacultyName());
                System.out.println("Short Name: " + f.getFacultyShortName());
                System.out.println("Head: " + f.getHeadOfFaculty());
                System.out.println("Contact number of faculty: " + f.getContactsOfFaculty());
                System.out.println("------------------------------------------");

                if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                    System.out.println("   No departments.");
                } else {
                    System.out.println("   Departments:");
                    for (Department d : f.getDepartments()) {
                        System.out.println("      • ID: " + d.getIdDepartment()
                                + " | Name: " + d.getDepartmentName()
                                + " | Head: " + d.getHeadOfDepartment()
                                + " | Room: " + d.getRoomNumber());
                    }
                }

                System.out.println("\n==========================================");
            }
        }
    }
}