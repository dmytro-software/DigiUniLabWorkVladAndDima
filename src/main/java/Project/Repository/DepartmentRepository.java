package Project.Repository;

import Project.Models.Department;
import Project.Models.Faculty;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository {
    private static Path filePath = Path.of("data/departments.txt");

    public void saveAll(List<Department> departments) throws IOException {
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (OutputStream out = Files.newOutputStream(filePath);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(writer)) {

            for (Department d : departments) {
                bw.write(d.getIdDepartment() + ";"
                        + d.getDepartmentName() + ";"
                        + d.getFacultyId() + ";"
                        + d.getHeadOfDepartment() + ";"
                        + d.getRoomNumber());
                bw.newLine();
            }
        }
    }

    public Optional<List<Department>> loadAll() throws IOException {

        if (!Files.exists(filePath)) return Optional.empty();
        List<Department> departments = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 5) {
                    Department d = new Department(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2]),
                            parts[3],
                            Integer.parseInt(parts[4])
                    );
                    departments.add(d);

                    System.out.println("Department ID: " + d.getIdDepartment());
                    System.out.println("Name: " + d.getDepartmentName());
                    System.out.println("Head: " + d.getHeadOfDepartment());
                    System.out.println("Room: " + d.getRoomNumber());
                    System.out.println("Faculty ID: " + d.getFacultyId());
                    System.out.println("----------------------------------");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(departments);
    }
}
