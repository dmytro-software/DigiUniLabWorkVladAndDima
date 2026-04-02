package Project.ui;

import Project.Models.Student;
import Project.service.StudentService;
import Project.validation.StudentValidator;
import org.jline.reader.LineReader;

import javax.sound.sampled.Line;
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

                }catch (Exception e) {
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

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public void handleFindStudentsByGroup(LineReader reader) {
        try {
            int group = Integer.parseInt(reader.readLine("Enter group number: "));
            StudentValidator.valideGroup(group);

            List<Student> result = studentService.findAll().stream()
                    .filter(s -> s.getGroup() == group)
                    .toList();

            if (result.isEmpty()) {
                System.out.println("No students found with this group: " + group);
            } else {
                System.out.println("\n--- Search Results ---");
                result.forEach(System.out::println); 
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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