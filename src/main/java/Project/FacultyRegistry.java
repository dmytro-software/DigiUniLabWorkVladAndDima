package Project;

import java.util.Arrays;

public class FacultyRegistry {

    private Faculty[] faculties;
    private int numOfFaculties;

    public FacultyRegistry() {
        this.faculties = new Faculty[1];
    }


    //Метод для створення факультету, щоб занести його в масив
    public void addFaculty(Faculty faculty){
        //якщо число факультетів більша за розмір масиву то ми його розширюємо
        if (numOfFaculties >= faculties.length){
            resizeArray(numOfFaculties + 1);
        }
        faculties[numOfFaculties++] = faculty;
    }

    //метод щоб видалити факультет, шляхом знаходження по айді
    public void removeFaculty(int id) {
        for (int i = 0; i < numOfFaculties; i++) {
            //шукаємо за айді
            if (faculties[i].getIdFaculty() == id) {
                Faculty[] newArray = new Faculty[numOfFaculties - 1];
                // пояснення для себе
                // копіювання все до вибраного елементу
                System.arraycopy(faculties, 0, newArray, 0, i);
                // копіювання все після вибраного елементу
                System.arraycopy(faculties, i + 1, newArray, i, numOfFaculties - i - 1);

                faculties = newArray;
                numOfFaculties--;
                return;
            }
        }
        throw new IllegalArgumentException("ФАКУЛЬТЕТ НЕ ЗНАЙДЕНО");
    }

    public void editFaculty(int id,
                            String facultyName,
                            String facultyShortName,
                            String headOfFaculty,
                            String email,
                            String phone) {

        for (int i = 0; i < numOfFaculties; i++) {
            if (faculties[i].getIdFaculty() == id) {

                faculties[i].setFacultyName(facultyName);
                faculties[i].setFacultyShortName(facultyShortName);
                faculties[i].setHeadOfFaculty(headOfFaculty);
                faculties[i].setEmail(email);
                faculties[i].setPhoneNumber(phone);

                return;
            }
        }
        throw new IllegalArgumentException("Faculty not found");
    }

    //метод для збільшення масиву
    private void resizeArray(int i) {
        Faculty[] newArray = new Faculty[i];
        System.arraycopy(faculties,0,newArray,0,faculties.length);
        faculties = newArray;
    }

    //повернути масив факультетів(клонований)
    public Faculty[] getFaculties() {
        return faculties.clone();
    }

    public int getNumOfFaculties() {
        return numOfFaculties;
    }

    @Override
    public String toString() {
        String result = "РЕЄСТР КАФЕДР (Всього:" + numOfFaculties + ") \n";

        for(int i = 0 ;i<numOfFaculties;i++){
            result = result + (i + 1) + ". " + faculties[i].toString() + "\n";
            result = result + "--------------------------------------------------\n";
        }
        return result;
    }
}
