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
        return  " |Факультет: " + facultyName + " (коротко: " + facultyShortName + ")" +
                " | Декан: " + headOfFaculty +
                " | Контакти: " + contactsOfFaculty;
    }
}
