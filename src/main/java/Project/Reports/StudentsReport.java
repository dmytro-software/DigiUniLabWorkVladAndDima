package Project.Reports;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.Teacher;
import Project.service.DepartmentService;

import java.util.*;

import static Project.Models.ConsoleColors.*;

public class StudentsReport {

    public static void getStudentsGroupedByCourse(List<Student> list){
        if (list == null || list.isEmpty()) {
            throw new EntityNotFoundException(RED + "No students found to display." + RESET);
        }
        List<Student> sortedByCourse = list.stream()
                .sorted(Comparator.comparingInt(Student::getCourse))
                .toList();

        int currentCourse = 0;
        for (Student student : sortedByCourse) {
            if (student.getCourse() != currentCourse) {
                if (currentCourse != 0) {
                    System.out.println();
                }
                System.out.println(CYAN + "====== Course " + student.getCourse() + " ===========" + RESET);
                currentCourse = student.getCourse();
            }
            System.out.println(student);
        }
    }




}
