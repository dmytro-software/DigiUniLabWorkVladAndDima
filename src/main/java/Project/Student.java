package Project;
public class Student extends Person {
    private final int gradeBookId;
    private int course;
    private Faculty faculty;
    private int group;
    private final int enrollmentYear;
    private String formOfEducation;
    private String studentStatus;
    private Department department;

    public Student(int idPerson, String pib, String birthDate, String email, int phoneNumber, int gradeBookId, int course,
                   Faculty faculty, int group, int  enrollmentYear, String formOfEducation, String studentStatus) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.course = course;
        this.faculty = faculty;
        this.group = group;
        this.gradeBookId = gradeBookId;
        this.enrollmentYear = enrollmentYear;
        this.formOfEducation = formOfEducation;
        this.studentStatus = studentStatus;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getGradeBookId() {
        return gradeBookId;
    }

    public int getCourse() {
        return course;
    }

    public Faculty getFaculty(){
        return  faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getGroup(){
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(String formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }


    @Override
    public String toString() {
        return super.toString() +
                "\n  | Заліковка: " + gradeBookId +
                "\n | Курс: " + course +
                "\n | Група: " + group +
                "\n | Рік: " + enrollmentYear +
                "\n | Форма: " + formOfEducation +
                "\n | Статус: " + studentStatus;
    }
}
