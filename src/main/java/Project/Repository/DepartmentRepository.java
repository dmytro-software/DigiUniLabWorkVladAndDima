package Project.Repository;

import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.University;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    private final UniversityRepository universityRepository;

    public DepartmentRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    // =========================
    // GET ALL
    // =========================
    public List<Department> findAll() {

        Optional<University> uniOpt = safeLoad();
        if (uniOpt.isEmpty()) return new ArrayList<>();

        List<Department> result = new ArrayList<>();

        for (Faculty f : uniOpt.get().faculties()) {
            result.addAll(f.getDepartments());
        }

        return result;
    }

    // =========================
    // FIND BY ID
    // =========================
    public Department findById(int id) {

        return findAll().stream()
                .filter(d -> d.getIdDepartment() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Department not found: " + id));
    }

    // =========================
    // SAVE (FULL UNIVERSITY)
    // =========================
    public void save() {
        try {
            universityRepository.saveUniversity(getUniversity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // HELPER
    // =========================
    private University getUniversity() {
        try {
            return universityRepository.loadUniversity()
                    .orElseThrow(() -> new RuntimeException("University not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<University> safeLoad() {
        try {
            return universityRepository.loadUniversity();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}