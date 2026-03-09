package Project.service.Impl;

import Project.Models.Department;
import Project.Models.Faculty;
import Project.service.DepartmentService;
import Project.service.FacultyService;
import java.util.ArrayList;
import java.util.List;


public class DepartmentServiceImpl implements DepartmentService {

    private final FacultyService facultyService;

    public DepartmentServiceImpl(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public void addDepartment(int id, String name, int facultyId, String head, int room) {
        Faculty faculty = facultyService.findById(facultyId);
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty not found with ID: " + facultyId);
        }

        Department newDepartment = new Department(id, name, facultyId, head, room);

        faculty.getDepartments().add(newDepartment);
    }
    @Override
    public boolean removeDepartment(int id) {

        for (Faculty faculty : facultyService.findAll()) {
            List<Department> departments = faculty.getDepartments();

            for (Department dep : departments) {
                if (dep.getIdDepartment() == id) {
                    departments.remove(dep);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void editDepartment(int id, String name, int facultyId, String head, Integer roomNumber) {
        Department department = findById(id);

        if (name != null && !name.isBlank())
            department.setDepartmentName(name);
        if (head != null && !head.isBlank())
            department.setHeadOfDepartment(head);
        if (roomNumber != null)
            department.setRoomNumber(roomNumber);
    }

    @Override
    public Department findById(int id) {
        for (Faculty faculty : facultyService.findAll()) {

            for (Department dep : faculty.getDepartments()) {

                if (dep.getIdDepartment() == id) {
                    return dep;
                }
            }
        }
        throw new IllegalArgumentException("Department with ID " + id + " not found");
    }

    @Override
    public List<Department> findAll() {

        List<Department> departments = new ArrayList<>();

        for (Faculty faculty : facultyService.findAll()) {
            for (Department department : faculty.getDepartments()) {
                departments.add(department);
            }
        }

        return departments;
    }

}