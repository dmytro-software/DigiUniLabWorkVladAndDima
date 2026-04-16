package Project.service;

import Project.Models.Teacher;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherService {

    void addTeacher(int departmentId,
                    int idPerson,
                    String pib,
                    LocalDate birthDate,
                    String email,
                    int phoneNumber,
                    int teacherId,
                    String position,
                    String academicDegree,
                    String academicRank,
                    String hireDate,
                    double fullTimeEquivalent) throws IOException;

    boolean removeTeacher(int teacherId) throws IOException;

    void editTeacher(int departmentId,
                     int idPerson,
                     String pib,
                     String birthDate,
                     String email,
                     int phoneNumber,
                     int teacherId,
                     String position,
                     String academicDegree,
                     String academicRank,
                     String hireDate,
                     Double fullTimeEquivalent) throws IOException;

    List<Teacher> findAll() throws IOException;

    Optional<Teacher> findByTeacherId(int teacherId) throws IOException;

    Optional<Teacher> findByPib(String pib) throws IOException;
}