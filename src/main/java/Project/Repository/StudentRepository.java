package Project.Repository;

import Project.Models.Department;
import Project.Models.Student;
import Project.Models.University;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {

    private final UniversityRepository universityRepository;

    public StudentRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<Student> findAll() {

        Optional<University> uniOpt = safeLoad();
        if (uniOpt.isEmpty()) return new ArrayList<>();

        List<Student> result = new ArrayList<>();

        for (var faculty : uniOpt.get().faculties()) {
            for (Department d : faculty.getDepartments()) {
                result.addAll(d.getStudents());
            }
        }

        return result;
    }

    public Student findById(int idPerson) {

        return findAll().stream()
                .filter(s -> s.getIdPerson() == idPerson)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Student not found: " + idPerson));
    }

    public void save() {
        try {
            universityRepository.saveUniversity(getUniversity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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