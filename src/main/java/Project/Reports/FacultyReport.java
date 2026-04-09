package Project.Reports;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.Teacher;

import java.util.Comparator;
import java.util.List;

public class FacultyReport {
    public static void generateStudentsAlphabeticalReport(Faculty faculty) {
        System.out.println("Students on Faculty: " + faculty.getFacultyName() + " (А-Я)");

        if (faculty.getDepartments() == null || faculty.getDepartments().isEmpty()) {
            throw new EntityNotFoundException("No departments there");
        }

        List<Student> allStudents = faculty.getDepartments().stream()
                .filter(d -> d.getStudents() != null)
                .flatMap(d -> d.getStudents().stream())
                .sorted(Comparator.comparing(Student::getPib))
                .toList();

        if (allStudents.isEmpty()) {
            throw new EntityNotFoundException("No students found");
        } else {
            allStudents.forEach(System.out::println);
        }
    }

    public static void generateTeachersAlphabeticalReport(Faculty faculty) {
        System.out.println("Teachers on Faculty : " + faculty.getFacultyName() + " (А-Я)");

        if (faculty.getDepartments() == null || faculty.getDepartments().isEmpty()) {
           throw new EntityNotFoundException("No departments there");
        }

        List<Teacher> allTeachers = faculty.getDepartments().stream()
                .filter(d -> d.getTeachers() != null)
                .flatMap(d -> d.getTeachers().stream())
                .sorted(Comparator.comparing(Teacher::getPib))
                .toList();

        if (allTeachers.isEmpty()) {
            System.out.println("No teachers found");
        } else {
            allTeachers.forEach(System.out::println);
        }
    }
}
