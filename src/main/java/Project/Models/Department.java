package Project.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {

    private final int idDepartment;
    private String departmentName;
    private String headOfDepartment;
    private int roomNumber;
    private int facultyId;
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();


    public Department(int idDepartment, String departmentName, int facultyId, String headOfDepartment, int roomNumber) {
        this.idDepartment = idDepartment;
        this.departmentName = departmentName;
        this.facultyId = facultyId;
        this.headOfDepartment = headOfDepartment;
        this.roomNumber = roomNumber;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + idDepartment +
                ", name='" + departmentName + '\'' +
                ", head='" + headOfDepartment + '\'' +
                ", room=" + roomNumber +
                ", faculty id =" + facultyId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return idDepartment == that.idDepartment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDepartment);
    }
}