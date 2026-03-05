package Project.service.Impl;

import Project.Models.Department;
import Project.Models.Student;
import Project.Models.Teacher;
import Project.service.DepartmentService;
import Project.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private final DepartmentService departmentService;

    public TeacherServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void addTeacher(int departmentId,
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
                           double fullTimeEquivalent) {

        // 1. Знаходимо кафедру
        Department department = departmentService.findById(departmentId);

        // 2. Створюємо викладача
        Teacher newTeacher = new Teacher(
                idPerson,
                pib,
                birthDate,
                email,
                phoneNumber,
                teacherId,
                position,
                department,
                academicDegree,
                academicRank,
                hireDate,
                fullTimeEquivalent
        );

        // 3. Додаємо до кафедри
        department.getTeachers().add(newTeacher);
    }

    @Override
    public boolean removeTeacher(int teacherId) {

        for (Department department : departmentService.findAll()) {

            boolean removed = department.getTeachers()
                    .removeIf(t -> t.getTeacherId() == teacherId);

            if (removed) return true;
        }

        return false;
    }

    @Override
    public void editTeacher(int teacherId,
                            String position,
                            String academicDegree,
                            String academicRank,
                            String hireDate,
                            Double fullTimeEquivalent) {

        for (Department department : departmentService.findAll()) {

            for (Teacher teacher : department.getTeachers()) {

                if (teacher.getTeacherId() == teacherId) {

                    if (position != null && !position.isBlank())
                        teacher.setPosition(position);

                    if (academicDegree != null && !academicDegree.isBlank())
                        teacher.setAcademicDegree(academicDegree);

                    if (academicRank != null && !academicRank.isBlank())
                        teacher.setAcademicRank(academicRank);

                    if (hireDate != null && !hireDate.isBlank())
                        teacher.setHireDate(hireDate);

                    if (fullTimeEquivalent != null)
                        teacher.setFullTimeEquivalent(fullTimeEquivalent);

                    return;
                }
            }
        }

        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
    }

    public List<Teacher> findAll() {

        List<Teacher> allTeachers = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            allTeachers.addAll(department.getTeachers());
        }

        return allTeachers;
    }
}