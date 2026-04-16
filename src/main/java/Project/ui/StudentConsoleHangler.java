package Project.ui;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.ValidationException;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Reports.FacultyReport;
import Project.Reports.StudentsReport;
import Project.service.FacultyService;
import Project.service.StudentService;
import Project.validation.StudentValidator;
import org.jline.reader.LineReader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static Project.Models.ConsoleColors.*;


public class StudentConsoleHangler {
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

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error. Please enter valid numbers." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleEditStudent(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== EDIT STUDENT ==" + RESET);
        try {
            int gradeBooktId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter GradeBook ID of student to edit: "));
            StudentValidator.validateId(gradeBooktId);
            Student student = studentService.findStudentByGradeBook(gradeBooktId)
                    .orElseThrow(() -> new EntityNotFoundException("Student with GradeBook ID " + gradeBooktId + " not found."));

            System.out.println(CYAN + " ℹ " + RESET + "Editing student. Press Enter to keep current value.");

            // PIB
            String pib = reader.readLine(YELLOW + " ❯ " + RESET + "Full Name (" + student.getPib() + "): ");
            if (!pib.isBlank()) {
                StudentValidator.validatePib(pib);
                student.setPib(pib);
            }

            // Email
            String email = reader.readLine(YELLOW + " ❯ " + RESET + "Email (" + student.getEmail() + "): ");
            if (!email.isBlank()) {
                StudentValidator.validateEmail(email);
                student.setEmail(email);
            }

            // Phone
            String phoneStr = reader.readLine(YELLOW + " ❯ " + RESET + "Phone (" + student.getPhoneNumber() + "): ");
            int phoneNumber;
            if (phoneStr.isBlank()) {
                phoneNumber = student.getPhoneNumber();
            } else {
                StudentValidator.validatePhoneNumber(phoneStr);
                phoneNumber = Integer.parseInt(phoneStr);
                student.setPhoneNumber(phoneNumber);
            }

            // Course
            String courseInput = reader.readLine(YELLOW + " ❯ " + RESET + "Course (" + student.getCourse() + "): ");
            int finalCourse;
            if (courseInput.isBlank()) {
                finalCourse = student.getCourse();
            } else {
                finalCourse = Integer.parseInt(courseInput);
                StudentValidator.valideCourse(finalCourse);
                student.setCourse(finalCourse);
            }

            // Group
            String groupInput= reader.readLine(YELLOW + " ❯ " + RESET + "Group (" + student.getGroup() + "): ");
            int group;
            if (groupInput.isBlank()) {
                group = student.getGroup();
            } else {
                group = Integer.parseInt(groupInput);
                StudentValidator.valideGroup(group);
                student.setGroup(group);
            }

            // Form of Education
            String formOfEducation = reader.readLine(YELLOW + " ❯ " + RESET + "Form of Education (" + student.getFormOfEducation() + "): ");
            if (!formOfEducation.isBlank()) {
                StudentValidator.valideFormOfEducation(formOfEducation);
                student.setFormOfEducation(formOfEducation);
            }

            // Status
            String studentStatus = reader.readLine(YELLOW + " ❯ " + RESET + "Status (" + student.getStudentStatus() + "): ");
            if (!studentStatus.isBlank()) {
                StudentValidator.valideStudentStatus(studentStatus);
                student.setStudentStatus(studentStatus);
            }

            // У твоєму оригінальному коді було два System.out.println, залишив обидва для збереження логіки
            System.out.println(GREEN + " ✓ Student updated successfully!" + RESET);

            studentService.editStudent(gradeBooktId,
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

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error" + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleRemoveStudent(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== REMOVE STUDENT ==" + RESET);
        try {
            int gradeBookId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter GradeBook ID to remove: "));

            boolean removed = studentService.removeStudent(gradeBookId);

            if (removed) {
                System.out.println(GREEN + " ✓ Student removed successfully." + RESET);
            } else {
                System.out.println(RED + " ✗ Student not found." + RESET);
            }
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Invalid input. Please enter a number." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleShowAllStudents() throws IOException {

        List<Student> students = studentService.findAll();

        if (students.isEmpty()) {
            System.out.println(YELLOW + "\n ⚠ " + RESET + "No students found.\n");
            return;
        }

        System.out.println(CYAN_BOLD + "\n========== STUDENTS LIST ==========" + RESET);

        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println(CYAN_BOLD + "===================================\n" + RESET);
    }

    public void handleFindStudentByPib(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND STUDENT BY PIB ==" + RESET);
        try {
            String studentPib = reader.readLine(YELLOW + " ❯ " + RESET + "Enter a Student Pib: ");
            StudentValidator.validatePib(studentPib);
            List<Student> result = studentService.findByPib(studentPib);

            if (result.isEmpty()) {
                System.out.println(YELLOW + " ⚠ " + RESET + "No students found with PIB: " + studentPib);
                return;
            }
            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for( Student s : result){
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e){
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
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
                return;
            }
            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for( Student s : result){
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
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
                return;
            }
            System.out.println(CYAN_BOLD + "\n--- Search Results ---" + RESET);
            for (Student s : result) {
                System.out.println(s);
            }
            System.out.println(CYAN + "----------------------\n" + RESET);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleShowStudentsReportByCourse(LineReader reader){
        try{
            List<Student> allStudents = studentService.findAll();
            StudentValidator.validateList(allStudents);
            System.out.println(CYAN_BOLD + "\n--- Report Search By Course ---" + RESET);
            StudentsReport.getStudentsGroupedByCourse(allStudents);
        } catch (EntityNotEmptyException e) {
            throw new EntityNotFoundException(RED + " ✗ Not found Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            throw new RuntimeException(RED + " ✗ ERROR: " + e.getMessage() + RESET);
        }
    }

}


// Department
//String departmentInput = reader.readLine("Department ID (" + student.getDepartmentId() + "): ");
//int departmentId;
//
//if (departmentInput.isBlank()) {
//departmentId = student.getDepartmentId();
//} else {
//departmentId = Integer.parseInt(departmentInput);
//
//    if (studentService.getDepartmentService().findById(departmentId) == null) {
//        System.out.println("Department with this ID does not exist!");
//        return;
//                }
//
//                student.setDepartmentId(departmentId);
//}