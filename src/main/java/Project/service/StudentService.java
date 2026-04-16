package Project.service;

import Project.Models.Department;
import Project.Models.Student;

import java.io.IOException;
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
                    String studentStatus) throws IOException;

    boolean removeStudent(int gradeBookId) throws IOException;

    void editStudent(
            int gradeBookId,
            String pib,
            String email,
            int phoneNumber,
            int course,
            int group,
            String formOfEducation,
            String studentStatus) throws IOException;

    List<Student> findAll() throws IOException;

    Optional<Student> findStudentByGradeBook(int gradeBookId) throws IOException;

    List<Student> findByPib(String pib) throws IOException;

    List<Student> findByGroup(int group) throws IOException;

    List<Student> findByCourse(int course) throws IOException;

    void changeDepartment(Department department, Student student);


}
