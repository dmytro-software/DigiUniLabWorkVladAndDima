package Project.Repository;

import Project.Models.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    private Path filePath = Path.of("data/students.txt");

    public void saveAll(List<Student> students) throws IOException {
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (OutputStream out = Files.newOutputStream(filePath);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(writer)) {

            for (Student s : students) {
                bw.write(
                        s.getIdPerson() + ";"
                                + s.getPib() + ";"
                                + s.getBirthDate() + ";"
                                + s.getEmail() + ";"
                                + s.getPhoneNumber() + ";"
                                + s.getGradeBookId() + ";"
                                + s.getCourse() + ";"
                                + s.getGroup() + ";"
                                + s.getEnrollmentYear() + ";"
                                + s.getFormOfEducation() + ";"
                                + s.getStudentStatus() + ";"
                                + s.getDepartmentId()
                );
                bw.newLine();
            }
        }
    }

    public Optional<List<Student>> loadAll() throws IOException {
        if (!Files.exists(filePath)) return Optional.empty();

        List<Student> students = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 12) {
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
                    students.add(s);

                    System.out.println("Student ID: " + s.getIdPerson());
                    System.out.println("PIB: " + s.getPib());
                    System.out.println("Birth date: " + s.getBirthDate());
                    System.out.println("Email: " + s.getEmail());
                    System.out.println("Phone: " + s.getPhoneNumber());
                    System.out.println("GradeBook ID: " + s.getGradeBookId());
                    System.out.println("Course: " + s.getCourse());
                    System.out.println("Group: " + s.getGroup());
                    System.out.println("Enrollment year: " + s.getEnrollmentYear());
                    System.out.println("Form of education: " + s.getFormOfEducation());
                    System.out.println("Student status: " + s.getStudentStatus());
                    System.out.println("Department ID: " + s.getDepartmentId());
                    System.out.println("----------------------------------");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(students);
    }
}