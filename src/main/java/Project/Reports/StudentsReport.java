package Project.Reports;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.Teacher;
import Project.service.DepartmentService;

import java.util.*;

public class StudentsReport {

    public static void getStudentsGroupedByCourse(List<Student> list){
        List<Student> sortedByCourse = list.stream().sorted(Comparator.comparing(Student::getCourse)).toList();
        int currentCourse = 0;
        for(Student student : sortedByCourse){
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
