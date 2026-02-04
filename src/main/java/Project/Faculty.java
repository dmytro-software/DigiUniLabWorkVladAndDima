package Project;

public class Faculty {

    private final int idFaculty;
    private String facultyName;
    private String facultyShortName;
    private Teacher headOfFaculty;
    private String contactsOfFaculty;

    public Faculty(int idFaculty, String facultyName, String facultyShortName, Teacher headOfFaculty, String contactsOfFaculty) {
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

    public Teacher getHeadOfFaculty() {
        return headOfFaculty;
    }

    public String getContactsOfFaculty() {
        return contactsOfFaculty;
    }
}
