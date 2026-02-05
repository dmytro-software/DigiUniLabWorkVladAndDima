package Project;
public class Student extends Person {
    private final int grateBookId;
    private int course;
    private int group;
    private final int enrollmentYear;
    private String formOfEducation;
    private String studentStatus;

    public Student(int idPerson, String pib, String birthDate, String email, int phoneNumber, int grateBookId, int enrollmentYear) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.course = course;

        this.grateBookId = grateBookId;
        this.enrollmentYear = enrollmentYear;
    }

    public int getGrateBookId() {
        return grateBookId;
    }

    public int getCourse() {
        return course;
    }


}
