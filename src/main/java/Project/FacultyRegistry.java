package Project;

import java.util.Arrays;

public class FacultyRegistry {
    private Faculty faculty;

    private Faculty[] faculties;  // масив факультетів
    private int numOfFaculties;   // фактична кількість

    // Конструктор без параметрів
    public FacultyRegistry() {
        this.faculties = new Faculty[1]; // початковий розмір
        this.numOfFaculties = 0;

    }

    public Faculty getFaculty() {
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // Додати факультет
    public void addFaculty(Faculty faculty) {
        if (numOfFaculties >= faculties.length) {
            resizeArray(faculties.length * 2); // збільшуємо масив в 2 рази
        }
        faculties[numOfFaculties++] = faculty;
    }

    // Видалити факультет за ID
    public boolean removeFaculty(int id) {
        for (int i = 0; i < numOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                // зсуваємо всі елементи після видаленого на одну позицію вліво
                for (int j = i; j < numOfFaculties - 1; j++) {
                    faculties[j] = faculties[j + 1];
                }
                faculties[numOfFaculties - 1] = null; // очищуємо останній
                numOfFaculties--;
                return true;
            }
        }
        return false; // факультет не знайдено
    }

    // Редагувати факультет
    public boolean editFaculty(int id,
                               String facultyName,
                               String facultyShortName,
                               String headOfFaculty,
                               String email, String phoneNumber) {
        for (int i = 0; i < numOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                if (facultyName != null && !facultyName.isBlank())
                    faculties[i].setFacultyName(facultyName);
                if (facultyShortName != null && !facultyShortName.isBlank())
                    faculties[i].setFacultyShortName(facultyShortName);
                if (headOfFaculty != null && !headOfFaculty.isBlank())
                    faculties[i].setHeadOfFaculty(headOfFaculty);
                if (email != null && !email.isBlank())
                    faculties[i].setEmail(email);
                if(phoneNumber != null && !phoneNumber.isBlank())
                    faculties[i].setPhoneNumber(phoneNumber);
                return true;
            }
        }
        return false;
    }

    // Пошук факультету за ID
    public Faculty findFacultyById(int id) {
        for (int i = 0; i < numOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {
                return faculties[i];
            }
        }
        return null; // повертаємо null, якщо не знайдено
    }

    // Пошук факультету за назвою
    public Faculty findFacultyByName(String name) {
        for (int i = 0; i < numOfFaculties; i++) {
            if (faculties[i].getFacultyName().equalsIgnoreCase(name)) {
                return faculties[i];
            }
        }
        return null;
    }

    // Геттер для масиву факультетів
    public Faculty[] getFaculties() {
        return Arrays.copyOf(faculties, numOfFaculties);
    }

    public int getNumOfFaculties() {
        return numOfFaculties;
    }

    // Збільшення розміру масиву
    private void resizeArray(int newSize) {
        faculties = Arrays.copyOf(faculties, newSize);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FacultyRegistry:\n");
        for (int i = 0; i < numOfFaculties; i++) {
            sb.append(faculties[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
