package com.ims.view;

import com.ims.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {

    private Stage stage;
    private LoginController loginController = new LoginController();

    public LoginView(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // ── LEFT PANEL ────────────────────────────────────────
        Label appTitle = new Label("IMS");
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        appTitle.setStyle("-fx-text-fill: white;");

        Label appFull = new Label("Inventory Management");
        appFull.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        appFull.setStyle("-fx-text-fill: white;");

        Label appSub = new Label("System");
        appSub.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        appSub.setStyle("-fx-text-fill: white;");

        Label tagline = new Label("Track. Manage. Succeed.");
        tagline.setFont(Font.font("Arial", 14));
        tagline.setStyle("-fx-text-fill: #A8C4E0;");

        VBox leftPanel = new VBox(10);
        leftPanel.setAlignment(Pos.CENTER);
        leftPanel.setPrefWidth(380);
        leftPanel.setPadding(new Insets(60));
        leftPanel.setStyle("-fx-background-color: #1B3A6B;");
        leftPanel.getChildren().addAll(appTitle, appFull, appSub, tagline);

        // ── RIGHT PANEL ───────────────────────────────────────
        Label welcomeLabel = new Label("Welcome Back");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        welcomeLabel.setStyle("-fx-text-fill: #1B3A6B;");

        Label subLabel = new Label("Please login to your account");
        subLabel.setFont(Font.font("Arial", 13));
        subLabel.setStyle("-fx-text-fill: #777777;");

        // Username
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        usernameLabel.setStyle("-fx-text-fill: #1B3A6B;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(40);
        usernameField.setMaxWidth(Double.MAX_VALUE);
        usernameField.setStyle(
            "-fx-background-color: #F0F4FF;" +
            "-fx-border-color: #C0CCE0;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8px;" +
            "-fx-font-size: 13px;"
        );

        // Password
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        passwordLabel.setStyle("-fx-text-fill: #1B3A6B;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setStyle(
            "-fx-background-color: #F0F4FF;" +
            "-fx-border-color: #C0CCE0;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8px;" +
            "-fx-font-size: 13px;"
        );

        // Login button
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(42);
        loginBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        loginBtn.setStyle(
            "-fx-background-color: #1B3A6B;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        // Register link
        Label registerLabel = new Label("Don't have an account?");
        registerLabel.setStyle("-fx-text-fill: #777777; -fx-font-size: 13px;");

        Button registerBtn = new Button("Register here");
        registerBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1B3A6B;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: transparent;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;"
        );

        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.getChildren().addAll(registerLabel, registerBtn);

        // Divider line
        Label divider = new Label("─────────────────────────────");
        divider.setStyle("-fx-text-fill: #DDDDDD;");

        // Actions
        loginBtn.setOnAction(e -> {
            boolean success = loginController.login(
                usernameField.getText(),
                passwordField.getText()
            );
            if (success) {
                DashboardView dashboard = new DashboardView(stage);
                dashboard.show();
            }
        });

        registerBtn.setOnAction(e -> {
            RegisterView registerView = new RegisterView(stage);
            registerView.show();
        });

        // Right panel layout
        VBox rightPanel = new VBox(14);
        rightPanel.setAlignment(Pos.CENTER_LEFT);
        rightPanel.setPadding(new Insets(60, 60, 60, 60));
        rightPanel.setStyle("-fx-background-color: white;");
        rightPanel.getChildren().addAll(
            welcomeLabel,
            subLabel,
            divider,
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginBtn,
            registerBox
        );

        // ── MAIN LAYOUT ───────────────────────────────────────
        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(leftPanel, rightPanel);

        // Make right panel fill remaining space
        rightPanel.setPrefWidth(420);

        Scene scene = new Scene(mainLayout, 800, 520);
        stage.setTitle("IMS - Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}