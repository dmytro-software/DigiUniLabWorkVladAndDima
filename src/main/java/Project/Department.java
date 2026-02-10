package Project;

public class Department {

    private final int idDepartment;
    private String departmentName;
    private Faculty faculty;
    private String headOfDepartment;
    private int roomNumberOfDepartment;

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

    @Override
    public String toString() {
        return "  |Кафедра ID: " + idDepartment +
                "\n | Назва: " + departmentName +
                "\n | Завідувач: " + headOfDepartment +
                "\n | Ауд: " + roomNumberOfDepartment +
                "\n   └─ " + faculty;
    }
}
