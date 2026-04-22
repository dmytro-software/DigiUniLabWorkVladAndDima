package Project.Repository;

import Project.Models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Path filePath = Path.of("data/users.json");
    private final ObjectMapper mapper;

    public UserRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String, User> loadUsers() {
        if (!Files.exists(filePath)) {
            return new HashMap<>();
        }

        try {
            return mapper.readValue(filePath.toFile(), new TypeReference<Map<String, User>>() {});
        } catch (IOException e) {
            System.out.println(" ✗ Error reading users.json: " + e.getMessage());
            return new HashMap<>();
        }
    }

    public void saveUsers(Map<String, User> users) {
        try {
            if (Files.notExists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            mapper.writeValue(filePath.toFile(), users);
        } catch (IOException e) {
            System.out.println(" ✗ Error saving users.json: " + e.getMessage());
        }
    }
}
