package Project.ui;

import Project.Exceptions.ValidationException;
import Project.Models.Role;
import Project.Models.User;
import Project.service.AuthService;
import Project.validation.UserValidator;
import org.jline.reader.LineReader;

import java.util.List;
import java.util.Map;

import static Project.Models.ConsoleColors.*;


public class UserConsoleHandler {
    private final AuthService authService;

    public UserConsoleHandler(AuthService authService) {
        this.authService = authService;
    }

    public void handleAddUser(LineReader reader) {
        try {
            String newUsername = reader.readLine(YELLOW + " ❯ " + RESET + "New username: ");
            UserValidator.validateUsername(newUsername);

            String newPassword = reader.readLine(YELLOW + " ❯ " + RESET + "New password: ");
            UserValidator.validatePassword(newPassword);

            String roleInput = reader.readLine(YELLOW + " ❯ " + RESET + "Role (USER, MANAGER, ADMIN): ").toUpperCase();
            Role newRole = Role.valueOf(roleInput);
            UserValidator.validateRole(newRole);

            authService.createUser(newUsername, newPassword, newRole);
            System.out.println(GREEN + " ✓ User created successfully." + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + " ✗ Invalid role specified. Use USER, MANAGER, or ADMIN." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ System Error: " + e.getMessage() + RESET);
        }
    }

    public void handleDeleteUser(LineReader reader){
        try {
            String userToDelete = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to delete: ");
            UserValidator.validateUsername(userToDelete);

            authService.deleteUser(userToDelete);
            System.out.println(GREEN + "✓ User " + userToDelete + " has been deleted successfully." + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED +" ✗ System Error: " + e.getMessage() + RESET);
        }
    }

    public void handleBlockUser(LineReader reader) {
        try {
            String userToBlock = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to block: ");
            UserValidator.validateUsername(userToBlock);

            authService.blockUser(userToBlock);
            System.out.println(GREEN + " ✓ User " + userToBlock + " blocked successfully." + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ System Error: " + e.getMessage() + RESET);
        }
    }

    public void handleUnblockUser(LineReader reader) {
        try {
            String userToUnblock = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to unblock: ");
            UserValidator.validateUsername(userToUnblock);

            authService.unblockUser(userToUnblock);
            System.out.println(GREEN + " ✓ User unblocked successfully." + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ System Error: " + e.getMessage() + RESET);
        }
    }

    public void handleEditRole(LineReader reader) {
        try {
            String targetUser = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username: ");
            UserValidator.validateUsername(targetUser);

            String newRoleStr = reader.readLine(YELLOW + " ❯ " + RESET + "Enter new role (USER, MANAGER, ADMIN): ").toUpperCase();
            Role updatedRole = Role.valueOf(newRoleStr);
            UserValidator.validateRole(updatedRole);

            authService.changeUserRole(targetUser, updatedRole);
            System.out.println(GREEN + " ✓ Role updated successfully." + RESET);
        } catch (ValidationException e) {
            System.out.println(RED + " ✗ Validation Error: " + e.getMessage() + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + " ✗ Invalid role specified." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ System Error: " + e.getMessage() + RESET);
        }
    }

    public void handelShowAllUsers(){
        Map<String, User> users = authService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println(YELLOW + " ℹ No users found." + RESET);
            return;
        }

        System.out.println(CYAN_BOLD + "\n==========================================" + RESET);
        System.out.println(CYAN_BOLD + "|               USERS LIST               |" + RESET);
        System.out.println(CYAN_BOLD + "==========================================" + RESET);

        for(User u : users.values()){
            System.out.println(u);
        }
        System.out.println(CYAN_BOLD + "==========================================\n" + RESET);

    }
}
