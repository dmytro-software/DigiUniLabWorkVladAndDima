package Project.ui;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.service.DepartmentService;
import org.jline.reader.LineReader;

import java.util.List;

public class DepartmentConsoleHandler {
    private final DepartmentService departmentService;

    public DepartmentConsoleHandler(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void handleAddDepartment(LineReader reader) {
        try {
            String name = reader.readLine("Department Name: ");
            int facultyId = Integer.parseInt(reader.readLine("Faculty ID: "));
            String head = reader.readLine("Head: ");
            int room = Integer.parseInt(reader.readLine("Room Number: "));
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

            String name = reader.readLine("New Name (leave blank to keep '" + department.getDepartmentName() + "'): ");
            int facultyId= Integer.parseInt(reader.readLine("New Faculty ID (leave blank to keep '" + department.getFaculty() + "'): "));
            String head = reader.readLine("New Head (leave blank to keep '" + department.getHeadOfDepartment() + "'): ");
            int roomNumber = Integer.parseInt(reader.readLine("New Room Number (leave blank to keep '" + department.getRoomNumber() + "'): "));


            departmentService.editDepartment(departmentId, name, facultyId, head,  roomNumber);
            System.out.println("Department updated successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. ID must be a number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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