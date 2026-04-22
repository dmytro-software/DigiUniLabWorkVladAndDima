package Project.ui;

import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.ValidationException;
import Project.Models.Teacher;
import Project.service.TeacherService;
import Project.validation.TeacherValidator;
import org.jline.reader.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static Project.Models.ConsoleColors.*;

public class TeacherConsoleHangler {


    private static final Logger log = LoggerFactory.getLogger(TeacherConsoleHangler.class);
    private TeacherService teacherService;

    public TeacherConsoleHangler(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public void handleAddTeacher(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== ADD NEW TEACHER ==" + RESET);
        try {
            int idPerson = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Generated Person ID: " + idPerson);

            String pib = reader.readLine(YELLOW + " ❯ " + RESET + "Full Name (PIB): ");
            TeacherValidator.validateName(pib);

            String birthDate = reader.readLine(YELLOW + " ❯ " + RESET + "Birth Date (YYYYMMDD): ");
            TeacherValidator.validateBirthDate(birthDate);
            LocalDate birthDatee = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

            String email = reader.readLine(YELLOW + " ❯ " + RESET + "Email: ");
            TeacherValidator.validateEmail(email);

            String phoneStr = reader.readLine(YELLOW + " ❯ " + RESET + "Phone (10 digits): ");
            TeacherValidator.validatePhone(phoneStr);
            int phoneNumber = Integer.parseInt(phoneStr);

            int teacherId = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Generated Teacher ID: " + teacherId);

            String position = reader.readLine(YELLOW + " ❯ " + RESET + "Position: ");
            TeacherValidator.validatePositionOrDegree(position);

            String academicDegree = reader.readLine(YELLOW + " ❯ " + RESET + "Academic Degree: ");
            TeacherValidator.validatePositionOrDegree(academicDegree);

            String academicRank = reader.readLine(YELLOW + " ❯ " + RESET + "Academic Rank: ");
            TeacherValidator.validatePositionOrDegree(academicRank);

            String hireDate = reader.readLine(YELLOW + " ❯ " + RESET + "Hire Date (YYYY.MM.DD): ");
            TeacherValidator.validateHireDate(hireDate);

            double fullTimeEquivalent = Double.parseDouble(reader.readLine(YELLOW + " ❯ " + RESET + "Full Time Equivalent: ").replace(',', '.'));
            TeacherValidator.validateFTE(fullTimeEquivalent);

            int departmentId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Department ID: "));
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

            System.out.println(GREEN + " ✓ Teacher successfully added!" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Teacher added successfully. TeacherID: {}, PIB: '{}', DeptID: {}", teacherId, pib, departmentId);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during teacher creation: {}", e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("Entity not found during teacher creation: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error" + RESET);
            log.warn("Invalid number format entered during teacher creation");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while adding a teacher", e);
        }
    }

    public void handleEditTeacher(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== EDIT TEACHER ==" + RESET);
        try {
            int teacherId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter teacher ID to edit: "));
            Teacher teacher = teacherService.findByTeacherId(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher with Teacher ID " + teacherId + " not found."));

            System.out.println(CYAN + " ℹ " + RESET + "Editing teacher. Press Enter to keep current value.");

            // pib
            String pib = reader.readLine(YELLOW + " ❯ " + RESET + "Full Name (" + teacher.getPib() + "): ");
            if (!pib.isBlank()) {
                if (!TeacherValidator.validateName(pib)) {
                    System.out.println(RED + " ✗ Invalid name format." + RESET);
                    log.warn("Invalid name format entered during teacher edit: '{}'", pib);
                    return;
                }
                teacher.setPib(pib);
            }

            // email
            String email = reader.readLine(YELLOW + " ❯ " + RESET + "Email (" + teacher.getEmail() + "): ");
            if (!email.isBlank()) {
                if (!TeacherValidator.validateEmail(email)) {
                    System.out.println(RED + " ✗ Invalid email format." + RESET);
                    log.warn("Invalid email format entered during teacher edit: '{}'", email);
                    return;
                }
                teacher.setEmail(email);
            }

            // phoneNumber
            String phoneStr = reader.readLine(YELLOW + " ❯ " + RESET + "Phone (" + teacher.getPhoneNumber() + "): ");
            if (!phoneStr.isBlank()) {
                if (!TeacherValidator.validatePhone(phoneStr)) {
                    System.out.println(RED + " ✗ Phone must be 10 digits." + RESET);
                    log.warn("Invalid phone format entered during teacher edit: '{}'", phoneStr);
                    return;
                }
                teacher.setPhoneNumber(Integer.parseInt(phoneStr));
            }

            // position
            String position = reader.readLine(YELLOW + " ❯ " + RESET + "Position (" + teacher.getPosition() + "): ");
            if (!position.isBlank()) {
                if (!TeacherValidator.validatePositionOrDegree(position)) {
                    System.out.println(RED + " ✗ Invalid position format." + RESET);
                    log.warn("Invalid position format entered during teacher edit: '{}'", position);
                    return;
                }
                teacher.setPosition(position);
            }

            // academicDegree
            String degree = reader.readLine(YELLOW + " ❯ " + RESET + "Academic Degree (" + teacher.getAcademicDegree() + "): ");
            if (!degree.isBlank()) {
                if (!TeacherValidator.validatePositionOrDegree(degree)) {
                    System.out.println(RED + " ✗ Invalid academic degree format." + RESET);
                    log.warn("Invalid academic degree format entered during teacher edit: '{}'", degree);
                    return;
                }
                teacher.setAcademicDegree(degree);
            }

            // academicRank
            String rank = reader.readLine(YELLOW + " ❯ " + RESET + "Academic Rank (" + teacher.getAcademicRank() + "): ");
            if (!rank.isBlank()) {
                teacher.setAcademicRank(rank);
            }

            // hireDate
            String hireDate = reader.readLine(YELLOW + " ❯ " + RESET + "Hire Date (" + teacher.getHireDate() + ") YYYYMMDD: ");
            if (!hireDate.isBlank()) {
                if (!TeacherValidator.validateHireDate(hireDate)) {
                    System.out.println(RED + " ✗ Invalid hire date format." + RESET);
                    log.warn("Invalid hire date format entered during teacher edit: '{}'", hireDate);
                    return;
                }
                teacher.setHireDate(hireDate);
            }

            // fullTimeEquivalent
            String fteStr = reader.readLine(YELLOW + " ❯ " + RESET + "Full Time Equivalent (" + teacher.getFullTimeEquivalent() + "): ");
            if (!fteStr.isBlank()) {
                double fte = Double.parseDouble(fteStr.replace(',', '.'));
                if (!TeacherValidator.validateFTE(fte)) {
                    System.out.println(RED + " ✗ FTE should be between 0.1 and 1.0." + RESET);
                    log.warn("Invalid FTE entered during teacher edit: {}", fte);
                    return;
                }
                teacher.setFullTimeEquivalent(fte);
            }

            System.out.println(GREEN + " ✓ Teacher updated successfully!" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Teacher with TeacherID: {} was updated successfully", teacherId);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during teacher edit: {}", e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during teacher edit: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Invalid number format: " + e.getMessage() + RESET);
            log.warn("Invalid number format entered during teacher edit");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while editing a teacher", e);
        }
    }

    public void handleRemoveTeacher(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== REMOVE TEACHER ==" + RESET);
        try {
            int teacherId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter teacher ID to remove: "));

            boolean removed = teacherService.removeTeacher(teacherId);

            if (removed) {
                System.out.println(GREEN + " ✓ Teacher removed successfully." + RESET);
                log.info("Teacher with TeacherID: {} was removed successfully", teacherId);
            } else {
                System.out.println(RED + " ✗ Teacher not found." + RESET);
                log.info("Attempted to remove non-existent teacher. TeacherID: {}", teacherId);
            }
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during teacher removal: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Invalid input. Please enter a number." + RESET);
            log.warn("Invalid number format entered for teacher removal");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while removing a teacher", e);
        }
    }

    public void handelFindTeacherByPib(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND TEACHER BY PIB ==" + RESET);
        try {
            String teacherPib = reader.readLine(YELLOW + " ❯ " + RESET + "Enter teacher Pib: ");
            TeacherValidator.validateName(teacherPib);

            Teacher teacher = teacherService.findByPib(teacherPib).orElseThrow(() -> new EntityNotFoundException("Teacher with Pib " + teacherPib + " not found."));

            System.out.println(GREEN + " ✓ Teacher Found" + RESET);
            System.out.println(teacher);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Successfully found teacher by PIB: '{}'", teacherPib);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during teacher search by PIB: {}", e.getMessage());
        } catch (Exception e){
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error during teacher search by PIB", e);
        }
    }

    public void handleFindTeacherById(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== FIND TEACHER BY ID ==" + RESET);
        try{
            int teacherID = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter TeacherId: "));
            TeacherValidator.validateId(teacherID);

            Teacher teacher = teacherService.findByTeacherId(teacherID).orElseThrow(() -> new EntityNotFoundException("Teacher with Teacher ID " + teacherID + " not found."));

            System.out.println(GREEN + " ✓ Teacher Found" + RESET);
            System.out.println(teacher);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Successfully found teacher by ID: {}", teacherID);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during teacher search by ID: {}", e.getMessage());
        } catch (Exception e){
            System.out.println(RED + " ✗ Error: "+ e.getMessage() + RESET);
            log.error("Unexpected error during teacher search by ID", e);
        }
    }

    public void handleShowAllTeachers() throws IOException {
        log.info("requested to view all teachers");

        List<Teacher> teachers = teacherService.findAll();

        if (teachers.isEmpty()) {
            System.out.println(YELLOW + "\n ⚠ " + RESET + "No teachers found.\n");
            return;
        }

        System.out.println(CYAN_BOLD + "\n========== TEACHERS LIST ==========" + RESET);

        for (Teacher t : teachers) {
            System.out.println(CYAN + "--------------------------------------------------" + RESET);
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
        System.out.println(CYAN_BOLD + "===================================\n" + RESET);
    }

}
