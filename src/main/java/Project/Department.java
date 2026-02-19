package Project;

import java.util.Arrays;

public class Department {

    private final int idDepartment;
    private String departmentName;
    private Faculty faculty;
    private String headOfDepartment;
    private int roomNumberOfDepartment;


    private Teacher[] teachers = new Teacher[1];
    private int numberOfTeachers;

    private Student[] students = new Student[1];
    private int numberOfStudents;

    public Department(int idDepartment, String departmentName, Faculty faculty, String headOfDepartment, int roomNumberOfDepartment) {
        if (idDepartment <= 0) {
            throw new IllegalArgumentException("ПОМИЛКА: ID КАФЕДРИ МАЄ БУТИ ДОДАТНИМ ЧИСЛОМ");
        }
        this.idDepartment = idDepartment;

        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException("ПОМИЛКА: НАЗВА КАФЕДРИ НЕ МОЖЕ БУТИ ПОРОЖНЬОЮ");
        }
        this.departmentName = departmentName;

        if (faculty == null) {
            throw new IllegalArgumentException("ПОМИЛКА: КАФЕДРА МАЄ БУТИ ПРИВ'ЯЗАНА ДО ФАКУЛЬТЕТУ");
        }
        this.faculty = faculty;

        if (headOfDepartment == null || headOfDepartment.isBlank()) {
            throw new IllegalArgumentException("ПОМИЛКА: ПІБ ЗАВІДУВАЧА КАФЕДРИ НЕ ВВЕДЕНО");
        }
        this.headOfDepartment = headOfDepartment;

        if (roomNumberOfDepartment <= 0) {
            throw new IllegalArgumentException("ПОМИЛКА: НОМЕР АУДИТОРІЇ МАЄ БУТИ ДОДАТНИМ ЧИСЛОМ");
        }
        this.roomNumberOfDepartment = roomNumberOfDepartment;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isBlank()) {
            throw new IllegalArgumentException("ПОМИЛКА: НАЗВА КАФЕДРИ НЕ МОЖЕ БУТИ ПОРОЖНЬОЮ");
        }
        this.departmentName = departmentName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("ПОМИЛКА: ФАКУЛЬТЕТ НЕ МОЖЕ БУТИ NULL");
        }
        this.faculty = faculty;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        if (headOfDepartment == null || headOfDepartment.isBlank()) {
            throw new IllegalArgumentException("ПОМИЛКА: ПІБ ЗАВІДУВАЧА НЕ МОЖЕ БУТИ ПОРОЖНІМ");
        }
        this.headOfDepartment = headOfDepartment;
    }

    public int getRoomNumberOfDepartment() {
        return roomNumberOfDepartment;
    }

    public void setRoomNumberOfDepartment(int roomNumberOfDepartment) {
        if (roomNumberOfDepartment <= 0) {
            throw new IllegalArgumentException("ПОМИЛКА: НОМЕР АУДИТОРІЇ МАЄ БУТИ ДОДАТНИМ ЧИСЛОМ");
        }
        this.roomNumberOfDepartment = roomNumberOfDepartment;
    }

    public void addTeacher(Teacher teacher) {
        if (numberOfTeachers >= teachers.length) {
            Teacher[] newArray = new Teacher[teachers.length + 1];
            System.arraycopy(teachers, 0, newArray, 0, teachers.length);
            teachers = newArray;
        }
        teachers[numberOfTeachers++] = teacher;

    }

    public void removeTeacher(Teacher teacher) {
        boolean found = false;
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i] == teacher) {
                found = true;
                System.arraycopy(teachers, i + 1, teachers, i, numberOfTeachers - i - 1);
                teachers[--numberOfTeachers] = null;
                break;
            }
        }
        if (!found) {
            System.out.println("Teacher not found in this department.");
        }
    }

    public void editTeacher(int teacherId, String pib, String email, Integer phoneNumber, String position, String academicDegree,
                            String academicRank, String hireDate, Double fullTimeEquivalent, Department department) {
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i].getTeacherId() == teacherId) {
                Teacher t = teachers[i];

                if (pib != null && !pib.isBlank()) {
                    t.setPib(pib);
                }
                if (email != null && !email.isBlank()) {
                    t.setEmail(email);
                }
                if (phoneNumber != null) {
                    t.setPhoneNumber(phoneNumber);
                }
                if (position != null) {
                    t.setPosition(position);
                }
                if (academicDegree != null) {
                    t.setAcademicDegree(academicDegree);
                }
                if (academicRank != null) {
                    t.setAcademicRank(academicRank);
                }
                if (hireDate != null) {
                    t.setHireDate(hireDate);
                }
                if (fullTimeEquivalent != null) {
                    t.setFullTimeEquivalent(fullTimeEquivalent);
                }
                if (department != null) {
                    t.setDepartment(department);
                }

                return;
            }
        }
    }


    public void addStudent(Student student) {
        if (numberOfStudents >= students.length) {
            students = Arrays.copyOf(students, numberOfStudents + 1);
        }
        students[numberOfStudents++] = student;
    }


    public void editStudent(int gradeBookId, String pib, Integer course, Integer group, Faculty faculty, String formOfEducation,
                            String studentStatus,String email, Integer phoneNumber) {
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].getGradeBookId() == gradeBookId) {
                Student s = students[i];

                if (pib != null && !pib.isBlank()) {
                    s.setPib(pib);
                }
                if (email != null && !email.isBlank()) {
                    s.setEmail(email);
                }
                if (phoneNumber != null) {
                    s.setPhoneNumber(phoneNumber);
                }
                if (course != null) {
                    s.setCourse(course);
                }
                if (group != null) {
                    s.setGroup(group);
                }
                if (faculty != null) {
                    s.setFaculty(faculty);
                }
                if (formOfEducation != null && !formOfEducation.isBlank()) {
                    s.setFormOfEducation(formOfEducation);
                }
                if (studentStatus != null && !studentStatus.isBlank()) {
                    s.setStudentStatus(studentStatus);
                }

                return;
            }
        }
    }

    public void removeStudent(Student student) {
        boolean found = false;
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i] == student) {
                found = true;
                // Зсуваємо всі елементи після видаленого на одну позицію вліво
                System.arraycopy(students, i + 1, students, i, numberOfStudents - i - 1);
                students[--numberOfStudents] = null; // Очищаємо останній елемент
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found in this department.");
        }
    }


    public Teacher findTeacherById(int teacherId) {
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i].getTeacherId() == teacherId) {
                return teachers[i];
            }
        }
        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
    }

    public Student findStudentByGradeBook(int gradeBookId) {
        for (Student s : students) {
            if (s != null && s.getGradeBookId() == gradeBookId) {
                return s;
            }
        }
        return null;
    }

    public Teacher[] getTeachers() {
        return Arrays.copyOf(teachers, numberOfTeachers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("============================================================\n");
        sb.append("Department:\n");
        sb.append("------------------------------------------------------\n");
        sb.append("ID: ").append(idDepartment).append("\n");
        sb.append("Name: ").append(departmentName).append("\n");
        sb.append("Faculty: ").append(faculty.getFacultyName()).append("\n");
        sb.append("Head: ").append(headOfDepartment).append("\n");
        sb.append("Room: ").append(roomNumberOfDepartment).append("\n");
        sb.append("------------------------------------------------------\n");
        sb.append("Teachers:\n");

        if (numberOfTeachers == 0) {
            sb.append("На даний момент викладачів немає\n");
        } else {
            for (int i = 0; i < numberOfTeachers; i++) {
                sb.append("   - ")
                        .append(teachers[i].getPib())
                        .append(" (")
                        .append(teachers[i].getAcademicDegree())
                        .append(")\n");
            }
        }

        sb.append("------------------------------------------------------\n");
        sb.append("Студентів зареєстровано: ").append(numberOfStudents).append("\n");
        sb.append("СПИСОК СТУДЕНТІВ:\n");

        if (numberOfStudents == 0) {
            sb.append("На даний момент студентів немає\n");
        } else {
            for (int i = 0; i < numberOfStudents; i++) {
                sb.append(String.format("    %2d. %-25s | Група: %s\n",
                        (i + 1),
                        students[i].getPib(),
                        students[i].getGroup()));
            }
        }

        sb.append("============================================================\n");

        return sb.toString();
    }
}