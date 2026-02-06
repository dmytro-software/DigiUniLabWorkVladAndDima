package Project;

public class Teacher extends Person{
    private String position;
    private String academicDegree;
    private String academicRank;
    private String hireDate;
    private double fullTimeEquivalent;

    public Teacher(int idPerson, String pib, String birthDate, String email,
                   int phoneNumber, String position, String academicDegree, String academicRank, String hireDate, double fullTimeEquivalent) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.position = position;
        this.academicDegree = academicDegree;
        this.academicRank = academicRank;
        this.hireDate = hireDate;
        this.fullTimeEquivalent = fullTimeEquivalent;
    }

    public String getPosition() {
        return position;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public String getHireDate() {
        return hireDate;
    }

    public double getFullTimeEquivalent() {
        return fullTimeEquivalent;
    }

    @Override
    public String toString() {
        return super.toString() + "Teacher{" +
                "position='" + position + '\'' +
                ", academicDegree='" + academicDegree + '\'' +
                ", academicRank='" + academicRank + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", fullTimeEquivalent=" + fullTimeEquivalent +
                '}';
    }
}
