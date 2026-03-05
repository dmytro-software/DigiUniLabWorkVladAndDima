package Project.ui;

import Project.Models.Faculty;
import Project.service.FacultyService;

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
            String name = reader.readLine("Faculty Name: ");
            String shortName = reader.readLine("Short Name: ");
            String head = reader.readLine("Head: ");
            String contacts = reader.readLine("Contacts: ");

            Faculty newFaculty = new Faculty(id, name, shortName, head, contacts);
            facultyService.addFaculty(newFaculty);
            System.out.println("Faculty created.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleRemoveFaculty(LineReader reader) {
        try {
            String idInput = reader.readLine("Введіть ID факультету для видалення: ");
            int id = Integer.parseInt(idInput.trim());

            facultyService.removeFaculty(id);

            System.out.println("Готово! Якщо такий ID був, факультет видалено.");
        } catch (Exception e) {
            System.out.println("Помилка: введіть коректне число.");
        }
    }

    public void handleEditFaculty(LineReader reader) {

        try {
            String idInput = reader.readLine("Enter faculty id: ");

            if (idInput == null || idInput.isBlank()) {
                System.out.println("Faculty id cannot be empty.");
                return;
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
}