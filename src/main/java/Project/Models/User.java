package Project.Models;

public class User {
    private String username;
    private String password;
    private Role role;
    private boolean isBlocked = false;

    public User() {}

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isBlocked = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {

        String ifBlocked;
        if(isBlocked)
            ifBlocked = "BLOCKED";
        else
            ifBlocked = "ACTIVE";


        return  "| Username: " + username  +
                " | Password: " + password  +
                " | Role: " + role +
                " | Status: " + ifBlocked;
    }
}
