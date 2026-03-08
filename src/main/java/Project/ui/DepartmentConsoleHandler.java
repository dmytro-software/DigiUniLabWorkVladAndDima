package Project.ui;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.service.DepartmentService;
import Project.service.FacultyService;
import Project.validation.DepartmentValidator;
import org.jline.reader.LineReader;

import java.util.List;

public class DepartmentConsoleHandler {
    private final DepartmentService departmentService;
    private final FacultyService facultyService;

    public DepartmentConsoleHandler(DepartmentService departmentService, FacultyService facultyService) {
        this.departmentService = departmentService;
        this.facultyService = facultyService;
    }

    public void handleAddDepartment(LineReader reader) {
        try {
            String name = reader.readLine("Department Name: ");
            DepartmentValidator.validateDepartmentName(name);

            int facultyId = Integer.parseInt(reader.readLine("Faculty ID: "));
            DepartmentValidator.validateFacultyId(facultyId);

            String head = reader.readLine("Head: ");
            DepartmentValidator.validateHeadOfDepartment(head);

            int room = Integer.parseInt(reader.readLine("Room Number: "));
            DepartmentValidator.validateRoomNumber(room);

            int id = (int) (Math.random() * 900000) + 100000;

            departmentService.addDepartment(id, name, facultyId, head, room);

            System.out.println("Department linked and created.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleRemoveDepartment(LineReader reader) {
        try {
            int departmentId = Integer.parseInt(reader.readLine("Enter ID to remove: "));

            boolean removed = departmentService.removeDepartment(departmentId);

            if (removed) {
                System.out.println("Department removed successfully.");
            } else {
                System.out.println("Department not found.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void handleEditDepartment(LineReader reader) {
        try {
            int departmentId = Integer.parseInt(reader.readLine("Enter Department ID to edit: "));
            Department department = departmentService.findById(departmentId);

            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("Editing department. Press Enter to keep current value.");

            // Name
            String name = reader.readLine("New Name (" + department.getDepartmentName() + "): ");
            if (!name.isBlank()) {
                DepartmentValidator.validateDepartmentName(name);
                department.setDepartmentName(name);
            }

            // Faculty ID
            String facultyInput = reader.readLine("New Faculty ID (" + department.getFacultyId() + "): ");
            int facultyId;
            if (facultyInput.isBlank()) {
                facultyId = department.getFacultyId();
            } else {
                facultyId = Integer.parseInt(facultyInput);
                if (facultyService.findById(facultyId) == null) {
                    System.out.println("Department with this ID does not exist!");
                    return;

                }
                DepartmentValidator.validateFacultyId(departmentId);
                department.setFacultyId(facultyId);
            }

                // Head
                String head = reader.readLine("New Head (" + department.getHeadOfDepartment() + "): ");
                if (!head.isBlank()) {
                    DepartmentValidator.validateHeadOfDepartment(head);
                    department.setHeadOfDepartment(head);
                }

                // Room Number
                String roomInput = reader.readLine("New Room Number (" + department.getRoomNumber() + "): ");
                int roomNumber;
                if (roomInput.isBlank()) {
                    roomNumber = department.getRoomNumber();
                } else {
                    roomNumber = Integer.parseInt(roomInput);
                    DepartmentValidator.validateRoomNumber(roomNumber);
                    department.setRoomNumber(roomNumber);
                }

                departmentService.editDepartment(departmentId, name, facultyId, head, roomNumber);
                System.out.println("Department updated successfully.");

            } catch(NumberFormatException e){
                System.out.println("Error: Please enter a valid numeric value.");
            } catch(IllegalArgumentException e){
                System.out.println("Validation Error: " + e.getMessage());
            }
    }

    public void handleShowAllDepartments() {
        List<Department> departments = departmentService.findAll();

        if (departments.isEmpty()) {
            System.out.println("No departments found in the system.");
            return;
        }

        System.out.println("\n================ DEPARTMENTS ================\n");

        for (Department department : departments) {

            System.out.println("Department ID: " + department.getIdDepartment());
            System.out.println("Name: " + department.getDepartmentName());
            System.out.println("Head: " + department.getHeadOfDepartment());
            System.out.println("Room number: " + department.getRoomNumber());
            System.out.println("------------------------------------------");

            if (department.getTeachers() == null || department.getTeachers().isEmpty()) {
                System.out.println("   No teachers.");
            } else {
                System.out.println("   Teachers:");
                for (var t : department.getTeachers()) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("Person ID: " + t.getIdPerson());
                    System.out.println("Full Name: " + t.getPib());
                    System.out.println("Birth Date: " + t.getBirthDate());
                    System.out.println("Email: " + t.getEmail());
                    System.out.println("Phone: " + t.getPhoneNumber());
                    System.out.println("      • ID: " + t.getTeacherId()
                            + " | Full Name: " + t.getPib()
                            + " | Email: " + t.getEmail()
                            + " | Phone: " + t.getPhoneNumber()
                            + " | Position: " + t.getPosition()
                            + " | Degree: " + t.getAcademicDegree()
                            + " | Rank: " + t.getAcademicRank()
                            + " | Hire date: " + t.getHireDate()
                            + " | FTE: " + t.getFullTimeEquivalent());
                }
            }

            if (department.getStudents() == null || department.getStudents().isEmpty()) {
                System.out.println("   No students.");
            } else {
                System.out.println("   Students:");
                for (var s : department.getStudents()) {
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

            System.out.println("\n==========================================\n");
        }
    }
}