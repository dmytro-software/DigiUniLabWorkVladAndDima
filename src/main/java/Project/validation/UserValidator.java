package Project.validation;

import Project.Exceptions.ValidationException;
import Project.Models.Role;

public class UserValidator {
      public static void validateUsername(String username) {
            if (username == null || username.trim().isEmpty()) {
                  throw new ValidationException("Username cannot be empty.");
            }
            if (username.length() < 3) {
                  throw new ValidationException("Username must be at least 3 characters long.");
            }
            if (!username.matches("^[a-zA-Z0-9_]+$")) {
                  throw new ValidationException("Username can only contain English letters, numbers, and underscores.");
            }
      }

      public static void validatePassword(String password) {
            if (password == null || password.trim().isEmpty()) {
                  throw new ValidationException("Password cannot be empty.");
            }
            if (password.length() < 3) {
                  throw new ValidationException("Password must be at least 3 characters long.");
            }
      }

      public static void validateRole(Role role) {
            if (role == null) {
                  throw new ValidationException("Role cannot be null.");
            }
      }

}
