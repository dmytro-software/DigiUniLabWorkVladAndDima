package Project.service;

import Project.Models.Role;
import Project.Models.User;

public interface AuthService {
    User login(String username, String password) throws Exception;

    void createUser(String username, String password, Role role) throws Exception;

    void blockUser(String username) throws Exception;

    void unblockUser(String username) throws Exception;

    void changeUserRole(String username, Role newRole) throws Exception;
}