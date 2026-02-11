package Project;

import java.util.Arrays;

public class University {

    private String universityName;
    private String universityShortName;
    private String city;
    private String universityAddress;

    private Faculty[] faculties = new Faculty[1];
    private int numberOfFaculties;

    private Department[] departments = new Department[1];
    private int numberOfDepartments;

    public University(String universityName, String universityShortName, String city, String universityAddress) {
        this.universityName = universityName;
        this.universityShortName = universityShortName;
        this.city = city;
        this.universityAddress = universityAddress;
    }

    public void addFaculty(Faculty faculty) {
        if (numberOfFaculties >= faculties.length) {
            faculties = Arrays.copyOf(faculties, numberOfFaculties + 1);
        }
        faculties[numberOfFaculties++] = faculty;
    }

    public Faculty[] getFaculties() {
        return Arrays.copyOf(faculties, numberOfFaculties);
    }

    public void addDepartment(Department department) {
        if (numberOfDepartments >= departments.length) {
            departments = Arrays.copyOf(departments, numberOfDepartments + 1);
        }
        departments[numberOfDepartments++] = department;
    }

    public Department[] getDepartments() {
        return Arrays.copyOf(departments, numberOfDepartments);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("==============================================================================================\n");
        sb.append(String.format("🏫 UNIVERSITY: %s (%s)\n", universityName.toUpperCase(), universityShortName));
        sb.append("==============================================================================================\n");


        sb.append("📍 Місто:   ").append(city).append("\n");
        sb.append("🏠 Адреса:  ").append(universityAddress).append("\n");
        sb.append(String.format("📊 Всього факультетів: %d | Кафедр: %d\n", numberOfFaculties, numberOfDepartments));

        sb.append("\n--- [ ФАКУЛЬТЕТИ ] ---\n");
        if (numberOfFaculties == 0) {
            sb.append("   (Факультетів ще не додано)\n");
        } else {
            for (int i = 0; i < numberOfFaculties; i++) {
                sb.append(faculties[i].toString()).append("\n");
            }
        }

        sb.append("\n--- [ КАФЕДРИ ] ---\n");
        if (numberOfDepartments == 0) {
            sb.append("   (Кафедр ще не додано)\n");
        } else {
            for (int i = 0; i < numberOfDepartments; i++) {
                sb.append("   • ").append(departments[i].toString()).append("\n");
            }
        }

        sb.append("==============================================================================================\n");

        return sb.toString();
    }
}
