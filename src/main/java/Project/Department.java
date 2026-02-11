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

    public Department(int idDepartment, String departmentName, Faculty faculty, String headOfDepartment, int roomNumberOfDepartment) {
        this.idDepartment = idDepartment;
        this.departmentName = departmentName;
        this.faculty = faculty;
        this.headOfDepartment = headOfDepartment;
        this.roomNumberOfDepartment = roomNumberOfDepartment;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public int getRoomNumberOfDepartment() {
        return roomNumberOfDepartment;
    }

    public void setRoomNumberOfDepartment(int roomNumberOfDepartment) {
        this.roomNumberOfDepartment = roomNumberOfDepartment;
    }

    public void addTeacher(Teacher teacher) {
        if (numberOfTeachers >= teachers.length) {
            Teacher[] newArray = new Teacher[numberOfTeachers + 1];
            System.arraycopy(teacher, 0, newArray, 0, teachers.length);
            teachers = newArray;
        }
        teachers[numberOfTeachers++] = teacher;
    }
    public Teacher[] getTeachers() {
        return Arrays.copyOf(teachers, numberOfTeachers);
    }

    @Override
    public String toString() {
        String result = "Department:\n";
        result += "ID: " + idDepartment + "\n";
        result += "Name: " + departmentName + "\n";
        result += "Faculty: " + faculty.getFacultyName() + "\n";
        result += "Head: " + headOfDepartment + "\n";
        result += "Room: " + roomNumberOfDepartment + "\n";
        result += "Teachers:\n";

        for (int i = 0; i < numberOfTeachers; i++) {
            result += "   - " + teachers[i].getPib() +
                    " (" + teachers[i].getAcademicDegree() + ")\n";
        }

        return result;
    }

}
