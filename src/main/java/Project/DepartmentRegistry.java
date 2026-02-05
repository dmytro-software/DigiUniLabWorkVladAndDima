package Project;

import java.util.Arrays;

public class DepartmentRegistry {

    private Department[] departments;
    private int numOfDepartments;

    public DepartmentRegistry() {
        this.departments = new Department[1];
    }

    //Метод для створення кафедр, щоб занести його в масив
    public void addDepartment(Department department){
        //якщо число факультетів більша за розмір масиву то ми його розширюємо
        if(numOfDepartments >= departments.length){
            resizeArray(numOfDepartments + 1);
        }
        departments[numOfDepartments++] = department;
    }

    //метод для збільшення масиву
    private void resizeArray(int i){
        Department[] newArray = new Department[i];
        System.arraycopy(departments,0,newArray,0,departments.length);
        departments = newArray;
    }

    //повернути масив кафдер(клонований)
    public Department[] getDepartments() {
        return departments.clone();
    }

    public int getNumOfDepartments() {
        return numOfDepartments;
    }

    @Override
    public String toString() {
        return "DepartmentRegistry{" +
                "departments=" + Arrays.toString(departments) +
                ", numOfDepartments=" + numOfDepartments +
                '}';
    }
}
