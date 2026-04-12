package Project.Repository;

import Project.Models.Department;
import Project.Models.Student;
import Project.Models.Teacher;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository {

    private static StudentRepository studentRepository = new StudentRepository();
    private static TeacherRepository teacherRepository = new TeacherRepository();

    public DepartmentRepository(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }
    private static Path filePath = Path.of("data/departments.txt");

    public void saveAll(List<Department> departments) throws IOException {

        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

            for (Department d : departments) {

                // Department
                bw.write(d.getIdDepartment() + ";"
                        + d.getDepartmentName() + ";"
                        + d.getFacultyId() + ";"
                        + d.getHeadOfDepartment() + ";"
                        + d.getRoomNumber());
                bw.newLine();

                // Students
                for (Student s : d.getStudents()) {
                    bw.write("Student:" +
                            s.getIdPerson() + ";" +
                            s.getPib() + ";" +
                            s.getBirthDate() + ";" +
                            s.getEmail() + ";" +
                            s.getPhoneNumber() + ";" +
                            s.getGradeBookId() + ";" +
                            s.getCourse() + ";" +
                            s.getGroup() + ";" +
                            s.getEnrollmentYear() + ";" +
                            s.getFormOfEducation() + ";" +
                            s.getStudentStatus() + ";" +
                            s.getDepartmentId()
                    );
                    bw.newLine();
                }

                // Teachers
                for (Teacher t : d.getTeachers()) {
                    bw.write("Teacher:" +
                            t.getIdPerson() + ";" +
                            t.getPib() + ";" +
                            t.getBirthDate() + ";" +
                            t.getEmail() + ";" +
                            t.getPhoneNumber() + ";" +
                            t.getTeacherId() + ";" +
                            t.getPosition() + ";" +
                            t.getDepartmentId() + ";" +
                            t.getAcademicDegree() + ";" +
                            t.getAcademicRank() + ";" +
                            t.getHireDate() + ";" +
                            t.getFullTimeEquivalent()
                    );
                    bw.newLine();
                }
            }
        }
    }

    public Optional<List<Department>> loadAll() throws IOException {

        if (!Files.exists(filePath)) return Optional.empty();

        List<Department> departments = new ArrayList<>();
        Department currentDepartment = null;

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();

                if (line.isEmpty()) continue;

                // =====================
                // DEPARTMENT
                // =====================
                if (!line.startsWith("Student:") && !line.startsWith("Teacher:")) {

                    String[] parts = line.split(";", -1);

                    if (parts.length < 5) continue;

                    currentDepartment = new Department(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2]),
                            parts[3],
                            Integer.parseInt(parts[4])
                    );

                    departments.add(currentDepartment);
                }

                // =====================
                // STUDENT
                // =====================
                else if (line.startsWith("Student:")) {

                    String[] parts = line.substring("Student:".length()).split(";", -1);

                    if (parts.length < 12 || currentDepartment == null) continue;

                    Student s = new Student(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            LocalDate.parse(parts[2]),
                            parts[3],
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[5]),
                            Integer.parseInt(parts[6]),
                            Integer.parseInt(parts[7]),
                            Integer.parseInt(parts[8]),
                            parts[9],
                            parts[10],
                            Integer.parseInt(parts[11])
                    );

                    currentDepartment.getStudents().add(s);
                }

                // =====================
                // TEACHER
                // =====================
                else if (line.startsWith("Teacher:")) {

                    String[] parts = line.substring("Teacher:".length()).split(";", -1);

                    if (parts.length < 12 || currentDepartment == null) continue;

                    Teacher t = new Teacher(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            LocalDate.parse(parts[2]),
                            parts[3],
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[5]),
                            parts[6],
                            Integer.parseInt(parts[7]),
                            parts[8],
                            parts[9],
                            parts[10],
                            Double.parseDouble(parts[11])
                    );

                    currentDepartment.getTeachers().add(t);
                }
            }
        }

        // =====================
        // OUTPUT
        // =====================
        for (Department d : departments) {

            System.out.println("Department ID: " + d.getIdDepartment());
            System.out.println("Name: " + d.getDepartmentName());
            System.out.println("Head: " + d.getHeadOfDepartment());
            System.out.println("Room: " + d.getRoomNumber());
            System.out.println("Faculty ID: " + d.getFacultyId());

            System.out.println("Students:");
            for (Student s : d.getStudents()) {
                System.out.println(" - " + s.getPib() + " | " + s.getEmail());
            }

            System.out.println("Teachers:");
            for (Teacher t : d.getTeachers()) {
                System.out.println(" - " + t.getPib() + " | " + t.getPosition());
            }

            System.out.println("----------------------------------");
        }

        return Optional.of(departments);
    }
}