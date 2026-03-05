package Project.service;

import Project.Models.Department;

import java.util.List;

public interface DepartmentService {
    void addDepartment(int id,
                       String name,
                       int facultyId,
                       String head,
                       int room);

    boolean removeDepartment(int id);

    void editDepartment(int id,
                        String name,
                        int facultyId,
                        String head,
                        Integer roomNumber);

    Department findById(int id);

    List<Department> findAll();

}
