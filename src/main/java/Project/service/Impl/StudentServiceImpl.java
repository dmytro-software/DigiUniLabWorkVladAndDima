package Project.service.Impl;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.University;
import Project.Repository.DepartmentRepository;
import Project.Repository.StudentRepository;
import Project.Repository.UniversityRepository;
import Project.service.DepartmentService;
import Project.service.StudentService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentServiceImpl implements StudentService {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private UniversityRepository universityRepository;

    public StudentServiceImpl(DepartmentService departmentService,
                              StudentRepository studentRepository,
                              DepartmentRepository departmentRepository, UniversityRepository universityRepository) {
        this.departmentService = departmentService;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    // =========================
    // ADD STUDENT
    // =========================
    @Override
    public void addStudent(
            int departmentId,
            int idPerson,
            String pib,
            LocalDate birthDate,
            String email,
            int phoneNumber,
            int gradeBookId,
            int course,
            int group,
            int enrollmentYear,
            String formOfEducation,
            String studentStatus
    ) throws IOException {

        University uni = universityRepository.loadUniversity()
                .orElseThrow(() -> new EntityNotFoundException("University not found"));

        // 🔥 знайти кафедру через всі факультети
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

        // 🔥 ініціалізація списку студентів
        if (department.getStudents() == null) {
            department.setStudents(new ArrayList<>());
        }

        boolean exists = department.getStudents()
                .stream()
                .anyMatch(s -> s.getIdPerson() == idPerson);

        if (exists) {
            throw new RuntimeException("Student already exists with ID: " + idPerson);
        }

        Student student = new Student(
                idPerson,
                pib,
                birthDate,
                email,
                phoneNumber,
                gradeBookId,
                course,
                group,
                enrollmentYear,
                formOfEducation,
                studentStatus,
                departmentId
        );

        department.getStudents().add(student);

        universityRepository.saveUniversity(uni);
    }

    // =========================
    // REMOVE STUDENT
    // =========================
    @Override
    public boolean removeStudent(int gradeBookId) throws IOException {

        for (Department department : departmentService.findAll()) {

            boolean removed = department.getStudents()
                    .removeIf(s -> s.getGradeBookId() == gradeBookId);

            if (removed) {
                departmentRepository.save();
                return true;
            }
        }

        return false;
    }

    // =========================
    // EDIT STUDENT
    // =========================
    @Override
    public void editStudent(int gradeBookId,
                            String pib,
                            String email,
                            int phoneNumber,
                            int course,
                            int group,
                            String formOfEducation,
                            String studentStatus) throws IOException {

        for (Department department : departmentService.findAll()) {

            for (Student student : department.getStudents()) {

                if (student.getGradeBookId() == gradeBookId) {

                    student.setPib(pib);
                    student.setEmail(email);
                    student.setPhoneNumber(phoneNumber);
                    student.setCourse(course);
                    student.setGroup(group);
                    student.setFormOfEducation(formOfEducation);
                    student.setStudentStatus(studentStatus);

                    // ❗ save whole university tree
                    departmentRepository.save();
                    return;
                }
            }
        }

        throw new EntityNotFoundException("Student not found with gradeBookId: " + gradeBookId);
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public List<Student> findAll() throws IOException {

        List<Student> allStudents = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            if (department.getStudents() != null) {
                allStudents.addAll(department.getStudents());
            }
        }

        return allStudents;
    }

    // =========================
    // FIND BY GRADEBOOK
    // =========================
    @Override
    public Optional<Student> findStudentByGradeBook(int gradeBookId) throws IOException {

        for (Department department : departmentService.findAll()) {

            if (department.getStudents() == null) continue;

            for (Student student : department.getStudents()) {
                if (student.getGradeBookId() == gradeBookId) {
                    return Optional.of(student);
                }
            }
        }

        return Optional.empty();
    }

    // =========================
    // FIND BY PIB
    // =========================
    @Override
    public List<Student> findByPib(String pib) throws IOException {

        List<Student> found = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getPib().equalsIgnoreCase(pib)) {
                    found.add(student);
                }
            }
        }

        System.out.println("Search results: " + pib);
        System.out.println("Found: " + found.size());

        return found;
    }

    // =========================
    // FIND BY GROUP
    // =========================
    @Override
    public List<Student> findByGroup(int group) throws IOException {

        List<Student> found = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getGroup() == group) {
                    found.add(student);
                }
            }
        }

        System.out.println("Search by group: " + group);
        System.out.println("Total found: " + found.size());

        return found;
    }

    // =========================
    // FIND BY COURSE
    // =========================
    @Override
    public List<Student> findByCourse(int course) throws IOException {

        List<Student> found = new ArrayList<>();

        for (Department department : departmentService.findAll()) {
            for (Student student : department.getStudents()) {
                if (student.getCourse() == course) {
                    found.add(student);
                }
            }
        }

        System.out.println("Search by course: " + course);
        System.out.println("Total found: " + found.size());

        return found;
    }

    @Override
    public void changeDepartment(Department department, Student student) {


    }
}