package Project.Repository;

import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.University;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

public class UniversityRepository {

    private final Path filePath = Path.of("data/university.txt");

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;

    public UniversityRepository(FacultyRepository facultyRepository,
                                DepartmentRepository departmentRepository) {
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
    }

    public void saveUniversity(University university) throws IOException {
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {


            bw.write("University: " + university.universityName() + ";" +
                    university.universityShortName() + ";" +
                    university.city() + ";" +
                    university.universityAddress());
            bw.newLine();

            for (Faculty f : university.faculties()) {

                bw.write("Faculty:" + f.getIdFaculty() + ";" +
                        f.getFacultyName() + ";" +
                        f.getFacultyShortName() + ";" +
                        f.getHeadOfFaculty() + ";" +
                        f.getContactsOfFaculty());
                bw.newLine();

                for (Department d : f.getDepartments()) {
                    bw.write("Department:" +
                            d.getIdDepartment() + ";" +
                            d.getDepartmentName() + ";" +
                            f.getIdFaculty() + ";" +
                            d.getHeadOfDepartment() + ";" +
                            d.getRoomNumber());
                    bw.newLine();
                }
            }
        }
    }

    public Optional<University> loadUniversity() throws IOException {
        if (!Files.exists(filePath)) return Optional.empty();

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            University university = null;
            Faculty currentFaculty = null;

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts;
                if (line.startsWith("University:")) {
                    parts = line.substring("University:".length()).split(";", -1);
                    university = new University(parts[0], parts[1], parts[2], parts[3]);
                } else if (line.startsWith("Faculty:")) {
                    parts = line.substring("Faculty:".length()).split(";", -1);
                    currentFaculty = new Faculty(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4]
                    );
                    if (university != null) {
                        university.faculties().add(currentFaculty);
                    }
                } else if (line.startsWith("Department:")) {
                    parts = line.substring("Department:".length()).split(";", -1);
                    Department dept = new Department(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2]), // facultyId чи headOfDepartment залежно від твоєї моделі
                            parts[3],
                            Integer.parseInt(parts[4])
                    );
                    if (currentFaculty != null) {
                        currentFaculty.getDepartments().add(dept);
                    }
                }
            }

            return Optional.ofNullable(university);
        }
    }
}
