package Project.service.Impl;

import Project.service.AuthService;

public class AuthServiceImpl implements AuthService {

    @Override
    public String authorize(String login, String password) {
        if (login.equals("manager") && password.equals("123")) {
            return "manager";
        }
        if (login.equals("user") && password.equals("123")) {
            return "user";
        }
        if (login.equals("admin") && password.equals("123")) {
            return "admin";
        }
      return null;
    }
}
