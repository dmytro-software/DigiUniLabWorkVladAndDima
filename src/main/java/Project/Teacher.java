package Project;

public class Teacher extends Person{
    private final int teacherId;
    private String position;
    private Department department;
    private String academicDegree;
    private String academicRank;
    private String hireDate;
    private double fullTimeEquivalent;

    public Teacher(int idPerson, String pib, String birthDate, String email,
                   int phoneNumber, int teacherId, String position, Department department, String academicDegree, String academicRank, String hireDate, double fullTimeEquivalent) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.teacherId = teacherId;
        this.position = position;
        this.department = department;
        this.academicDegree = academicDegree;
        this.academicRank = academicRank;
        this.hireDate = hireDate;
        this.fullTimeEquivalent = fullTimeEquivalent;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public double getFullTimeEquivalent() {
        return fullTimeEquivalent;
    }

    public void setFullTimeEquivalent(double fullTimeEquivalent) {
        this.fullTimeEquivalent = fullTimeEquivalent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n | ID: " + teacherId +
                "\n | Посада: " + position +
                "\n | Ступінь: " + academicDegree +
                "\n | Звання: " + academicRank +
                "\n | Дата найму: " + hireDate +
                "\n | Ставка: " + fullTimeEquivalent;
    }
}
