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

    public Faculty getFaculty() {
        return faculty;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public int getRoomNumberOfDepartment() {
        return roomNumberOfDepartment;
    }

    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", departmentName='" + departmentName + '\'' +
                ", faculty=" + faculty +
                ", headOfDepartment='" + headOfDepartment + '\'' +
                ", roomNumberOfDepartment=" + roomNumberOfDepartment +
                '}';
    }
}
