package Project.service.Impl;

import Project.Models.Department;
import Project.Models.Student;
import Project.service.DepartmentService;
import Project.service.StudentService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private List<Student> students = new ArrayList<>();
    private final DepartmentService departmentService;

    public StudentServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void addStudent(int departmentId,
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
                           String studentStatus) {

        Department department = departmentService.findById(departmentId);

        Student newStudent = new Student(
                idPerson,
                pib,
                birthDate,
                email,
                phoneNumber,
                gradeBookId,
                course,
                group,
                enrollmentYear,
                formOfEducation,
                studentStatus
        );

        // 3. Додаємо до кафедри
        department.getStudents().add(newStudent);
    }

    @Override
    public boolean removeStudent(int gradeBookId) {

        for (Department department : departmentService.findAll()) {

            boolean removed = department.getStudents()
                    .removeIf(s -> s.getGradeBookId() == gradeBookId);

            if (removed) return true;
        }

        return false;
    }

    @Override
    public void editStudent(int gradeBookId,
                            String pib,
                            String email,
                            int phoneNumber,
                            int course,
                            int group,
                            String formOfEducation,
                            String studentStatus) {

        for (Department department : departmentService.findAll()) {

            for (Student student : department.getStudents()) {

                if (student.getGradeBookId() == gradeBookId) {

                    student.setPib(pib);
                    student.setEmail(email);
                    student.setPhoneNumber(phoneNumber);
                    student.setCourse(course);
                    student.setGroup(group);
                    student.setFormOfEducation(formOfEducation);
                    student.setStudentStatus(studentStatus);

                    return;
                }
            }
        }

        throw new IllegalArgumentException("Student not found with gradeBookId: " + gradeBookId);
    }

    @Override
    public List<Student> findAll() {

        List<Student> allStudents = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            allStudents.addAll(department.getStudents());
        }

        return allStudents;
    }

    @Override
    public Student findStudentByGradeBook(int gradeBookId) {

        for (Department department : departmentService.findAll()) {

            for (Student student : department.getStudents()) {

                if (student.getGradeBookId() == gradeBookId) {
                    return student;
                }

            }
        }

        return null;
    }

    @Override
    public List<Student> findByPib(String pib) {
        List<Student> foundStudents = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getPib().equalsIgnoreCase(pib)) {
                    foundStudents.add(student);
                }
            }
        }
        System.out.println("Search results: " + pib);
        System.out.println("Found: " + foundStudents.size());
        return foundStudents;
    }

    @Override
    public List<Student> findByGroup(int group) {

        List<Student> foundStudents = new ArrayList<>();
        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getGroup() == group) {
                    foundStudents.add(student);
                }
            }
        }

        System.out.println("Search by group: " + group );
        System.out.println("Total found: " + foundStudents.size());
        return foundStudents;

    }

    @Override
    public List<Student> findByCourse(int course) {

        List<Student> foundStudents = new ArrayList<>();
        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getCourse() == course) {
                    foundStudents.add(student);
                }
            }
        }

        System.out.println("Search by course: " + course );
        System.out.println("Total found: " + foundStudents.size());
        return foundStudents;

    }
}