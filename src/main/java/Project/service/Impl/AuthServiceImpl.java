package Project.service.Impl;

import Project.Models.Role;
import Project.Models.User;
import Project.Repository.UserRepository;
import Project.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AuthServiceImpl implements AuthService {


    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final Map<String, User> users;

    public AuthServiceImpl() {
        this.userRepository = new UserRepository();
        this.users = userRepository.loadUsers();
        if (users.isEmpty()) {
            users.put("admin", new User("admin", "admin", Role.ADMIN));
            userRepository.saveUsers(users);
            log.info("Default admin account created automatically.");
        }
    }

    @Override
    public User login(String username, String password) throws Exception {
        User user = users.get(username);

        if (user == null || !user.getPassword().equals(password)) {
            log.warn("Failed login attempt for username: {}", username);
            throw new Exception("Invalid username or password.");
        }

        if (user.isBlocked()) {
            log.warn("Login attempt blocked for user: {}", username);
            throw new Exception("Your account is blocked! Please contact the administrator.");
        }

        log.info("User '{}' successfully logged in with role '{}'", username, user.getRole());
        return user;
    }

    @Override
    public void createUser(String username, String password, Role role) throws Exception {
        if (users.containsKey(username)) {
            throw new Exception("A user with this username already exists!");
        }
        users.put(username, new User(username, password, role));
        userRepository.saveUsers(users);
        log.info("Admin created new user: '{}', role: {}", username, role);
    }

    @Override
    public void blockUser(String username) throws Exception {
        User user = users.get(username);
        if (user == null) throw new Exception("User not found!");
        if (user.getRole() == Role.ADMIN) throw new Exception("You cannot block an administrator!");

        user.setBlocked(true);
        userRepository.saveUsers(users);
        log.info("User '{}' has been blocked", username);
    }

    @Override
    public void unblockUser(String username) throws Exception {
        User user = users.get(username);
        if (user == null) throw new Exception("User not found!");

        user.setBlocked(false);
        userRepository.saveUsers(users);
        log.info("User '{}' has been unblocked", username);
    }

    @Override
    public void changeUserRole(String username, Role newRole) throws Exception {
        User user = users.get(username);
        if (user == null) throw new Exception("User not found!");

        user.setRole(newRole);
        userRepository.saveUsers(users);
        log.info("Role of user '{}' changed to {}", username, newRole);
    }
}
