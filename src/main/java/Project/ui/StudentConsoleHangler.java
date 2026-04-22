package Project.ui;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.ValidationException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Reports.FacultyReport;
import Project.Reports.StudentsReport;
import Project.service.DepartmentService;
import Project.service.FacultyService;
import Project.service.Impl.DepartmentServiceImpl;
import Project.service.StudentService;
import Project.validation.DepartmentValidator;
import Project.validation.StudentValidator;
import org.jline.reader.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static Project.Models.ConsoleColors.*;


public class StudentConsoleHangler {


    private static final Logger log = LoggerFactory.getLogger(StudentConsoleHangler.class);
    private StudentService studentService;

    public StudentConsoleHangler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handleAddStudent(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== ADD NEW STUDENT ==" + RESET);
        try {
            int departmentId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Department ID: "));
            StudentValidator.validateId(departmentId);

            int idPerson = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Generated Person ID: " + idPerson);

            String pib = reader.readLine(YELLOW + " ❯ " + RESET + "Full Name (PIB): ");
            StudentValidator.validatePib(pib);

            String birthDate = reader.readLine(YELLOW + " ❯ " + RESET + "Birth Date (YYYYMMDD): ");
            StudentValidator.validateBirthDate(birthDate);
            LocalDate birthDatee = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

            String email = reader.readLine(YELLOW + " ❯ " + RESET + "Email: ");
            StudentValidator.validateEmail(email);

            String phoneStr = reader.readLine(YELLOW + " ❯ " + RESET + "Phone (10 digits): ");
            StudentValidator.validatePhoneNumber(phoneStr);
            int phoneNumber = Integer.parseInt(phoneStr);

            int gradeBookId = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Generated gradeBook ID: " + gradeBookId);

            int course = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Course (1-4): "));
            StudentValidator.valideCourse(course);

            int group = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Group: "));
            StudentValidator.valideGroup(group);

            int enrollmentYear = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enrollment year: "));
            StudentValidator.validateEnrollmentYear(enrollmentYear);

            String formOfEducation = reader.readLine(YELLOW + " ❯ " + RESET + "Form of Education: ");
            StudentValidator.valideFormOfEducation(formOfEducation);

            String studentStatus = reader.readLine(YELLOW + " ❯ " + RESET + "Student status: ");
            StudentValidator.valideStudentStatus(studentStatus);

            studentService.addStudent(
                    departmentId,
                    idPerson,
                    pib,
                    birthDatee,
                    email,
                    phoneNumber,
                    gradeBookId,
                    course,
                    group,
                    enrollmentYear,
                    formOfEducation,
                    studentStatus
            );

            System.out.println(GREEN + " ✓ Student successfully added!" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Student added successfully. GradeBookID: {}, PIB: '{}', DeptID: {}", gradeBookId, pib, departmentId);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during student creation: {}", e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("Entity not found during student creation: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error. Please enter valid numbers." + RESET);
            log.warn("Invalid number format entered during student creation");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while adding a student", e);
        }
    }

    public void handleEditStudent(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== EDIT STUDENT ==" + RESET);
        try {
            int gradeBookId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter GradeBook ID of student to edit: "));
            StudentValidator.validateId(gradeBookId);

            Student student = studentService.findStudentByGradeBook(gradeBookId)
                    .orElseThrow(() -> new EntityNotFoundException("Student with GradeBook ID " + gradeBookId + " not found."));

            System.out.println(CYAN + " ℹ " + RESET + "Editing student. Press Enter to keep current value.");

            String pibInput = reader.readLine(YELLOW + " ❯ " + RESET + "Full Name (" + student.getPib() + "): ");
            String pib = student.getPib();
            if (!pibInput.isBlank()) {
                if (!StudentValidator.validatePib(pibInput)) {
                    System.out.println(RED + " ✗ Invalid name format." + RESET);
                    log.warn("Invalid name format entered during student edit: '{}'", pibInput);
                    return;
                }
                pib = pibInput;
            }

            String emailInput = reader.readLine(YELLOW + " ❯ " + RESET + "Email (" + student.getEmail() + "): ");
            String email = student.getEmail();
            if (!emailInput.isBlank()) {
                if (!StudentValidator.validateEmail(emailInput)) {
                    System.out.println(RED + " ✗ Invalid email format." + RESET);
                    log.warn("Invalid email format entered during student edit: '{}'", emailInput);
                    return;
                }
                email = emailInput;
            }

            String phoneStr = reader.readLine(YELLOW + " ❯ " + RESET + "Phone (" + student.getPhoneNumber() + "): ");
            int phoneNumber = student.getPhoneNumber();
            if (!phoneStr.isBlank()) {
                if (!StudentValidator.validatePhoneNumber(phoneStr)) {
                    System.out.println(RED + " ✗ Phone must be exactly 10 digits." + RESET);
                    log.warn("Invalid phone format entered during student edit: '{}'", phoneStr);
                    return;
                }
                phoneNumber = Integer.parseInt(phoneStr);
            }

            String courseInput = reader.readLine(YELLOW + " ❯ " + RESET + "Course (" + student.getCourse() + "): ");
            int finalCourse = student.getCourse();
            if (!courseInput.isBlank()) {
                finalCourse = Integer.parseInt(courseInput);
                if (!StudentValidator.valideCourse(finalCourse)) {
                    System.out.println(RED + " ✗ Invalid course: Must be between 1 and 5." + RESET);
                    log.warn("Invalid course entered during student edit: {}", finalCourse);
                    return;
                }
            }

            String groupInput = reader.readLine(YELLOW + " ❯ " + RESET + "Group (" + student.getGroup() + "): ");
            int group = student.getGroup();
            if (!groupInput.isBlank()) {
                group = Integer.parseInt(groupInput);
                if (!StudentValidator.valideGroup(group)) {
                    System.out.println(RED + " ✗ Invalid group: Group must be between 1 and 4." + RESET);
                    log.warn("Invalid group entered during student edit: {}", group);
                    return;
                }
            }

            String formOfEducationInput = reader.readLine(YELLOW + " ❯ " + RESET + "Form of Education (" + student.getFormOfEducation() + "): ");
            String formOfEducation = student.getFormOfEducation();
            if (!formOfEducationInput.isBlank()) {
                if (!StudentValidator.valideFormOfEducation(formOfEducationInput)) {
                    System.out.println(RED + " ✗ Invalid form of education format." + RESET);
                    log.warn("Invalid form of education entered during student edit: '{}'", formOfEducationInput);
                    return;
                }
                formOfEducation = formOfEducationInput;
            }

            String studentStatusInput = reader.readLine(YELLOW + " ❯ " + RESET + "Status (" + student.getStudentStatus() + "): ");
            String studentStatus = student.getStudentStatus();
            if (!studentStatusInput.isBlank()) {
                if (!StudentValidator.valideStudentStatus(studentStatusInput)) {
                    System.out.println(RED + " ✗ Invalid student status format." + RESET);
                    log.warn("Invalid student status entered during student edit: '{}'", studentStatusInput);
                    return;
                }
                studentStatus = studentStatusInput;
            }

            studentService.editStudent(
                    gradeBookId,
                    pib,
                    email,
                    phoneNumber,
                    finalCourse,
                    group,
                    formOfEducation,
                    studentStatus
            );

            System.out.println(GREEN + " ✓ Student successfully updated!" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Student with GradeBookID: {} was updated successfully", gradeBookId);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during student edit: {}", e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during student edit: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error" + RESET);
            log.warn("Invalid number format entered during student edit");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while editing a student", e);
        }
    }

    public void handleRemoveStudent(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== REMOVE STUDENT ==" + RESET);
        try {
            int gradeBookId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter GradeBook ID to remove: "));

            boolean removed = studentService.removeStudent(gradeBookId);

            if (removed) {
                System.out.println(GREEN + " ✓ Student removed successfully." + RESET);
                log.info("Student with GradeBookID: {} was removed successfully", gradeBookId);
            } else {
                System.out.println(RED + " ✗ Student not found." + RESET);
                log.info("Attempted to remove non-existent student. GradeBookID: {}", gradeBookId);
            }
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during student removal: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Invalid input. Please enter a number." + RESET);
            log.warn("Invalid number format entered for student removal");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while removing a student", e);
        }
    }

    public void handleShowAllStudents() throws IOException {
        log.info("User requested to view all students");
        List<Student> students = studentService.findAll();

        if (students.isEmpty()) {
            System.out.println(YELLOW + "\n ⚠ " + RESET + "No students found.\n");
            return;
        }

        System.out.println(CYAN_BOLD + "\n========== STUDENTS LIST ==========" + RESET);

        for (Student s : students) {
            System.out.println("\n================================");
            System.out.println("ID Person: " + s.getIdPerson());
            System.out.println("PIB: " + s.getPib());
            System.out.println("Birth Date: " + s.getBirthDate());
            System.out.println("Email: " + s.getEmail());
            System.out.println("Phone: " + s.getPhoneNumber());
            System.out.println("GradeBook ID: " + s.getGradeBookId());
            System.out.println("Course: " + s.getCourse());
            System.out.println("Group: " + s.getGroup());
            System.out.println("Enrollment Year: " + s.getEnrollmentYear());
            System.out.println("Form of Education: " + s.getFormOfEducation());
            System.out.println("Status: " + s.getStudentStatus());
            System.out.println("Department ID: " + s.getDepartmentId());
            System.out.println("================================");
        }

        System.out.println(CYAN_BOLD + "\n===================================\n" + RESET);
    }

    public void handleFindStudentByPib(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND STUDENT BY PIB ==" + RESET);
        try {
            String studentPib = reader.readLine(YELLOW + " ❯ " + RESET + "Enter a Student Pib: ");
            StudentValidator.validatePib(studentPib);
            List<Student> result = studentService.findByPib(studentPib);

            if (result.isEmpty()) {
                System.out.println(YELLOW + " ⚠ " + RESET + "No students found with PIB: " + studentPib);
                log.info("Student search by PIB yielded no results: '{}'", studentPib);
                return;
            }

            log.info("Successfully found {} student(s) by PIB: '{}'", result.size(), studentPib);

            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for( Student s : result){
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during student search by PIB: {}", e.getMessage());
        } catch (Exception e){
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error during student search by PIB", e);
        }
    }

    public void handleFindStudentByCourse(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND STUDENTS BY COURSE ==" + RESET);
        try{
            int course = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter course number: "));
            StudentValidator.valideCourse(course);
            List<Student> result = studentService.findByCourse(course);

            if (result.isEmpty()) {
                System.out.println(YELLOW + " ⚠ " + RESET + "No students found with this course: " + course);
                log.info("Student search by course yielded no results: {}", course);
                return;
            }

            log.info("Successfully found {} student(s) by course: {}", result.size(), course);

            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for( Student s : result){
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during student search by course: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error during student search by course", e);
        }
    }

    public void handleFindStudentsByGroup(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND STUDENTS BY GROUP ==" + RESET);
        try {
            int group = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter group number: "));
            StudentValidator.valideGroup(group);

            List<Student> result = studentService.findByGroup(group);

            if (result.isEmpty()) {
                System.out.println(YELLOW + " ⚠ " + RESET + "No students found with this group: " + group);
                log.info("Student search by group yielded no results: {}", group);
                return;
            }

            log.info("Successfully found {} student(s) by group: {}", result.size(), group);

            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for (Student s : result) {
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during student search by group: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error during student search by group", e);
        }
    }

    public void handleShowStudentsReportByCourse(LineReader reader){
        try{
            List<Student> allStudents = studentService.findAll();
            StudentValidator.validateList(allStudents);
            System.out.println(CYAN_BOLD + "\n--- Report Search By Course ---" + RESET);
            StudentsReport.getStudentsGroupedByCourse(allStudents);

            log.info("Generated 'Students Grouped By Course' report");

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Not found Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during student report generation: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println(RED + " ✗ ERROR: " + e.getMessage() + RESET);
            log.error("Unexpected error generating student report by course", e);
        }
    }



}
