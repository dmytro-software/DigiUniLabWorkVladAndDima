package Project.ui;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.ValidationException;
import Project.Models.Faculty;
import Project.Reports.FacultyReport;
import Project.service.FacultyService;

import Project.validation.FacultyValidator;
import org.jline.reader.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static Project.Models.ConsoleColors.*;

public class FacultyConsoleHandler {


    private static final Logger log = LoggerFactory.getLogger(FacultyConsoleHandler.class);
    private final FacultyService facultyService;

    public FacultyConsoleHandler(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    public void handleAddFaculty(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== ADD NEW FACULTY ==" + RESET);
        try {
            int id = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Faculty id: " + id);

            String name = reader.readLine(YELLOW + " ❯ " + RESET + "Faculty Name: ");
            FacultyValidator.validateFacultyName(name);

            String shortName = reader.readLine(YELLOW + " ❯ " + RESET + "Short Name: ");
            FacultyValidator.validateFacultyShortName(shortName);

            String head = reader.readLine(YELLOW + " ❯ " + RESET + "Head: ");
            FacultyValidator.validateHeadOfFaculty(head);

            String contacts = reader.readLine(YELLOW + " ❯ " + RESET + "Contacts: ");
            FacultyValidator.validatePhoneNumber(contacts);

            Faculty newFaculty = new Faculty(id, name, shortName, head, contacts);
            facultyService.addFaculty(newFaculty);

            System.out.println(GREEN + " ✓ Faculty created." + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Faculty created successfully. ID: {}, Name: '{}'", id, name);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during faculty creation: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error" + RESET);
            log.warn("Invalid number format entered during faculty creation");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while adding a faculty", e);
        }
    }

    public void handleRemoveFaculty(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== REMOVE FACULTY ==" + RESET);
        try {
            String idInput = reader.readLine(YELLOW + " ❯ " + RESET + "Enter Faculty Id for remove: ");
            int id = Integer.parseInt(idInput.trim());

            facultyService.removeFaculty(id);

            System.out.println(GREEN + " ✓ Faculty with id " + id + " was successfully deleted" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Faculty with ID: {} was removed successfully", id);

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during faculty removal: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Number format error" + RESET);
            log.warn("Invalid ID format entered for faculty removal");
        } catch (EntityNotEmptyException e) {
            System.out.println(RED + " ✗ Delete error: " + e.getMessage() + RESET);
            log.warn("Attempted to delete non-empty faculty: {}", e.getMessage());
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while removing a faculty", e);
        }
    }

    public void handleEditFaculty(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== EDIT FACULTY ==" + RESET);
        try {
            int id = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter faculty id to edit: "));
            FacultyValidator.validateId(id);
            Faculty faculty = facultyService.findById(id);

            System.out.println(CYAN + " ℹ " + RESET + "Editing faculty. Press Enter to keep current value.");

            // 2. Faculty Name
            String nameInput = reader.readLine(YELLOW + " ❯ " + RESET + "Faculty Name (" + faculty.getFacultyName() + "): ");
            String finalName = faculty.getFacultyName();
            if (!nameInput.isBlank()) {
                if (!FacultyValidator.validateFacultyName(nameInput)) {
                    System.out.println(RED + " ✗ Invalid faculty name format." + RESET);
                    log.warn("Invalid faculty name format entered during edit: '{}'", nameInput);
                    return;
                }
                finalName = nameInput;
            }

            // 3. Short Name
            String shortNameInput = reader.readLine(YELLOW + " ❯ " + RESET + "Short Name (" + faculty.getFacultyShortName() + "): ");
            String finalShortName = faculty.getFacultyShortName();
            if (!shortNameInput.isBlank()) {
                if (!FacultyValidator.validateFacultyShortName(shortNameInput)) {
                    System.out.println(RED + " ✗ Invalid short name format." + RESET);
                    log.warn("Invalid short name format entered during edit: '{}'", shortNameInput);
                    return;
                }
                finalShortName = shortNameInput;
            }

            // 4. Head of Faculty
            String headInput = reader.readLine(YELLOW + " ❯ " + RESET + "Head (" + faculty.getHeadOfFaculty() + "): ");
            String finalHead = faculty.getHeadOfFaculty();
            if (!headInput.isBlank()) {
                if (!FacultyValidator.validateHeadOfFaculty(headInput)) {
                    System.out.println(RED + " ✗ Invalid head of faculty format." + RESET);
                    log.warn("Invalid head format entered during edit: '{}'", headInput);
                    return;
                }
                finalHead = headInput;
            }

            // 5. Contacts
            String contactsInput = reader.readLine(YELLOW + " ❯ " + RESET + "Contacts (" + faculty.getContactsOfFaculty() + "): ");
            String finalContacts = faculty.getContactsOfFaculty();
            if (!contactsInput.isBlank()) {
                if (!FacultyValidator.validatePhoneNumber(contactsInput)) {
                    System.out.println(RED + " ✗ Invalid contacts format." + RESET);
                    log.warn("Invalid contacts format entered during edit: '{}'", contactsInput);
                    return;
                }
                finalContacts = contactsInput;
            }

            facultyService.editFaculty(id, finalName, finalShortName, finalHead, finalContacts);

            System.out.println(GREEN + " ✓ Faculty updated successfully." + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

            log.info("Faculty ID: {} was updated successfully", id);

        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
            log.warn("Validation failed during faculty edit: {}", e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
            log.warn("EntityNotFound exception during faculty edit: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Invalid faculty id. Please enter a number." + RESET);
            log.warn("Invalid number format entered during faculty edit");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error updating faculty: " + e.getMessage() + RESET);
            log.error("Unexpected error occurred while editing a faculty", e);
        }
    }

    public void handleShowAllFaculties() {
        log.info("User requested to view all faculties");
        List<Faculty> faculties = facultyService.findAll();

        if (faculties.isEmpty()) {
            System.out.println(YELLOW + "\n ⚠ " + RESET + "No faculties found in the system.\n");
            return;
        }

        System.out.println(CYAN_BOLD + "\n================ FACULTIES ================" + RESET);

        for (Faculty f : faculties) {

            System.out.println(CYAN + "\n[ Faculty ID: " + f.getIdFaculty() + " ]" + RESET);
            System.out.println("Name: " + f.getFacultyName());
            System.out.println("Short Name: " + f.getFacultyShortName());
            System.out.println("Head: " + f.getHeadOfFaculty());
            System.out.println("Contact number of faculty: " + f.getContactsOfFaculty());
            System.out.println(CYAN + "------------------------------------------" + RESET);

            if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                System.out.println(YELLOW + "   ℹ " + RESET + "No departments.");
            } else {
                System.out.println(CYAN_BOLD + "   Departments:" + RESET);
                for (var d : f.getDepartments()) {
                    System.out.println("   ID: " + d.getIdDepartment()
                            + " | Name: " + d.getDepartmentName()
                            + " | Head: " + d.getHeadOfDepartment()
                            + " | Room: " + d.getRoomNumber());
                }
            }
        }
        System.out.println(CYAN_BOLD + "\n=============================================\n" + RESET);
    }

    public void handelShowStudentsReportByPibFromFaculty(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== STUDENTS REPORT BY PIB (FACULTY) ==" + RESET);
        try {
            int facultyId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter Faculty ID: "));
            FacultyValidator.validateId(facultyId);
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                FacultyReport.generateStudentsAlphabeticalReport(faculty);
                log.info("Generated 'Students Alphabetical' report for Faculty ID: {}", facultyId);
            }
        } catch (EntityNotEmptyException e){
            System.out.println(RED + " ✗ Empty Error: " + e.getMessage() + RESET);
            log.warn("Report empty: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error." + RESET);
            log.warn("Invalid number format for faculty ID report");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error generating student report by faculty", e);
        }
    }

    public void handelShowTeacherReportByPibFromFaculty(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== TEACHERS REPORT BY PIB (FACULTY) ==" + RESET);
        try {
            int facultyId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter Faculty ID: "));
            FacultyValidator.validateId(facultyId);
            Faculty faculty = facultyService.findById(facultyId);

            if (faculty != null) {
                FacultyReport.generateTeachersAlphabeticalReport(faculty);
                log.info("Generated 'Teachers Alphabetical' report for Faculty ID: {}", facultyId);
            }
        } catch (EntityNotEmptyException e){
            System.out.println(RED + " ✗ Empty Error: " + e.getMessage() + RESET);
            log.warn("Report empty: {}", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error." + RESET);
            log.warn("Invalid number format for faculty ID report");
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
            log.error("Unexpected error generating teacher report by faculty", e);
        }
    }
}