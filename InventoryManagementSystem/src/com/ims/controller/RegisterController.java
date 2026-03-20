package com.ims.controller;

import com.ims.dao.UserDAO;
import com.ims.model.User;
import com.ims.util.AlertHelper;

public class RegisterController {

    private UserDAO userDAO = new UserDAO();

    public boolean register(String username, String password, String confirmPassword) {

        // Form validation
        if (username == null || username.trim().isEmpty()) {
            AlertHelper.showError("Username cannot be empty.");
            return false;
        }

        if (username.trim().length() < 3) {
            AlertHelper.showError("Username must be at least 3 characters.");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            AlertHelper.showError("Password cannot be empty.");
            return false;
        }

        if (password.length() < 6) {
            AlertHelper.showError("Password must be at least 6 characters.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            AlertHelper.showError("Passwords do not match.");
            return false;
        }

        // Check if username already taken
        if (userDAO.isUsernameTaken(username.trim())) {
            AlertHelper.showError("Username already exists. Please choose another.");
            return false;
        }

        // Create new user and save to database
        User newUser = new User(0, username.trim(), password, "ADMIN");
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            AlertHelper.showSuccess("Account created successfully! Please login.");
            return true;
        } else {
            AlertHelper.showError("Registration failed. Please try again.");
            return false;
        }
    }
}