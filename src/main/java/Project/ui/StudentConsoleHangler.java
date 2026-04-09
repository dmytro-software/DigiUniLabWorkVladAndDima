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

import java.time.LocalDate;
import java.util.List;


public class StudentConsoleHangler {
    private StudentService studentService;

    public StudentConsoleHangler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handleAddStudent(LineReader reader) {
        try {
            int departmentId = Integer.parseInt(reader.readLine("Department ID: "));
            StudentValidator.validateId(departmentId);

            int idPerson = (int) (Math.random() * 900000) + 100000;
            System.out.println("Generated Person ID: " + idPerson);

            String pib = reader.readLine("Full Name (PIB): ");
            StudentValidator.validatePib(pib);

            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            StudentValidator.validateBirthDate(birthDate);
            LocalDate birthDatee = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

            String email = reader.readLine("Email: ");
            StudentValidator.validateEmail(email);

            String phoneStr = reader.readLine("Phone (10 digits): ");
            StudentValidator.validatePhoneNumber(phoneStr);
            int phoneNumber = Integer.parseInt(phoneStr);

            int gradeBookId = (int) (Math.random() * 900000) + 100000;
            System.out.println("Generated gradeBook ID: " + gradeBookId);

            int course = Integer.parseInt(reader.readLine("Course (1-4): "));
            StudentValidator.valideCourse(course);

            int group = Integer.parseInt(reader.readLine("Group: "));
            StudentValidator.valideGroup(group);

            int enrollmentYear = Integer.parseInt(reader.readLine("Enrollment year: "));
            StudentValidator.validateEnrollmentYear(enrollmentYear);

            String formOfEducation = reader.readLine("Form of Education: ");
            StudentValidator.valideFormOfEducation(formOfEducation);

            String studentStatus = reader.readLine("Student status: ");
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

            System.out.println("Student successfully added!");

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Numeric format error. Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleEditStudent(LineReader reader) {
        try {
                int gradeBooktId = Integer.parseInt(reader.readLine("Enter GradeBook ID of student to edit: "));
                StudentValidator.validateId(gradeBooktId);
                Student student = studentService.findStudentByGradeBook(gradeBooktId)
                    .orElseThrow(() -> new EntityNotFoundException("Student with GradeBook ID " + gradeBooktId + " not found."));


                System.out.println("Editing student. Press Enter to keep current value.");

                // PIB
                String pib = reader.readLine("Full Name (" + student.getPib() + "): ");
                if (!pib.isBlank()) {
                    StudentValidator.validatePib(pib);
                    student.setPib(pib);
                }

                // Email
                String email = reader.readLine("Email (" + student.getEmail() + "): ");
                if (!email.isBlank()) {
                    StudentValidator.validateEmail(email);
                    student.setEmail(email);
                }

                // Phone
                String phoneStr = reader.readLine("Phone (" + student.getPhoneNumber() + "): ");
                int phoneNumber;
                if (phoneStr.isBlank()) {
                    phoneNumber = student.getPhoneNumber();
                } else {
                    StudentValidator.validatePhoneNumber(phoneStr);
                    phoneNumber = Integer.parseInt(phoneStr);
                    student.setPhoneNumber(phoneNumber);
                }

                // Course
                String courseInput = reader.readLine("Course (" + student.getCourse() + "): ");
                int finalCourse;
                if (courseInput.isBlank()) {
                    finalCourse = student.getCourse();
                } else {
                    finalCourse = Integer.parseInt(courseInput);
                    StudentValidator.valideCourse(finalCourse);
                    student.setCourse(finalCourse);
                }

                // Group
                String groupInput= reader.readLine("Group (" + student.getGroup() + "): ");
                int group;
                if (!groupInput.isBlank()) {
                    group = student.getGroup();
                } else {
                    group = Integer.parseInt(groupInput);
                    StudentValidator.valideGroup(group);
                    student.setGroup(group);
                }

                // Form of Education
                String formOfEducation = reader.readLine("Form of Education (" + student.getFormOfEducation() + "): ");
                if (!formOfEducation.isBlank()) {
                    StudentValidator.valideFormOfEducation(formOfEducation);
                    student.setFormOfEducation(formOfEducation);
                }

                // Status
                String studentStatus = reader.readLine("Status (" + student.getStudentStatus() + "): ");
                if (!studentStatus.isBlank()) {
                    StudentValidator.valideStudentStatus(studentStatus);
                    student.setStudentStatus(studentStatus);
                }

                System.out.println("Student updated successfully!");


                studentService.editStudent(gradeBooktId,
                                           pib,
                                           email,
                                           phoneNumber,
                                           finalCourse,
                                           group,
                                           formOfEducation,
                                           studentStatus

                );

                System.out.println("Student successfully updated!");

                }catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Numeric format error");
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

        } catch (EntityNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
            System.out.println(s);
        }
    }

    public void handleFindStudentByPib(LineReader reader){
        try {
            String studentPib = reader.readLine("Enter a Student Pib: ");
            StudentValidator.validatePib(studentPib);
            List<Student> result = studentService.findByPib(studentPib);

            if (result.isEmpty()) {
                System.out.println("No students found with PIB: " + studentPib);
                return;
            }
            System.out.println("\n--- Search Results ---");
            for( Student s : result){
                System.out.println(s);
            }


        }
        catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void handleFindStudentByCourse(LineReader reader){
        try{
            int course = Integer.parseInt(reader.readLine("Enter course number: "));
            StudentValidator.valideCourse(course);
            List<Student> result = studentService.findByCourse(course);

            if (result.isEmpty()) {
                System.out.println("No students found with this course: " + course);
                return;
            }
            System.out.println("\n--- Search Results ---");
            for( Student s : result){
                System.out.println(s);
            }

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void handleFindStudentsByGroup(LineReader reader){
        try {
            int group = Integer.parseInt(reader.readLine("Enter group number: "));
            StudentValidator.valideGroup(group);

            List<Student> result = studentService.findByGroup(group);

            if (result.isEmpty()) {
                System.out.println("No students found with this group: " + group);
                return;
            }
            System.out.println("\n--- Search Results ---");
            for (Student s : result) {
                System.out.println(s);
            }
        }catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleShowStudentsReportByCourse(LineReader reader){
        try{
            List<Student> allStudents = studentService.findAll();
            StudentValidator.validateList(allStudents);
            System.out.println("\n--- Report Search By Course ---");
            StudentsReport.getStudentsGroupedByCourse(allStudents);
        } catch (EntityNotEmptyException e) {
            throw new EntityNotFoundException("Not found Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("ERROR: " + e.getMessage());
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