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

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void editStudent(int grateBookId,
                            int course,
                            int group,
                            String formOfEducation,
                            String studentStatus) {

        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getGradeBookId() == grateBookId) {

                students[i].setCourse(course);
                students[i].setGroup(group);
                students[i].setFormOfEducation(formOfEducation);
                students[i].setStudentStatus(studentStatus);

                return;
            }
        }
        throw new IllegalArgumentException("Student not found");
    }

    public Student findStudentByGradeBook(int gradeBookId) {
        for (Student s : students) {
            if (s != null && s.getGradeBookId() == gradeBookId) {
                return s;
            }
        }
        return null;
    }

    public void removeStudent(int gradeBookId) {
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i] != null && students[i].getGradeBookId() == gradeBookId) {
                System.arraycopy(students, i + 1, students, i, numberOfStudents - i - 1);
                students[--numberOfStudents] = null;
                return;
            }
        }
    }


    public void resizeArray(int i) {
        Student[] newarray = new Student[i];
        System.arraycopy(students, 0, newarray, 0, students.length);
        students = newarray;
    }

    public Student[] findByPib(String pib) {
        if (pib.isEmpty() || pib.isBlank() || pib == null) {
            throw new IllegalArgumentException("EMPTY");
        }
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getPib().equalsIgnoreCase(pib)) {
                newArray[foundCount++] = students[i];
            }
        }

        System.out.println("Результат по пошуку ПІБ: " + pib);
        return Arrays.copyOf(newArray, foundCount);
    }

    public Student[] findByGroup(int group) {
        if (group < 0 || group > 6) {
            throw new IllegalArgumentException("Cannot be higher 6 or lower 0");
        }
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getGroup() == group) {
                newArray[foundCount++] = students[i];
            }
        }
        System.out.println("Студенти групи " + group);
        return Arrays.copyOf(newArray, foundCount);
    }

    public Student[] findByCourse(int course) {
        if (course < 0 || course > 4) {
            throw new IllegalArgumentException("Cannot be higher 4 or lower 0");
        }
        Student[] newArray = new Student[numberOfStudents];
        int foundCount = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getCourse() == course) {
                newArray[foundCount++] = students[i];
            }
        }
        System.out.println("Студенти курсу " + course);
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

