package Project.Repository;

import Project.Models.Faculty;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacultyRepository {

    private Path filePath = Path.of("data/faculties.txt");

    public void saveAll(List<Faculty> faculties) throws IOException {
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (OutputStream out = Files.newOutputStream(filePath);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(writer)) {

            for (Faculty f : faculties) {
                bw.write(f.getIdFaculty() + ";"
                        + f.getFacultyName() + ";"
                        + f.getFacultyShortName() + ";"
                        + f.getHeadOfFaculty() + ";"
                        + f.getContactsOfFaculty());
                bw.newLine();
            }
        }
    }

    public Optional<List<Faculty>> loadAll() throws IOException {
        if (!Files.exists(filePath)) return Optional.empty();

        List<Faculty> faculties = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", -1);
                if (parts.length == 5) {
                    Faculty f = new Faculty(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4]
                    );
                    faculties.add(f);


                    System.out.println("Faculty ID: " + f.getIdFaculty());
                    System.out.println("Name: " + f.getFacultyName());
                    System.out.println("Short Name: " + f.getFacultyShortName());
                    System.out.println("Head: " + f.getHeadOfFaculty());
                    System.out.println("Contacts: " + f.getContactsOfFaculty());
                    System.out.println("----------------------------------");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(faculties);
    }
}