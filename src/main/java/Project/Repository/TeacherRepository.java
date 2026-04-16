package Project.Repository;

import Project.Models.Department;
import Project.Models.Teacher;
import Project.Models.University;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherRepository {

    private final UniversityRepository universityRepository;

    public TeacherRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<Teacher> findAll() {

        Optional<University> uniOpt = safeLoad();
        if (uniOpt.isEmpty()) return new ArrayList<>();

        List<Teacher> result = new ArrayList<>();

        for (var faculty : uniOpt.get().faculties()) {
            if (faculty.getDepartments() == null) continue;

            for (Department d : faculty.getDepartments()) {
                if (d.getTeachers() == null) continue;
                result.addAll(d.getTeachers());
            }
        }

        return result;
    }

    public Teacher findById(int idPerson) {

        return findAll().stream()
                .filter(t -> t.getIdPerson() == idPerson)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found: " + idPerson));
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