package Project.Reports;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.Teacher;

import java.util.Comparator;
import java.util.List;

import static Project.Models.ConsoleColors.*;

public class FacultyReport {
    public static void generateStudentsAlphabeticalReport(Faculty faculty) {
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Students on Faculty: " + faculty.getFacultyName() + " (А-Я)");
        System.out.println("=======================================================" + RESET);

        if (faculty.getDepartments() == null || faculty.getDepartments().isEmpty()) {
            throw new EntityNotFoundException(RED + "No departments there" + RESET);
        }

        List<Student> allStudents = faculty.getDepartments().stream()
                .filter(d -> d.getStudents() != null)
                .flatMap(d -> d.getStudents().stream())
                .sorted(Comparator.comparing(Student::getPib))
                .toList();

        if (allStudents.isEmpty()) {
            throw new EntityNotFoundException(RED + "No students found" + RESET);
        } else {
            allStudents.forEach(System.out::println);
        }

        System.out.println(CYAN_BOLD + "=======================================================\n" + RESET);
    }

    public static void generateTeachersAlphabeticalReport(Faculty faculty) {
        System.out.println(CYAN_BOLD + "\n=======================================================");
        System.out.println("Teachers on Faculty : " + faculty.getFacultyName() + " (А-Я)");
        System.out.println("=======================================================" + RESET);

        if (faculty.getDepartments() == null || faculty.getDepartments().isEmpty()) {
            throw new EntityNotFoundException(RED + "No departments there" + RESET);
        }

        List<Teacher> allTeachers = faculty.getDepartments().stream()
                .filter(d -> d.getTeachers() != null)
                .flatMap(d -> d.getTeachers().stream())
                .sorted(Comparator.comparing(Teacher::getPib))
                .toList();

        if (allTeachers.isEmpty()) {
            System.out.println(RED + "No teachers found" + RESET);
        } else {
            allTeachers.forEach(System.out::println);
        }

        System.out.println(CYAN_BOLD + "=======================================================\n" + RESET);
    }
}
