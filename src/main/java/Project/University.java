package Project;

import java.util.Arrays;

public class University {

    private String universityName;
    private String universityShortName;
    private String city;
    private String universityAddress;

    private Faculty[] faculties;
    private int numberOfFaculties;


    public University(String universityName, String universityShortName, String city, String universityAddress) {
        this.universityName = universityName;
        this.universityShortName = universityShortName;
        this.city = city;
        this.universityAddress = universityAddress;
        this.faculties = new Faculty[1];
    }

    public void addFaculty(Faculty faculty) {
        if (numberOfFaculties >= faculties.length) {
            faculties = Arrays.copyOf(faculties, numberOfFaculties + 1);
        }
        faculties[numberOfFaculties++] = faculty;
    }

    public boolean editFaculty(int id, String name, String shortName, String head, String email, String phoneNumber) {
        for (int i = 0; i < numberOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                if (name != null && !name.isBlank())
                    faculties[i].setFacultyName(name);
                if (shortName != null && !shortName.isBlank())
                    faculties[i].setFacultyShortName(shortName);
                if (head != null && !head.isBlank())
                    faculties[i].setHeadOfFaculty(head);
                if (email != null && !email.isBlank())
                    faculties[i].setEmail(email);
                if (phoneNumber != null && !phoneNumber.isBlank())
                    faculties[i].setPhoneNumber(phoneNumber);
                return true;
            }
        }
        return false;
    }

    public boolean removeFaculty(int id) {
        for (int i = 0; i < numberOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                // зсуваємо всі елементи після видаленого на одну позицію вліво
                for (int j = i; j < numberOfFaculties - 1; j++) {
                    faculties[j] = faculties[j + 1];
                }
                faculties[numberOfFaculties - 1] = null; // очищуємо останній
                numberOfFaculties--;
                return true;
            }
        }
        return false;
    }

    public Faculty findFacultyById(int id) {
        for (int i = 0; i < numberOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                return faculties[i];
            }
        }
        throw new IllegalArgumentException("Faculty not found");
    }
    public Faculty findFacultyByName(String name) {
        for (Faculty f : faculties) {
            if (f.getFacultyName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Faculty not found");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("==============================================================================================\n");
        sb.append(String.format("🏫 UNIVERSITY: %s (%s)\n", universityName.toUpperCase(), universityShortName));
        sb.append("==============================================================================================\n");

        sb.append("📍 Місто:   ").append(city).append("\n");
        sb.append("🏠 Адреса:  ").append(universityAddress).append("\n");
        sb.append(String.format("📊 Всього факультетів: %d\n", numberOfFaculties));

        sb.append("\n--- ФАКУЛЬТЕТИ ---\n");
        if (numberOfFaculties == 0) {
            sb.append("   (Факультетів ще не додано)\n");
        } else {
            for (int i = 0; i < numberOfFaculties; i++) {
                if (faculties[i] != null) {
                    sb.append(faculties[i].toString()).append("\n");
                }
            }
        }

        sb.append("==============================================================================================\n");

        return sb.toString();
    }
}
