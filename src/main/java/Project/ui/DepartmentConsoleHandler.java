package Project.ui;
import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Exceptions.IsEmptyException;
import Project.Exceptions.ValidationException;
import Project.Models.Department;
import Project.Reports.DepartmentReport;
import Project.service.DepartmentService;
import Project.service.FacultyService;
import Project.validation.DepartmentValidator;
import org.jline.reader.LineReader;

import java.util.List;

import static Project.Models.ConsoleColors.*;

public class DepartmentConsoleHandler {
    private final DepartmentService departmentService;
    private final FacultyService facultyService;

    public DepartmentConsoleHandler(DepartmentService departmentService, FacultyService facultyService) {
        this.departmentService = departmentService;
        this.facultyService = facultyService;
    }

    public void handleAddDepartment(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== ADD NEW DEPARTMENT ==" + RESET);
        try {
            String name = reader.readLine(YELLOW + " ❯ " + RESET + "Department Name: ");
            DepartmentValidator.validateDepartmentName(name);

            int facultyId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Faculty ID: "));
            DepartmentValidator.validateFacultyId(facultyId);

            String head = reader.readLine(YELLOW + " ❯ " + RESET + "Head: ");
            DepartmentValidator.validateHeadOfDepartment(head);

            int room = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Room Number: "));
            DepartmentValidator.validateRoomNumber(room);

            int id = (int) (Math.random() * 900000) + 100000;
            System.out.println(CYAN + " ℹ " + RESET + "Generated Department ID: " + id);

            departmentService.addDepartment(id, name, facultyId, head, room);

            System.out.println(GREEN + " ✓ Department '" + name + "' linked and created successfully!" + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleRemoveDepartment(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== REMOVE DEPARTMENT ==" + RESET);
        try {
            int departmentId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter ID to remove: "));

            boolean removed = departmentService.removeDepartment(departmentId);

            if (removed) {
                System.out.println(GREEN + " ✓ Department removed successfully." + RESET);
            } else {
                System.out.println(RED + " ✗ Department not found." + RESET);
            }
            System.out.println(CYAN + "----------------------------------------\n" + RESET);
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (EntityNotEmptyException e) {
            System.out.println(RED + " ✗ Delete Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error: ID must be a number." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleEditDepartment(LineReader reader) {
        System.out.println(CYAN_BOLD + "\n== EDIT DEPARTMENT ==" + RESET);
        try {
            int departmentId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter Department ID to edit: "));
            Department department = departmentService.findById(departmentId);

            if (department == null) {
                System.out.println(RED + " ✗ Department not found." + RESET);
                return;
            }

            System.out.println(CYAN + " ℹ " + RESET + "Editing department. Press Enter to keep current value.");

            // Name
            String name = reader.readLine(YELLOW + " ❯ " + RESET + "New Name (" + department.getDepartmentName() + "): ");
            if (!name.isBlank()) {
                DepartmentValidator.validateDepartmentName(name);
                department.setDepartmentName(name);
            }

            // Faculty ID
            String facultyInput = reader.readLine(YELLOW + " ❯ " + RESET + "New Faculty ID (" + department.getFacultyId() + "): ");
            int facultyId;
            if (facultyInput.isBlank()) {
                facultyId = department.getFacultyId();
            } else {
                facultyId = Integer.parseInt(facultyInput);
                if (facultyService.findById(facultyId) == null) {
                    System.out.println(RED + " ✗ Department with this ID does not exist!" + RESET);
                    return;
                }
                DepartmentValidator.validateFacultyId(departmentId);
                department.setFacultyId(facultyId);
            }

            // Head
            String head = reader.readLine(YELLOW + " ❯ " + RESET + "New Head (" + department.getHeadOfDepartment() + "): ");
            if (!head.isBlank()) {
                DepartmentValidator.validateHeadOfDepartment(head);
                department.setHeadOfDepartment(head);
            }

            // Room Number
            String roomInput = reader.readLine(YELLOW + " ❯ " + RESET + "New Room Number (" + department.getRoomNumber() + "): ");
            int roomNumber;
            if (roomInput.isBlank()) {
                roomNumber = department.getRoomNumber();
            } else {
                roomNumber = Integer.parseInt(roomInput);
                DepartmentValidator.validateRoomNumber(roomNumber);
                department.setRoomNumber(roomNumber);
            }

            departmentService.editDepartment(departmentId, name, facultyId, head, roomNumber);
            System.out.println(GREEN + " ✓ Department updated successfully." + RESET);
            System.out.println(CYAN + "----------------------------------------\n" + RESET);

        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Search Error: " + e.getMessage() + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (EntityNotEmptyException e) {
            System.out.println(RED + " ✗ Delete Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error: Please enter a valid number." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleShowAllDepartments() {
        List<Department> departments = departmentService.findAll();

        if (departments.isEmpty()) {
            System.out.println(YELLOW + "\n ⚠ " + RESET + "No departments found in the system.\n");
            return;
        }

        System.out.println(CYAN_BOLD + "\n================ DEPARTMENTS ================" + RESET);

        for (Department department : departments) {

            System.out.println(CYAN + "\n[ Department ID: " + department.getIdDepartment() + " ]" + RESET);
            System.out.println("Name: " + department.getDepartmentName());
            System.out.println("Head: " + department.getHeadOfDepartment());
            System.out.println("Room number: " + department.getRoomNumber());
            System.out.println(CYAN + "------------------------------------------" + RESET);

            if (department.getTeachers() == null || department.getTeachers().isEmpty()) {
                System.out.println(YELLOW + "   ℹ " + RESET + "No teachers.");
            } else {
                System.out.println(CYAN_BOLD + "   Teachers:" + RESET);
                for (var t : department.getTeachers()) {
                    System.out.println("   " + CYAN + "----------------------------------------------" + RESET);
                    System.out.println("   Person ID: " + t.getIdPerson());
                    System.out.println("   Full Name: " + t.getPib());
                    System.out.println("   Birth Date: " + t.getBirthDate());
                    System.out.println("   Email: " + t.getEmail());
                    System.out.println("   Phone: " + t.getPhoneNumber());
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
                System.out.println(YELLOW + "   ℹ " + RESET + "No students.");
            } else {
                System.out.println(CYAN_BOLD + "   Students:" + RESET);
                for (var s : department.getStudents()) {
                    System.out.println("   " + CYAN + "----------------------------------------------" + RESET);
                    System.out.println("   Person ID: " + s.getIdPerson());
                    System.out.println("   Full Name: " + s.getPib());
                    System.out.println("   Birth Date: " + s.getBirthDate());
                    System.out.println("   Email: " + s.getEmail());
                    System.out.println("   Phone: " + s.getPhoneNumber());

                    System.out.println("   GradeBook ID: " + s.getGradeBookId());
                    System.out.println("   Course: " + s.getCourse());
                    System.out.println("   Group: " + s.getGroup());
                    System.out.println("   Enrollment Year: " + s.getEnrollmentYear());
                    System.out.println("   Form of Education: " + s.getFormOfEducation());
                    System.out.println("   Student Status: " + s.getStudentStatus());
                }
            }
        }
        System.out.println(CYAN_BOLD + "\n=============================================\n" + RESET);
    }

    public void handleShowReportOfStudentGroupingByCourse(LineReader reader){
        System.out.println(CYAN_BOLD + "\n== STUDENT REPORT BY COURSE ==" + RESET);
        try {
            int departmentId = Integer.parseInt(reader.readLine(YELLOW + " ❯ " + RESET + "Enter Department ID to show: "));
            DepartmentValidator.validateFacultyId(departmentId);
            Department department = departmentService.findById(departmentId);
            DepartmentReport.showStudentReportByCourseFromDepartment(department);
        } catch (IsEmptyException e){
            System.out.println(RED + " ✗ Empty Error: " + e.getMessage() + RESET);
        } catch (EntityNotFoundException e) {
            System.out.println(RED + " ✗ Not found Error: " + e.getMessage() + RESET);
        } catch (NumberFormatException e) {
            System.out.println(RED + " ✗ Numeric format error: ID must be a number." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: "+ e.getMessage() + RESET);
        }
    }
}