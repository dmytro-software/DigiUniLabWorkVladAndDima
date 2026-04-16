package Project.service.Impl;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.University;
import Project.Repository.UniversityRepository;
import Project.service.DepartmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private final UniversityRepository universityRepository;

    public DepartmentServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    // =========================
    // ADD DEPARTMENT
    // =========================
    @Override
    public void addDepartment(int id, String name, int facultyId, String head, int room) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        Faculty faculty = uni.faculties()
                .stream()
                .filter(f -> f.getIdFaculty() == facultyId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with ID: " + facultyId));

        boolean exists = faculty.getDepartments()
                .stream()
                .anyMatch(d -> d.getIdDepartment() == id);

        if (exists) {
            throw new RuntimeException("Department already exists with ID: " + id);
        }

        Department newDepartment = new Department(id, name, facultyId, head, room);
        faculty.getDepartments().add(newDepartment);

        universityRepository.saveUniversity(uni);
    }

    // =========================
    // REMOVE DEPARTMENT
    // =========================
    @Override
    public boolean removeDepartment(int id) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        for (Faculty faculty : uni.faculties()) {

            Department dep = faculty.getDepartments()
                    .stream()
                    .filter(d -> d.getIdDepartment() == id)
                    .findFirst()
                    .orElse(null);

            if (dep != null) {

                if ((dep.getStudents() != null && !dep.getStudents().isEmpty()) ||
                        (dep.getTeachers() != null && !dep.getTeachers().isEmpty())) {
                    throw new EntityNotEmptyException(
                            "Cannot delete department. Students or teachers exist."
                    );
                }

                faculty.getDepartments().remove(dep);
                universityRepository.saveUniversity(uni);
                return true;
            }
        }

        return false;
    }

    // =========================
    // EDIT DEPARTMENT
    // =========================
    @Override
    public void editDepartment(int id, String name, int facultyId, String head, Integer roomNumber)
            throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        for (Faculty faculty : uni.faculties()) {
            for (Department department : faculty.getDepartments()) {

                if (department.getIdDepartment() == id) {

                    if (name != null && !name.isBlank())
                        department.setDepartmentName(name);

                    if (head != null && !head.isBlank())
                        department.setHeadOfDepartment(head);

                    if (roomNumber != null)
                        department.setRoomNumber(roomNumber);

                    universityRepository.saveUniversity(uni);
                    return;
                }
            }
        }

        throw new EntityNotFoundException("Department not found with ID: " + id);
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Department findById(int id) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        return uni.faculties().stream()
                .flatMap(f -> f.getDepartments().stream())
                .filter(d -> d.getIdDepartment() == id)
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Department with ID " + id + " not found"));
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public List<Department> findAll() throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        List<Department> result = new ArrayList<>();

        for (Faculty faculty : uni.faculties()) {
            result.addAll(faculty.getDepartments());
        }

        return result;
    }
}