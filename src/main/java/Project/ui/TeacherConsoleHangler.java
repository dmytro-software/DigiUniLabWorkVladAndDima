package Project.ui;

import Project.Models.Teacher;
import Project.service.TeacherService;
import Project.validation.TeacherValidator;
import org.jline.reader.LineReader;

import java.time.LocalDate;
import java.util.List;

public class TeacherConsoleHangler {
    private TeacherService teacherService;

    public TeacherConsoleHangler(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    public void handleAddTeacher(LineReader reader) {
        try {
            int idPerson = (int) (Math.random() * 900000) + 100000;
            System.out.println("Generated Person ID: " + idPerson);

            String pib = reader.readLine("Full Name (PIB): ");
            TeacherValidator.validateName(pib);

            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            TeacherValidator.validateBirthDate(birthDate);
            LocalDate birthDatee = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

            String email = reader.readLine("Email: ");
            TeacherValidator.validateEmail(email);

            String phoneStr = reader.readLine("Phone (10 digits): ");
            TeacherValidator.validatePhone(phoneStr);
            int phoneNumber = Integer.parseInt(phoneStr);

            int teacherId = (int) (Math.random() * 900000) + 100000;
            System.out.println("Generated Teacher ID: " + teacherId);

            String position = reader.readLine("Position: ");
            TeacherValidator.validatePositionOrDegree(position);

            String academicDegree = reader.readLine("Academic Degree: ");
            TeacherValidator.validatePositionOrDegree(academicDegree);

            String academicRank = reader.readLine("Academic Rank: ");
            TeacherValidator.validatePositionOrDegree(academicRank);

            String hireDate = reader.readLine("Hire Date (YYYY.MM.DD): ");
            TeacherValidator.validateHireDate(hireDate);

            double fullTimeEquivalent = Double.parseDouble(reader.readLine("Full Time Equivalent: ").replace(',', '.'));
            TeacherValidator.validateFTE(fullTimeEquivalent);

            int departmentId = Integer.parseInt(reader.readLine("Department ID: "));
            TeacherValidator.validateId(departmentId);

            teacherService.addTeacher(
                    departmentId,
                    idPerson,
                    pib, birthDatee,
                    email,
                    phoneNumber,
                    teacherId,
                    position,
                    academicDegree,
                    academicRank,
                    hireDate,
                    fullTimeEquivalent
            );

            System.out.println("Teacher successfully added!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleEditTeacher(LineReader reader) {
        try {
            int teacherId = Integer.parseInt(reader.readLine("Enter teacher ID to edit: "));
            Teacher teacher = teacherService.findByTeacherId(teacherId);

            if (teacher == null) {
                System.out.println("Teacher not found.");
                return;
            }

            System.out.println("Editing teacher. Press Enter to keep current value.\n");

            // pib
            String pib = reader.readLine("Full Name (" + teacher.getPib() + "): ");
            if (!pib.isBlank()) {
                if (!TeacherValidator.validateName(pib)) {
                    System.out.println("Invalid name format.");
                    return;
                }
                teacher.setPib(pib);
            }

            // email
            String email = reader.readLine("Email (" + teacher.getEmail() + "): ");
            if (!email.isBlank()) {
                if (!TeacherValidator.validateEmail(email)) {
                    System.out.println("Invalid email format.");
                    return;
                }
                teacher.setEmail(email);
            }

            // phoneNumber
            String phoneStr = reader.readLine("Phone (" + teacher.getPhoneNumber() + "): ");
            if (!phoneStr.isBlank()) {
                if (!TeacherValidator.validatePhone(phoneStr)) {
                    System.out.println("Phone must be 10 digits.");
                    return;
                }
                teacher.setPhoneNumber(Integer.parseInt(phoneStr));
            }

            // position
            String position = reader.readLine("Position (" + teacher.getPosition() + "): ");
            if (!position.isBlank()) {
                if (!TeacherValidator.validatePositionOrDegree(position)) {
                    System.out.println("Invalid position format.");
                    return;
                }
                teacher.setPosition(position);
            }

            // academicDegree
            String degree = reader.readLine("Academic Degree (" + teacher.getAcademicDegree() + "): ");
            if (!degree.isBlank()) {
                if (!TeacherValidator.validatePositionOrDegree(degree)) {
                    System.out.println("Invalid academic degree format.");
                    return;
                }
                teacher.setAcademicDegree(degree);
            }

            // academicRank
            String rank = reader.readLine("Academic Rank (" + teacher.getAcademicRank() + "): ");
            if (!rank.isBlank()) {
                teacher.setAcademicRank(rank);
            }

            // hireDate
            String hireDate = reader.readLine("Hire Date (" + teacher.getHireDate() + ") YYYYMMDD: ");
            if (!hireDate.isBlank()) {
                if (!TeacherValidator.validateHireDate(hireDate)) {
                    System.out.println("Invalid hire date format.");
                    return;
                }
                teacher.setHireDate(hireDate);
            }

            // fullTimeEquivalent
            String fteStr = reader.readLine("Full Time Equivalent (" + teacher.getFullTimeEquivalent() + "): ");
            if (!fteStr.isBlank()) {
                double fte = Double.parseDouble(fteStr.replace(',', '.'));
                if (!TeacherValidator.validateFTE(fte)) {
                    System.out.println("FTE should be between 0.1 and 1.0.");
                    return;
                }
                teacher.setFullTimeEquivalent(fte);
            }

            System.out.println("Teacher updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
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

    public void handelFindTeacherByPib(LineReader reader){
        try {
            String teacherPib = reader.readLine("Enter teacher Pib: ");
            TeacherValidator.validateName(teacherPib);

            Teacher teacher = teacherService.findByPib(teacherPib);
            if (teacher == null) {
                System.out.println("Teacher with PIB " + teacherPib + " not found");
            }
            System.out.println("Teacher Found");
            System.out.println(teacher);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleFindTeacherById(LineReader reader){
        try{
            int teacherID = Integer.parseInt(reader.readLine("Enter TeacherId: "));
            TeacherValidator.validateId(teacherID);

            Teacher teacher = teacherService.findByTeacherId(teacherID);

            if(teacher == null){
                System.out.println("Teacher with PIB " + teacherID + " not found");
            }

            System.out.println("Teacher Found");
            System.out.println(teacher);
        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage() );
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
