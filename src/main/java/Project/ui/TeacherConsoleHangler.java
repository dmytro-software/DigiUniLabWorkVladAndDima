package Project.ui;

import Project.Models.Teacher;
import Project.service.TeacherService;
import org.jline.reader.LineReader;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeacherConsoleHangler {
    private TeacherService teacherService;

    public TeacherConsoleHangler(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    public void handleAddTeacher(LineReader reader) {
        try {

            int departmentId = Integer.parseInt(reader.readLine("Department ID: "));

            int idPerson = (int) (Math.random() * 900000) + 100000;
            System.out.println("Person ID: " + idPerson);

            String pib = reader.readLine("Full Name (PIB): ");
            if (pib.isBlank() || !pib.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Invalid name format.");
                return;
            }

            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = LocalDate.parse(birthDate, formatter);

            if (birthLocalDate.isAfter(LocalDate.now())) {
                System.out.println("Birth date cannot be in future.");
                return;
            }

            int age = Period.between(birthLocalDate, LocalDate.now()).getYears();
            if (age < 16 || age > 60) {
                System.out.println("Student age must be between 16 and 60.");
                return;
            }

            String email = reader.readLine("Email: ");
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("Invalid email format.");
                return;
            }

            String phoneStr = reader.readLine("Phone (10 digits): ");
            if (!phoneStr.matches("\\d{10}")) {
                System.out.println("Phone must be 10 digits.");
                return;
            }
            int phoneNumber = Integer.parseInt(phoneStr);

            int teacherId = (int) (Math.random() * 900000) + 100000;
            System.out.println("Teacher ID: " + teacherId);

            String position = reader.readLine("Position: ");

            if (position.isBlank() || !position.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Position is required and must contain only letters.");
                return;
            }
            String academicDegree = reader.readLine("Academic Degree: ");

            if (academicDegree.isBlank() || !academicDegree.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Validation Error.");
                return;
            }
            String academicRank = reader.readLine("Academic Rank: ");

            String hireDate = reader.readLine("Hire Date (YYYYMMDD): ");
            if (hireDate.isBlank() || !hireDate.matches("\\d{8}")) {
                System.out.println("Validation Error: Hire Date must be exactly 8 digits (format: YYYYMMDD).");
                return;
            }

            System.out.print("Full Time Equivalent: ");
            String input = reader.readLine().replace(',', '.');
            double fullTimeEquivalent = Double.parseDouble(input);

            if (fullTimeEquivalent <= 0 || fullTimeEquivalent > 1.0) {
                System.out.println("FTE should be between 0.1 and 1.0.");
                return;
            }

            String departmentIdStr = reader.readLine("Department ID: ");
            if (departmentIdStr.isBlank() || !departmentIdStr.matches("\\d+")) {
                System.out.println("Department ID must be a number.");
                return;
            }

            teacherService.addTeacher(
                    departmentId,
                    idPerson,
                    pib, birthDate,
                    email,
                    phoneNumber,
                    teacherId,
                    position,
                    academicDegree,
                    academicRank,
                    hireDate,
                    fullTimeEquivalent
            );

            System.out.println("Student successfully added!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleRemoveTeacher(LineReader reader) {
        try {
            int teacherId = Integer.parseInt(reader.readLine("Enter teacher ID to remove: "));

            boolean removed = teacherService.removeTeacher(teacherId);

            if (removed) {
                System.out.println("Teacher removed successfully.");
            } else {
                System.out.println("Teacher not found.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void handleShowAllTeachers() {

       List<Teacher> teachers = teacherService.findAll();

        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }

        System.out.println("\n========== TEACHERS LIST ==========\n");

            for (Teacher t : teachers) {
                System.out.println("--------------------------------------------------");
                System.out.println("Person ID: " + t.getIdPerson());
                System.out.println("Full Name: " + t.getPib());
                System.out.println("Birth Date: " + t.getBirthDate());
                System.out.println("Email: " + t.getEmail());
                System.out.println("Phone: " + t.getPhoneNumber());
                System.out.println("ID: " + t.getTeacherId());
                System.out.println("Full Name: " + t.getPib());
                System.out.println("Email: " + t.getEmail());
                System.out.println("Phone: " + t.getPhoneNumber());
                System.out.println("Position: " + t.getPosition());
                System.out.println("Degree: " + t.getAcademicDegree());
                System.out.println("Rank: " + t.getAcademicRank());
                System.out.println("Hire date: " + t.getHireDate());
                System.out.println("FTE: " + t.getFullTimeEquivalent());
            }
    }

}
