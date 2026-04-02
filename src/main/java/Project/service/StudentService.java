package Project.service;

import Project.Models.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    void addStudent(int departmentId,
                    int idPerson,
                    String pib,
                    LocalDate birthDate,
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
            String email,
            int phoneNumber,
            int course,
            int group,
            String formOfEducation,
            String studentStatus);

    List<Student> findAll();

    Optional<Student> findStudentByGradeBook(int gradeBookId);

    List<Student> findByPib(String pib);

    List<Student> findByGroup(int group);

    List<Student> findByCourse(int course);


}
