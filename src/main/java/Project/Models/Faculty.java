package Project.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Faculty {

    private final int idFaculty;
    private String facultyName;
    private String facultyShortName;
    private String headOfFaculty;
    private String contactsOfFaculty;
    private List<Department> departments = new ArrayList<>();

    public Faculty(int idFaculty,
                   String facultyName,
                   String facultyShortName,
                   String headOfFaculty,
                   String contactsOfFaculty) {

        this.idFaculty = idFaculty;
        this.facultyName = facultyName;
        this.facultyShortName = facultyShortName;
        this.headOfFaculty = headOfFaculty;
        this.contactsOfFaculty = contactsOfFaculty;
    }

    public int getIdFaculty() {
        return idFaculty;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getFacultyShortName() {
        return facultyShortName;
    }

    public String getHeadOfFaculty() {
        return headOfFaculty;
    }

    public String getContactsOfFaculty() {
        return contactsOfFaculty;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setFacultyShortName(String facultyShortName) {
        this.facultyShortName = facultyShortName;
    }

    public void setHeadOfFaculty(String headOfFaculty) {
        this.headOfFaculty = headOfFaculty;
    }

    public void setContactsOfFaculty(String contactsOfFaculty) {
        this.contactsOfFaculty = contactsOfFaculty;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + idFaculty +
                ", name='" + facultyName + '\'' +
                ", shortName='" + facultyShortName + '\'' +
                ", head='" + headOfFaculty + '\'' +
                ", contacts='" + contactsOfFaculty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return idFaculty == faculty.idFaculty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFaculty);
    }
}