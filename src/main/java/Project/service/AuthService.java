package Project.service;

import Project.Models.Role;
import Project.Models.User;

import java.util.Map;

public interface AuthService {
    User login(String username, String password) throws Exception;

    void createUser(String username, String password, Role role) throws Exception;

    void blockUser(String username) throws Exception;

    void unblockUser(String username) throws Exception;

    void changeUserRole(String username, Role newRole) throws Exception;

    Map<String ,User> getAllUsers();

    void deleteUser(String username) throws Exception;
}