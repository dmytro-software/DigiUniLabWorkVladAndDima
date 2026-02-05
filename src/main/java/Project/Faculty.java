package Project;

public class Faculty {

    private final int idFaculty;
    private String facultyName;
    private String facultyShortName;
    private String headOfFaculty;
    private String contactsOfFaculty;

    public Faculty(int idFaculty, String facultyName, String facultyShortName, String headOfFaculty, String contactsOfFaculty) {
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

    @Override
    public String toString() {
        return "Faculty{" +
                "idFaculty=" + idFaculty +
                ", facultyName='" + facultyName + '\'' +
                ", facultyShortName='" + facultyShortName + '\'' +
                ", headOfFaculty='" + headOfFaculty + '\'' +
                ", contactsOfFaculty='" + contactsOfFaculty + '\'' +
                '}';
    }
}
