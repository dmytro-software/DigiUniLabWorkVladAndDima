package Project.Repository;

import Project.Models.Faculty;
import Project.Models.University;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FacultyRepository {

    private final UniversityRepository universityRepository;

    public FacultyRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<Faculty> findAll() throws IOException {
        return universityRepository
                .loadUniversity()
                .map(University::faculties)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    public Faculty findById(int id) throws IOException {
        return findAll().stream()
                .filter(f -> f.getIdFaculty() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found: " + id));
    }

    // ❗ ЗБЕРІГАННЯ ТІЛЬКИ ЧЕРЕЗ UNIVERSITY
    public void saveAll(List<Faculty> faculties) {
        try {
            University uni = universityRepository.loadUniversity()
                    .orElseThrow(() -> new RuntimeException("University not found"));

            uni.faculties().clear();
            uni.faculties().addAll(faculties);

            universityRepository.saveUniversity(uni);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<List<Faculty>> loadAll() throws IOException {
        return universityRepository.loadUniversity()
                .map(u -> u.faculties() == null
                        ? List.of()
                        : List.copyOf(u.faculties()));
    }
}