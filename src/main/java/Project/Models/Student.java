package Project.Models;

import Project.Annotation.EducationInfo;
import Project.Annotation.Identifier;
import Project.Annotation.ValueRange;

import java.time.LocalDate;
import java.util.List;

public class Student extends Person {
    @Identifier(label = "GradeBook")
    private final int gradeBookId;

    @ValueRange(min = 1, max = 6)
    private int course;
    private int group;

    @EducationInfo(isMutable = false)
    private final int enrollmentYear;

    @EducationInfo
    private String formOfEducation;

    @EducationInfo
    private String studentStatus;

    public Student(int idPerson, String pib, LocalDate birthDate, String email, int phoneNumber, int gradeBookId, int course,
                   int group, int  enrollmentYear, String formOfEducation, String studentStatus) {
        super(idPerson, pib, birthDate, email, phoneNumber);
        this.course = course;
        this.group = group;
        this.gradeBookId = gradeBookId;
        this.enrollmentYear = enrollmentYear;
        this.formOfEducation = formOfEducation;
        this.studentStatus = studentStatus;

    }

    public int getGradeBookId() {
        return gradeBookId;
    }

    public int getCourse() {
        return course;
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
                "   | Заліковка: " + gradeBookId +
                "  | Курс: " + course +
                "  | Група: " + group +
                "  | Рік: " + enrollmentYear +
                "  | Форма: " + formOfEducation +
                "  | Статус: " + studentStatus;
    }

}
