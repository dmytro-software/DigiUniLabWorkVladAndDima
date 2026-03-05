package Project.service;

import Project.Models.Student;

import java.util.List;

public interface StudentService {
    void addStudent(int departmentId,
                    int idPerson,
                    String pib,
                    String birthDate,
                    String email,
                    int phoneNumber,
                    int gradeBookId,
                    int course,
                    int group,
                    int enrollmentYear,
                    String formOfEducation,
                    String studentStatus);

    boolean removeStudent(int gradeBookId);

    void editStudent(
            int gradeBookId,
            String pib,
            String birthDate,
            String email,
            int phoneNumber,
            int course,
            int group,
            int enrollmentYear,
            String formOfEducation,
            String studentStatus);

    List<Student> findAll();

    Student findStudentByGradeBook(int gradeBookId);
}
