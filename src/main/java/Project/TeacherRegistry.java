package Project;

import java.util.Arrays;

public class TeacherRegistry {
    private Teacher[] teachers;
    private int numberOfTeachers;

    public TeacherRegistry() {
        this.teachers = new Teacher[10];
    }
    public void addTeacher(Teacher teacher){
        if(numberOfTeachers >= teachers.length){
            resizeArray(numberOfTeachers + 1);
        }
        teachers[numberOfTeachers++] = teacher;
    }

    public void removeTeacher(int id) {
        for (int i = 0; i < numberOfTeachers; i++) {
            //шукаємо за айді
            if (teachers[i].getIdPerson() == id) {
                Teacher[] newArray = new Teacher[numberOfTeachers - 1];
                // пояснення для себе
                // копіювання все до вибраного елементу
                System.arraycopy(teachers, 0, newArray, 0, i);
                // копіювання все після вибраного елементу
                System.arraycopy(teachers, i + 1, newArray, i, numberOfTeachers - i - 1);

                teachers = newArray;
                numberOfTeachers--;
                return;
            }
        }
        throw new IllegalArgumentException("TEACHER НЕ ЗНАЙДЕНО");
    }

    public void editTeacher(int teacherId,
                            String position,
                            String academicDegree,
                            String academicRank,
                            String hireDate,
                            double fullTimeEquivalent){
        for(int i = 0; i < numberOfTeachers; i++) {
            if(teachers[i].getTeacherId() == teacherId){
                teachers[i].setPosition(position);
                teachers[i].setAcademicDegree(academicDegree);
                teachers[i].setAcademicRank(academicRank);
                teachers[i].setHireDate(hireDate);
                teachers[i].setFullTimeEquivalent(fullTimeEquivalent);
                return;
            }
            throw new IllegalArgumentException("Teacher not found");
        }
    }

    public Teacher[] findByPib(String pib) {
        Teacher[] results = new Teacher[numberOfTeachers];
        int count = 0;
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i].getPib().equalsIgnoreCase(pib)) {
                results[count++] = teachers[i];
            }
        }
        return Arrays.copyOf(results, count);
    }


    //додав цей метод що за айді тічера шукало
    public Teacher findByTeacherId(int id) {
        for (Teacher tch : teachers) {
            if (tch.getTeacherId() == id) {
                return tch;
            }
        }
        throw new IllegalArgumentException("Teacher with TeacherID " + id + " not found.");
    }

    public void resizeArray(int i){
        Teacher[] newarray = new Teacher[i];
        System.arraycopy(teachers, 0, newarray, 0, teachers.length);
        teachers = newarray;
    }

    public Teacher[] getTeachers() {
        return teachers.clone();
    }

    public int getNumberOfTeachers() {
        return numberOfTeachers;
    }

    @Override
    public String toString() {
        String result = " РЕЄСТР ВЧИТЕЛІВ (Всього: " + numberOfTeachers + ") \n";
        for (int i = 0; i < numberOfTeachers; i++) {
            result = result + (i + 1) + ". " + teachers[i].toString() + "\n";
            result = result + "--------------------------------------------------\n";
        }
        return result;
    }
}
