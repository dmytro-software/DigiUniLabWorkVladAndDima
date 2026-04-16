package Project.Models;

import Project.Annotation.DisplayName;
import Project.Annotation.HeadOfUnit;
import Project.Annotation.Identifier;
import Project.Annotation.ValidContact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Faculty {
    @Identifier(label = "Faculty ID")
    private final int idFaculty;

    @DisplayName(value = "Name of Faculty")
    private String facultyName;

    @DisplayName(value = "Short Name of Faculty")
    private String facultyShortName;

    @HeadOfUnit(level = "Faculty")
    private String headOfFaculty;

    @ValidContact(format = "Phone")
    private String contactsOfFaculty;

    private List<Department> departments = new ArrayList<>();

    public Faculty() {
        idFaculty = 0;
    }

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

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + idFaculty +
                ", Name='" + facultyName + '\'' +
                ", Short Name='" + facultyShortName + '\'' +
                ", Head='" + headOfFaculty + '\'' +
                ", Contacts='" + contactsOfFaculty + '\'' +
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