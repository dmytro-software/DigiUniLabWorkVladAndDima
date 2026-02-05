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
        return "FacultyRegistry{" +
                "faculties=" + Arrays.toString(faculties) +
                ", numOfFaculties=" + numOfFaculties +
                '}';
    }
}
