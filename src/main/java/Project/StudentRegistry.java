package Project;

import java.util.Arrays;
import java.util.Optional;

public class StudentRegistry {
    private Student[] students;
    private int numberOfStudents;

    public StudentRegistry() {
        this.students = new Student[1];
    }

    public void addStudent(Student student) {
        if (numberOfStudents >= students.length) {
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

    public Student findStudentByGradeBook(int id) {
        for (Student s : students) {
            if (s.getGrateBookId() == id) {
                return s;
            }
        }
        throw new IllegalArgumentException("Student with GradeBook ID " + id + " not found.");
    }

    public void resizeArray(int i) {
        Student[] newarray = new Student[i];
        System.arraycopy(students, 0, newarray, 0, students.length);
        students = newarray;
    }

    public Student[] findByPib(String pib) {
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getPib().equalsIgnoreCase(pib)) {
                newArray[foundCount++] = students[i];
            }
        }
        return Arrays.copyOf(newArray, foundCount);
    }

    public Student[] findByGroup(int group) {
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getGroup() == group) {
                newArray[foundCount++] = students[i];
            }
        }
        return Arrays.copyOf(newArray, foundCount);
    }

    public Student[] findByCourse(int course) {
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getCourse() == course) {
                newArray[foundCount++] = students[i];
            }
        }
        return Arrays.copyOf(newArray, foundCount);
    }

    public Student[] getStudents() {
        return students.clone();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    @Override
    public String toString() {
        String result = " РЕЄСТР СТУДЕНТІВ (Всього: " + numberOfStudents + ") \n";
        for (int i = 0; i < numberOfStudents; i++) {
            result = result + (i + 1) + ". " + students[i].toString() + "\n";
            result = result + "--------------------------------------------------\n";
        }
        return result;
    }
}

