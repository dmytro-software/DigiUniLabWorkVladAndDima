package Project.Repository;

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

public class TeacherRepository {
    private Path filePath = Path.of("data/teachers.txt");

    public void saveAll(List<Teacher> teachers) throws IOException {
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (OutputStream out = Files.newOutputStream(filePath);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(writer)) {

            for (Teacher t : teachers) {
                bw.write(
                            t.getIdPerson() + ";"
                                + t.getPib() + ";"
                                + t.getBirthDate() + ";"
                                + t.getEmail() + ";"
                                + t.getPhoneNumber() + ";"
                                + t.getTeacherId() + ";"
                                + t.getPosition() + ";"
                                + t.getDepartmentId() + ";"
                                + t.getAcademicDegree() + ";"
                                + t.getAcademicRank() + ";"
                                + t.getHireDate() + ";"
                                + t.getFullTimeEquivalent()
                );
                bw.newLine();
            }
        }
    }

    public Optional<List<Teacher>> loadAll() throws IOException {
        if (!Files.exists(filePath)) return Optional.empty();

        List<Teacher> teachers = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 12) {
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
                    teachers.add(t);

                    System.out.println("Teacher ID: " + t.getTeacherId());
                    System.out.println("Person ID: " + t.getIdPerson());
                    System.out.println("PIB: " + t.getPib());
                    System.out.println("Birth date: " + t.getBirthDate());
                    System.out.println("Email: " + t.getEmail());
                    System.out.println("Phone: " + t.getPhoneNumber());
                    System.out.println("Position: " + t.getPosition());
                    System.out.println("Department ID: " + t.getDepartmentId());
                    System.out.println("Academic degree: " + t.getAcademicDegree());
                    System.out.println("Academic rank: " + t.getAcademicRank());
                    System.out.println("Hire date: " + t.getHireDate());
                    System.out.println("Full time equivalent: " + t.getFullTimeEquivalent());
                    System.out.println("----------------------------------");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(teachers);
    }
}
