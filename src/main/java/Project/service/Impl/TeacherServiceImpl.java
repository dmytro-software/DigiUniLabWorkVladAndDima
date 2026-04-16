package Project.service.Impl;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Teacher;
import Project.Models.University;
import Project.Repository.DepartmentRepository;
import Project.Repository.UniversityRepository;
import Project.service.DepartmentService;
import Project.service.TeacherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherServiceImpl implements TeacherService {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;
    private final UniversityRepository universityRepository;

    public TeacherServiceImpl(DepartmentService departmentService,
                              DepartmentRepository departmentRepository,
                              UniversityRepository universityRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public void addTeacher(
            int departmentId,
            int idPerson,
            String pib,
            java.time.LocalDate birthDate,
            String email,
            int phoneNumber,
            int teacherId,
            String position,
            String academicDegree,
            String academicRank,
            String hireDate,
            double fullTimeEquivalent
    ) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        Department department = null;

        for (Faculty faculty : uni.faculties()) {

            department = faculty.getDepartments()
                    .stream()
                    .filter(d -> d.getIdDepartment() == departmentId)
                    .findFirst()
                    .orElse(null);

            if (department != null) break;
        }

        if (department == null) {
            throw new EntityNotFoundException("Department not found with ID: " + departmentId);
        }

        // init teachers list
        if (department.getTeachers() == null) {
            department.setTeachers(new ArrayList<>());
        }

        boolean exists = department.getTeachers()
                .stream()
                .anyMatch(t -> t.getIdPerson() == idPerson);

        if (exists) {
            throw new RuntimeException("Teacher already exists with ID: " + idPerson);
        }

        Teacher teacher = new Teacher(
                idPerson,
                pib,
                birthDate,
                email,
                phoneNumber,
                teacherId,
                position,
                departmentId,
                academicDegree,
                academicRank,
                hireDate,
                fullTimeEquivalent
        );

        department.getTeachers().add(teacher);

        universityRepository.saveUniversity(uni);
    }

    // =========================
    // REMOVE TEACHER
    // =========================
    @Override
    public boolean removeTeacher(int teacherId) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        for (Faculty faculty : uni.faculties()) {

            for (Department d : faculty.getDepartments()) {

                if (d.getTeachers() == null) continue;

                boolean removed = d.getTeachers()
                        .removeIf(t -> t.getTeacherId() == teacherId);

                if (removed) {
                    universityRepository.saveUniversity(uni);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void editTeacher(
            int departmentId,
            int idPerson,
            String pib,
            String birthDate,
            String email,
            int phoneNumber,
            int teacherId,
            String position,
            String academicDegree,
            String academicRank,
            String hireDate,
            Double fullTimeEquivalent
    ) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        Teacher targetTeacher = null;
        Department targetDepartment = null;
        outer:
        for (Faculty faculty : uni.faculties()) {

            if (faculty.getDepartments() == null) continue;

            for (Department d : faculty.getDepartments()) {

                if (d.getTeachers() == null) continue;

                for (Teacher t : d.getTeachers()) {

                    if (t.getIdPerson() == idPerson) {
                        targetTeacher = t;
                        targetDepartment = d;
                        break outer;
                    }
                }
            }
        }

        if (targetTeacher == null) {
            throw new EntityNotFoundException("Teacher not found with idPerson: " + idPerson);
        }

        targetTeacher.setPib(pib);
        targetTeacher.setEmail(email);
        targetTeacher.setPhoneNumber(phoneNumber);

        targetTeacher.setPosition(position);
        targetTeacher.setAcademicDegree(academicDegree);
        targetTeacher.setAcademicRank(academicRank);
        targetTeacher.setHireDate(hireDate);
        targetTeacher.setFullTimeEquivalent(fullTimeEquivalent);


        universityRepository.saveUniversity(uni);
    }

    @Override
    public List<Teacher> findAll() throws IOException {

        List<Teacher> result = new ArrayList<>();

        for (Department d : departmentService.findAll()) {

            if (d.getTeachers() != null) {
                result.addAll(d.getTeachers());
            }
        }

        return result;
    }

    @Override
    public Optional<Teacher> findByPib(String pib) throws IOException {

        for (Department d : departmentService.findAll()) {

            if (d.getTeachers() == null) continue;

            for (Teacher t : d.getTeachers()) {
                if (t.getPib().equalsIgnoreCase(pib)) {
                    return Optional.of(t);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Teacher> findByTeacherId(int teacherId) throws IOException {

        for (Department d : departmentService.findAll()) {

            if (d.getTeachers() == null) continue;

            for (Teacher t : d.getTeachers()) {
                if (t.getTeacherId() == teacherId) {
                    return Optional.of(t);
                }
            }
        }

        return Optional.empty();
    }
}