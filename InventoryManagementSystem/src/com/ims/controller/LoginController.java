package com.ims.controller;

import com.ims.dao.UserDAO;
import com.ims.model.User;
import com.ims.util.AlertHelper;

public class LoginController {

    private UserDAO userDAO = new UserDAO();

    public boolean login(String username, String password) {

        // Form validation
        if (username == null || username.trim().isEmpty()) {
            AlertHelper.showError("Username cannot be empty.");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            AlertHelper.showError("Password cannot be empty.");
            return false;
        }

        // Check credentials against database
        User user = userDAO.getUserByUsername(username.trim());

        if (user == null) {
            AlertHelper.showError("Username does not exist.");
            return false;
        }

        if (!user.getPassword().equals(password)) {
            AlertHelper.showError("Incorrect password.");
            return false;
        }

        return true;
    }
} 