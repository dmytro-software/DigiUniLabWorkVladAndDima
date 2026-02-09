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

    public void removeDepartment(int id) {
        for (int i = 0; i < numOfDepartments; i++) {
            //шукаємо за айді
            if (departments[i].getIdDepartment() == id) {
                Department[] newArray = new Department[numOfDepartments - 1];
                // пояснення для себе
                // копіювання все до вибраного елементу
                System.arraycopy(departments, 0, newArray, 0, i);
                // копіювання все після вибраного елементу
                System.arraycopy(departments, i + 1, newArray, i, numOfDepartments - i - 1);

                departments = newArray;
                numOfDepartments--;
                return;
            }
        }
        throw new IllegalArgumentException("КАФЕДРУ НЕ ЗНАЙДЕНО");
    }

    public void editDepartment(int idDepartment,
                            String departmentName,
                               Faculty faculty,
                            String headOfDepartment,
                            int roomNumberOfDepartment) {

        for (int i = 0; i < numOfDepartments; i++) {
            if (departments[i].getIdDepartment() == idDepartment) {

                departments[i].setDepartmentName(departmentName);
                departments[i].setFaculty(faculty);
                departments[i].setHeadOfDepartment(headOfDepartment);
                departments[i].setRoomNumberOfDepartment(roomNumberOfDepartment);
                return;
            }
        }
        throw new IllegalArgumentException("Department not found");
    }

    public Department findDepartmentById(int idDepartment) {
        for (Department department : departments) {
            if (department.getIdDepartment() == idDepartment) {
                return department;
            }
        }
        throw new IllegalArgumentException("Department with department ID " + idDepartment + " not found.");
    }

    //метод для збільшення масиву
    private void resizeArray(int i){
        Department[] newArray = new Department[i];
        System.arraycopy(departments,0,newArray,0,departments.length);
        departments = newArray;
    }


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
