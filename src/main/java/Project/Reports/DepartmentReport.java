package Project.Reports;

import Project.Exceptions.IsEmptyException;
import Project.Models.Department;
import Project.Models.Student;

import java.util.Comparator;
import java.util.List;

public class DepartmentReport {

    public static void showStudentReportByCourseFromDepartment(Department department){
        System.out.println("Students by course from Department: " + department.getDepartmentName());

        if(department.getStudents() == null || department.getStudents().isEmpty()){
            throw new IsEmptyException("No students in this Department");
        }

        List<Student> sortedStudentsByCourseFromDepartment = department.getStudents().stream().sorted(Comparator.comparing(Student::getCourse)).toList();

        int currentCourse = 0;
        for(Student student : sortedStudentsByCourseFromDepartment){
            if (student.getCourse() != currentCourse) {
                if (currentCourse != 0) {
                    System.out.println();
                }
                System.out.println("====== Курс " + student.getCourse() + " ===========");
                currentCourse = student.getCourse();
            }
            System.out.println(student);
        }
    }
}
