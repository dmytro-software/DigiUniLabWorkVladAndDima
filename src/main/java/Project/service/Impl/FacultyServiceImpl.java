package Project.service.Impl;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.University;
import Project.Repository.DepartmentRepository;
import Project.Repository.FacultyRepository;
import Project.Repository.UniversityRepository;
import Project.service.FacultyService;

import java.io.IOException;
import java.util.*;

public class FacultyServiceImpl implements FacultyService {

    private final University university;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private  UniversityRepository universityRepository;

    public FacultyServiceImpl(University university,
                              FacultyRepository facultyRepository,
                              DepartmentRepository departmentRepository, UniversityRepository universityRepository) {
        this.university = university;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public void addFaculty(Faculty faculty) throws IOException {

        if (faculty == null)
            throw new IllegalArgumentException("Faculty cannot be null");

        boolean exists = university.faculties()
                .stream()
                .anyMatch(f -> f.getIdFaculty() == faculty.getIdFaculty());

        if (exists) {
            System.out.println("⚠ Faculty already exists: " + faculty.getIdFaculty());
            return;
        }

        university.faculties().add(faculty);

        facultyRepository.saveAll(university.faculties());
    }

    @Override
    public boolean removeFaculty(int id) throws IOException {

        Faculty fac = findById(id);

        // перевірка кафедр через університет (НЕ через repo load)
        boolean hasDepartments = fac.getDepartments() != null &&
                !fac.getDepartments().isEmpty();

        if (hasDepartments) {
            throw new EntityNotEmptyException(
                    "Cannot delete faculty. It contains departments."
            );
        }

        university.faculties().remove(fac);

        facultyRepository.saveAll(university.faculties());
        return true;
    }

    @Override
    public void editFaculty(int id,
                            String name,
                            String shortName,
                            String head,
                            String contacts) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        Faculty faculty = uni.faculties()
                .stream()
                .filter(f -> f.getIdFaculty() == id)
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Faculty not found"));

        if (name != null && !name.isBlank())
            faculty.setFacultyName(name);

        if (shortName != null && !shortName.isBlank())
            faculty.setFacultyShortName(shortName);

        if (head != null && !head.isBlank())
            faculty.setHeadOfFaculty(head);

        if (contacts != null && !contacts.isBlank())
            faculty.setContactsOfFaculty(contacts);

        universityRepository.saveUniversity(uni);
    }

    @Override
    public Faculty findById(int id) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        return uni.faculties()
                .stream()
                .filter(f -> f.getIdFaculty() == id)
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Faculty with ID " + id + " not found"));
    }

    @Override
    public List<Faculty> findAll() {
        return new ArrayList<>(university.faculties());
    }
}