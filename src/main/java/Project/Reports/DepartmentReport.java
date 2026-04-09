package Project.Reports;

import Project.Exceptions.IsEmptyException;
import Project.Models.Department;
import Project.Models.Student;

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
}
