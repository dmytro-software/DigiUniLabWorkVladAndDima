package Project.ui;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.ValidationException;
import Project.Models.Faculty;
import Project.Reports.FacultyReport;
import Project.service.FacultyService;

import Project.validation.FacultyValidator;
import org.jline.reader.LineReader;

import java.util.List;

public class FacultyConsoleHandler {
    private final FacultyService facultyService;

    public FacultyConsoleHandler(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    public void handleAddFaculty(LineReader reader) {
        try {
            int id = (int) (Math.random() * 900000) + 100000;
            System.out.println("Faculty id: " + id);

            String name = reader.readLine("Faculty Name: ");
            FacultyValidator.validateFacultyName(name);

            String shortName = reader.readLine("Short Name: ");
            FacultyValidator.validateFacultyShortName(shortName);

            String head = reader.readLine("Head: ");
            FacultyValidator.validateHeadOfFaculty(head);

            String contacts = reader.readLine("Contacts: ");
            FacultyValidator.validatePhoneNumber(contacts);

            Faculty newFaculty = new Faculty(id, name, shortName, head, contacts);
            facultyService.addFaculty(newFaculty);
            System.out.println("Faculty created.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Numeric format error");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleRemoveFaculty(LineReader reader) {
        try {
            String idInput = reader.readLine("Enter Faculty Id for remove: ");
            int id = Integer.parseInt(idInput.trim());

            facultyService.removeFaculty(id);

            System.out.println("Faculty with id " + id + " was successfully deleted");
        } catch (EntityNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Number format error");
        } catch (EntityNotEmptyException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleEditFaculty(LineReader reader) {

        try {
            String idInput = reader.readLine("Enter faculty id: ");
            int idFaculty;
            if(idInput.isBlank()){
//                idFaculty =

            }

            int id = Integer.parseInt(idInput);

            String name = reader.readLine("Faculty Name: ");
            if (name == null || name.isBlank()) {
                System.out.println("Faculty name cannot be empty.");
                return;
            }

            String shortName = reader.readLine("Short Name: ");
            if (shortName == null || shortName.isBlank()) {
                System.out.println("Short name cannot be empty.");
                return;
            }

            String head = reader.readLine("Head: ");
            if (head == null || head.isBlank()) {
                System.out.println("Head cannot be empty.");
                return;
            }

            String contacts = reader.readLine("Contacts: ");
            if (contacts == null || contacts.isBlank()) {
                System.out.println("Contacts cannot be empty.");
                return;
            }

            facultyService.editFaculty(id, name, shortName, head, contacts);
            System.out.println("Faculty updated successfully.");

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Search Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid faculty id. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error updating faculty: " + e.getMessage());
        }
    }
    public void handleShowAllFaculties() {
        List<Faculty> faculties = facultyService.findAll();

        if (faculties.isEmpty()) {
            System.out.println("No faculties found in the system.");
            return;
        }

        System.out.println("\n================ FACULTIES ================\n");

        for (Faculty f : faculties) {

            System.out.println("Faculty ID: " + f.getIdFaculty());
            System.out.println("Name: " + f.getFacultyName());
            System.out.println("Short Name: " + f.getFacultyShortName());
            System.out.println("Head: " + f.getHeadOfFaculty());
            System.out.println("Contact number of faculty: " + f.getContactsOfFaculty());
            System.out.println("------------------------------------------");

            if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                System.out.println("   No departments.");
            } else {
                System.out.println("   Departments:");
                for (var d : f.getDepartments()) {
                    System.out.println("ID: " + d.getIdDepartment()
                            + " | Name: " + d.getDepartmentName()
                            + " | Head: " + d.getHeadOfDepartment()
                            + " | Room: " + d.getRoomNumber());
                }
            }

            System.out.println("\n==========================================\n");
        }
    }

    public void handelShowStudentsReportByPibFromFaculty(LineReader reader){
        try {
            int facultyId = Integer.parseInt(reader.readLine("Enter Faculty ID: "));
            FacultyValidator.validateId(facultyId);
            Faculty faculty = facultyService.findById(facultyId);
            if (faculty != null) {
                FacultyReport.generateStudentsAlphabeticalReport(faculty);
            }
        }catch (EntityNotEmptyException e){
            System.out.println("Empty Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handelShowTeacherReportByPibFromFaculty(LineReader reader){
        try {
            int facultyId = Integer.parseInt(reader.readLine("Enter Faculty ID: "));
            FacultyValidator.validateId(facultyId);
            Faculty faculty = facultyService.findById(facultyId);

            if (faculty != null) {
                FacultyReport.generateTeachersAlphabeticalReport(faculty);
            }
        }catch (EntityNotEmptyException e){
            System.out.println("Empty Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}