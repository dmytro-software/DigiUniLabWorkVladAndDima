package Project.Reports;

import Project.Exceptions.IsEmptyException;
import Project.Models.Department;
import Project.Models.Student;
import Project.Models.Teacher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Project.Models.ConsoleColors.*;

public class DepartmentReport {

    public static void showStudentReportByCourseFromDepartment(Department department){
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Students by course from Department: " + department.getDepartmentName());
        System.out.println("=======================================================" + RESET);

        if(department.getStudents() == null || department.getStudents().isEmpty()){
            throw new IsEmptyException(RED + "No students in this Department" + RESET);
        }

        List<Student> sortedStudentsByCourseFromDepartment = department.getStudents().stream().sorted(Comparator.comparing(Student::getCourse)).toList();

        int currentCourse = 0;
        for(Student student : sortedStudentsByCourseFromDepartment){
            if (student.getCourse() != currentCourse) {
                if (currentCourse != 0) {
                    System.out.println();
                }
                System.out.println(CYAN + "====== Курс " + student.getCourse() + " ===========" + RESET);
                currentCourse = student.getCourse();
            }
            System.out.println(student);
        }

        System.out.println(CYAN_BOLD + "=======================================================\n" + RESET);
    }

    public static void showStudentsReportFromDepartmentByAlphabet(Department department){
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Students by alphabet from Department: " + department.getDepartmentName());
        System.out.println("=======================================================" + RESET);

        if(department.getStudents() == null || department.getStudents().isEmpty()){
            throw new IsEmptyException(RED + "No students in this Department" + RESET);
        }

        List<Student> sortedStudentsByAlphabetFromDepartment = department.getStudents().stream().sorted(Comparator.comparing(Student::getPib)).toList();

        for(Student student : sortedStudentsByAlphabetFromDepartment){
            System.out.println(student);
        }
    }

    public static void showTeachersReportFromDepartmentByAlphabet(Department department){
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Teachers by alphabet from Department: " + department.getDepartmentName());
        System.out.println("=======================================================" + RESET);

        if(department.getTeachers() == null || department.getTeachers().isEmpty()){
            throw new IsEmptyException(RED + "No teachers in this Department" + RESET);
        }

        List<Teacher> sortedTeachersByAlphabetFromDepartment = department.getTeachers().stream().sorted(Comparator.comparing(Teacher::getPib)).toList();

        for(Teacher teacher : sortedTeachersByAlphabetFromDepartment){
            System.out.println(teacher);
        }
    }

    public static void showStudnetsReportByChoosedCourseFromDepartment(Department department, int course){
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Students by course" + course + " from Department: " + department.getDepartmentName());
        System.out.println("=======================================================" + RESET);

        if(department.getStudents() == null || department.getStudents().isEmpty()) {
            throw new IsEmptyException(RED + "No students in this Department" + RESET);
        }

        List<Student> sortedStudentsByChoosedCourseFromDepartment = department.getStudents().stream().filter(student -> student.getCourse() == course).toList();

        for(Student student: sortedStudentsByChoosedCourseFromDepartment){
            System.out.println(student);
        }

        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Students by course" + course + " from Department by Alphabet: " + department.getDepartmentName());
        System.out.println("=======================================================" + RESET);

        List<Student> sortedStudentsByChoosedCourseFromDepartmentByAlphabet = department.getStudents().stream().filter(student -> student.getCourse() == course).sorted(Comparator.comparing(Student::getPib)).toList();

        for(Student student: sortedStudentsByChoosedCourseFromDepartmentByAlphabet){
            System.out.println(student);
        }
    }

}
