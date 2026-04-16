package Project.Repository;

import Project.Models.University;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class UniversityRepository {

    private final Path filePath = Path.of("data/university.json");
    private final ObjectMapper mapper;

    public UniversityRepository() {

        this.mapper = new ObjectMapper();

        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveUniversity(University university) throws IOException {

        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        mapper.writeValue(filePath.toFile(), university);
    }

    public Optional<University> loadUniversity() throws IOException {

        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        University uni = mapper.readValue(filePath.toFile(), University.class);
        return Optional.of(uni);
    }
}