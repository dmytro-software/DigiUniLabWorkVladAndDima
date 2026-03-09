package Project.service;

import Project.Models.Teacher;

import java.util.List;

public interface TeacherService {

    void addTeacher(int departmentId,
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

    Teacher findByTeacherId(int teacherId);

    Teacher findByPib(String pib);
}