package Project.ui;

import Project.Models.Role;
import Project.Models.User;
import Project.service.AuthService;
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
            String newPassword = reader.readLine(YELLOW + " ❯ " + RESET + "New password: ");
            String roleInput = reader.readLine(YELLOW + " ❯ " + RESET + "Role (USER, MANAGER, ADMIN): ").toUpperCase();

            Role newRole = Role.valueOf(roleInput);
            authService.createUser(newUsername, newPassword, newRole);
            System.out.println(GREEN + " ✓ User created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + " ✗ Invalid role specified. Use USER, MANAGER, or ADMIN." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleDeleteUser(LineReader reader) throws Exception {
        try {
            String userToDelete = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to block: ");
            authService.deleteUser(userToDelete);
            System.out.println(GREEN + "✓ User " + userToDelete + " has been deleted successfully." + RESET);
        } catch (Exception e) {
            System.out.println(RED +"✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleBlockUser(LineReader reader) {
        try {
            String userToBlock = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to block: ");
            authService.blockUser(userToBlock);
            System.out.println(GREEN + " ✓ User "+ userToBlock+ " blocked successfully." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleUnblockUser(LineReader reader) {
        try {
            String userToUnblock = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username to unblock: ");
            authService.unblockUser(userToUnblock);
            System.out.println(GREEN + " ✓ User unblocked successfully." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
        }
    }

    public void handleEditRole(LineReader reader) {
        try {
            String targetUser = reader.readLine(YELLOW + " ❯ " + RESET + "Enter username: ");
            String newRoleStr = reader.readLine(YELLOW + " ❯ " + RESET + "Enter new role (USER, MANAGER, ADMIN): ").toUpperCase();

            Role updatedRole = Role.valueOf(newRoleStr);
            authService.changeUserRole(targetUser, updatedRole);
            System.out.println(GREEN + " ✓ Role updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + " ✗ Invalid role specified." + RESET);
        } catch (Exception e) {
            System.out.println(RED + " ✗ Error: " + e.getMessage() + RESET);
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
