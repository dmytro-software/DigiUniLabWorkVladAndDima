package Project.service;

import Project.Models.Teacher;

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
                    double fullTimeEquivalent);

    boolean removeTeacher(int teacherId);

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
                     Double fullTimeEquivalent);

    List<Teacher> findAll();

    Optional<Teacher> findByTeacherId(int teacherId);

    Optional<Teacher> findByPib(String pib);
}