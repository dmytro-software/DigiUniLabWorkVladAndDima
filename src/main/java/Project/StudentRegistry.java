package Project;

import java.util.Arrays;

public class StudentRegistry {
    private Student[] students;
    private int numberOfStudents;

    public StudentRegistry() {
        this.students = new Student[10];
    }

    public void addStudent(Student student){
        if(numberOfStudents >= students.length){
            resizeArray(numberOfStudents + 1);
        }
        students[numberOfStudents++] = student;
    }

    public void removeStudent(int id) {
        for (int i = 0; i < numberOfStudents; i++) {
            //шукаємо за айді
            if (students[i].getIdPerson() == id) {
                Student[] newArray = new Student[numberOfStudents - 1];
                // пояснення для себе
                // копіювання все до вибраного елементу
                System.arraycopy(students, 0, newArray, 0, i);
                // копіювання все після вибраного елементу
                System.arraycopy(students, i + 1, newArray, i, numberOfStudents - i - 1);

                students = newArray;
                numberOfStudents--;
                return;
            }
        }
        throw new IllegalArgumentException("Student not found");
    }

    public void editStudent(int grateBookId,
                            int course,
                            int group,
                            String formOfEducation,
                            String studentStatus) {

        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getGrateBookId() == grateBookId) {

                students[i].setCourse(course);
                students[i].setGroup(group);
                students[i].setFormOfEducation(formOfEducation);
                students[i].setStudentStatus(studentStatus);

                return;
            }
        }
        throw new IllegalArgumentException("Student not found");
    }

    public void resizeArray(int i){
        Student[] newarray = new Student[i];
        System.arraycopy(students, 0, newarray, 0, students.length);
        students = newarray;
    }

    public Student[] getStudents() {
        return students.clone();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    @Override
    public String toString() {
        return "StudentRegistry{" +
                "students=" + Arrays.toString(students) +
                ", numberOfStudents=" + numberOfStudents +
                '}';
    }
}
