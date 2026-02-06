package Project;
public class Student extends Person {
    private final int grateBookId;
    private int course;
    private int group;
    private final int enrollmentYear;
    private String formOfEducation;
    private String studentStatus;

    public Student(int idPerson, String pib, String birthDate, String email, int phoneNumber, int grateBookId, int course, int group, int  enrollmentYear, String formOfEducation, String studentStatus) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.course = course;
        this.group = group;
        this.grateBookId = grateBookId;
        this.enrollmentYear = enrollmentYear;
        this.formOfEducation = formOfEducation;
        this.studentStatus = studentStatus;
    }

    public int getGrateBookId() {
        return grateBookId;
    }

    public int getCourse() {
        return course;
    }

    public int getGroup(){
        return group;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getFormOfEducation() {
        return formOfEducation;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    @Override
    public String toString() {
        return super.toString() + "grateBookId=" + grateBookId +
                ", course=" + course +
                ", group=" + group +
                ", enrollmentYear=" + enrollmentYear +
                ", formOfEducation='" + formOfEducation + '\'' +
                ", studentStatus='" + studentStatus + '\'' +
                '}';
    }
}
