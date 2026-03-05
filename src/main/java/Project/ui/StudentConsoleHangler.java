package Project.ui;

import Project.Models.Student;
import Project.service.DepartmentService;
import Project.service.StudentService;
import org.jline.reader.LineReader;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class StudentConsoleHangler {
    private final StudentService studentService;

    public StudentConsoleHangler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handleAddStudent(LineReader reader) {
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

            int gradeBookId = (int) (Math.random() * 900000) + 100000;
            System.out.println("GradeBook ID: " + gradeBookId);

            int course = Integer.parseInt(reader.readLine("Course (1-4): "));
            int group = Integer.parseInt(reader.readLine("Group: "));
            int enrollmentYear = Integer.parseInt(reader.readLine("Enrollment year: "));
            String formOfEducation = reader.readLine("Form of Education: ");
            String studentStatus = reader.readLine("Student status: ");

            studentService.addStudent(
                    departmentId,
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

            System.out.println("Student successfully added!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleEditStudent(LineReader reader) {
        try {
                int gradeBooktId = Integer.parseInt(reader.readLine("Enter GradeBook ID of student to edit: "));
                Student student = studentService.findStudentByGradeBook(gradeBooktId);

                if (student == null) {
                    System.out.println("Student not found.");
                    return;
                }

                System.out.println("Editing student. Press Enter to keep current value.");

                String pib = reader.readLine("Full Name (" + student.getPib() + "): ");
                if (!pib.isBlank())
                    student.setPib(pib);

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

                String email = reader.readLine("Email (" + student.getEmail() + "): ");
                if (!email.isBlank())
                    student.setEmail(email);

                int phoneNumber = Integer.parseInt(reader.readLine("Phone Number (" + student.getPhoneNumber() + "): "));


                int course = Integer.parseInt(reader.readLine("Course (" + student.getCourse() + "): "));


                int group = Integer.parseInt(reader.readLine("Group (" + student.getGroup() + "): "));

                int enrollmentYear = Integer.parseInt(reader.readLine("Enrollment year (" + student.getEnrollmentYear() + "): "));

                String formOfEducation = reader.readLine("Form of Education (" + student.getFormOfEducation() + "): ");

                String studentStatus = reader.readLine("Status (" + student.getStudentStatus() + "): ");


                studentService.editStudent(
                        gradeBooktId,
                        pib,
                        birthDate,
                        email,
                        phoneNumber,
                        course,
                        group,
                        enrollmentYear,
                        formOfEducation,
                        studentStatus
                );

                System.out.println("Student successfully updated!");

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
    }

    public void handleRemoveStudent(LineReader reader) {
        try {
            int gradeBookId = Integer.parseInt(reader.readLine("Enter GradeBook ID to remove: "));

            boolean removed = studentService.removeStudent(gradeBookId);

            if (removed) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student not found.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
    public void handleShowAllStudents() {

        List<Student> students = studentService.findAll();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n========== STUDENTS LIST ==========\n");

        for (Student s : students) {

            System.out.println("--------------------------------------------------");
            System.out.println("Person ID: " + s.getIdPerson());
            System.out.println("Full Name: " + s.getPib());
            System.out.println("Birth Date: " + s.getBirthDate());
            System.out.println("Email: " + s.getEmail());
            System.out.println("Phone: " + s.getPhoneNumber());
            System.out.println("GradeBook ID: " + s.getGradeBookId());
            System.out.println("Course: " + s.getCourse());
            System.out.println("Group: " + s.getGroup());
            System.out.println("Enrollment Year: " + s.getEnrollmentYear());
            System.out.println("Form of Education: " + s.getFormOfEducation());
            System.out.println("Student Status: " + s.getStudentStatus());
            System.out.println("--------------------------------------------------\n");
        }
    }
}
