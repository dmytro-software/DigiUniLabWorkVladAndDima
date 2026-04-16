package Project.service;

import Project.Models.Department;

import java.io.IOException;
import java.util.List;

public interface DepartmentService {
    void addDepartment(int id,
                       String name,
                       int facultyId,
                       String head,
                       int room) throws IOException;

    boolean removeDepartment(int id) throws IOException;

    void editDepartment(int id,
                        String name,
                        int facultyId,
                        String head,
                        Integer roomNumber) throws IOException;

    Department findById(int id) throws IOException;

    List<Department> findAll() throws IOException;

}
