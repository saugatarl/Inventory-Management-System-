package com.ims.view;

import com.ims.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterView {

    private Stage stage;
    private RegisterController registerController = new RegisterController();

    public RegisterView(Stage stage) {
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
        Label welcomeLabel = new Label("Create Account");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        welcomeLabel.setStyle("-fx-text-fill: #1B3A6B;");

        Label subLabel = new Label("Fill in the details to register");
        subLabel.setFont(Font.font("Arial", 13));
        subLabel.setStyle("-fx-text-fill: #777777;");

        Label divider = new Label("─────────────────────────────");
        divider.setStyle("-fx-text-fill: #DDDDDD;");

        // Username
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        usernameLabel.setStyle("-fx-text-fill: #1B3A6B;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Min 3 characters");
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
        passwordField.setPromptText("Min 6 characters");
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

        // Confirm Password
        Label confirmLabel = new Label("Confirm Password");
        confirmLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        confirmLabel.setStyle("-fx-text-fill: #1B3A6B;");

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Re-enter your password");
        confirmField.setPrefHeight(40);
        confirmField.setMaxWidth(Double.MAX_VALUE);
        confirmField.setStyle(
            "-fx-background-color: #F0F4FF;" +
            "-fx-border-color: #C0CCE0;" +
            "-fx-border-radius: 6;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 8px;" +
            "-fx-font-size: 13px;"
        );

        // Register button
        Button registerBtn = new Button("Create Account");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setPrefHeight(42);
        registerBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        registerBtn.setStyle(
            "-fx-background-color: #1B3A6B;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        // Back to login
        Label loginLabel = new Label("Already have an account?");
        loginLabel.setStyle("-fx-text-fill: #777777; -fx-font-size: 13px;");

        Button backBtn = new Button("Login here");
        backBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #1B3A6B;" +
            "-fx-cursor: hand;" +
            "-fx-border-color: transparent;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 13px;"
        );

        HBox backBox = new HBox(5);
        backBox.setAlignment(Pos.CENTER);
        backBox.getChildren().addAll(loginLabel, backBtn);

        // Actions
        registerBtn.setOnAction(e -> {
            boolean success = registerController.register(
                usernameField.getText(),
                passwordField.getText(),
                confirmField.getText()
            );
            if (success) {
                LoginView loginView = new LoginView(stage);
                loginView.show();
            }
        });

        backBtn.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            loginView.show();
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
            confirmLabel,
            confirmField,
            registerBtn,
            backBox
        );

        rightPanel.setPrefWidth(420);

        // ── MAIN LAYOUT ───────────────────────────────────────
        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(leftPanel, rightPanel);

        Scene scene = new Scene(mainLayout, 800, 580);
        stage.setTitle("IMS - Register");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}