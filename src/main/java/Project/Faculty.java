package Project;

import java.util.Arrays;

public class Faculty {

    private final int idFaculty;
    private String facultyName;
    private String facultyShortName;
    private String headOfFaculty;
    private String contactsOfFaculty;


    private Student[] students = new Student[1];
    private int numberOfStudents;

    public Faculty(int idFaculty, String facultyName, String facultyShortName,
                   String headOfFaculty, String contactsOfFaculty) {
        this.idFaculty = idFaculty;
        this.facultyName = facultyName;
        this.facultyShortName = facultyShortName;
        this.headOfFaculty = headOfFaculty;
        this.contactsOfFaculty = contactsOfFaculty;
    }


    public void addStudent(Student student) {
        if (numberOfStudents >= students.length) {
            Student[] newArray = new Student[numberOfStudents + 1];
            System.arraycopy(students, 0, newArray, 0, students.length);
            students = newArray;
        }
        students[numberOfStudents++] = student;
    }

    public void removeStudent(Student student) {
        boolean found = false;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i] == student) {
                found = true;
                // Зсув всіх елементів після видаленого
                for (int j = i; j < numberOfStudents - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[numberOfStudents - 1] = null;
                numberOfStudents--;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found in this faculty.");
        }
    }

    public Student[] getStudents() {
        return Arrays.copyOf(students, numberOfStudents);
    }

    public int getIdFaculty() {
        return idFaculty;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyShortName() {
        return facultyShortName;
    }

    public void setFacultyShortName(String facultyShortName) {
        this.facultyShortName = facultyShortName;
    }

    public String getHeadOfFaculty() {
        return headOfFaculty;
    }

    public void setHeadOfFaculty(String headOfFaculty) {
        this.headOfFaculty = headOfFaculty;
    }

    public String getContactsOfFaculty() {
        return contactsOfFaculty;
    }

    public void setContactsOfFaculty(String contactsOfFaculty) {
        this.contactsOfFaculty = contactsOfFaculty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();


        sb.append("============================================================");
        sb.append(String.format(String.valueOf(idFaculty)));
        sb.append("============================================================");

        sb.append(String.format("  Повна назва:  %s\n", facultyName));
        sb.append(String.format("  Абревіатура:  %s\n", facultyShortName));
        sb.append(String.format("  Декан:        %s\n", headOfFaculty));
        sb.append(String.format("  Контакти:     %s\n", contactsOfFaculty));

        sb.append("------------------------------------------------------\n");
        sb.append(String.format("  Студентів зареєстровано: %d\n", numberOfStudents));
        sb.append("------------------------------------------------------\n");

        sb.append("  СПИСОК СТУДЕНТІВ:\n");
        if (numberOfStudents == 0) {
            sb.append("На даний момент студентів немає");
        } else {
            for (int i = 0; i < numberOfStudents; i++) {

                sb.append(String.format("    %2d. %-25s | Група: %s\n",
                        (i + 1),
                        students[i].getPib(),
                        students[i].getGroup()));
            }
        }
        sb.append("============================================================");
        return sb.toString();
    }}
