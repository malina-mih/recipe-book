package com.example.recipebookprojectfinal;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManagementScene {

    public static void openUserManagementScene(Stage primaryStage, RecipeUI recipeUI) {

        Button addUserButton = new Button("Add User");
        addUserButton.setOnAction(event -> promptForNewUser(primaryStage, recipeUI));

        Button deleteUserButton = new Button("Delete User");
        deleteUserButton.setOnAction(event -> promptForDeleteUser(primaryStage, recipeUI));

        VBox root = new VBox( addUserButton, deleteUserButton);
        root.setSpacing(10);

        primaryStage.setScene(new Scene(root, 400, 200));
    }

    private static void promptForNewUser(Stage primaryStage, RecipeUI recipeUI) {
        Stage addUserStage = new Stage();
        addUserStage.setTitle("Add New User");

        Label nameLabel = new Label("User Name:");
        TextField nameTextField = new TextField();

        Label roleLabel = new Label("Role:");
        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Owner", "Editor");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button addButton = new Button("Add User");
        addButton.setOnAction(event -> {
            String userName = nameTextField.getText();
            String role = roleChoiceBox.getValue();
            String password = passwordField.getText();

            if (!userName.isEmpty() && role != null && !password.isEmpty()) {

                boolean success = insertUserIntoDatabase(userName, role, password);
                if (success) {
                    recipeUI.loadDataFromDatabase();
                }

                addUserStage.close();
            } else {
                showAlert("Invalid Input", "Please enter username, role, password.");
            }
        });

        VBox root = new VBox(nameLabel, nameTextField, roleLabel, roleChoiceBox, passwordLabel, passwordField, addButton);
        root.setSpacing(10);
        Scene scene = new Scene(root, 400, 400);

        addUserStage.setScene(scene);
        addUserStage.show();
    }

    private static void promptForDeleteUser(Stage primaryStage, RecipeUI recipeUI) {
        Stage deleteUserStage = new Stage();
        deleteUserStage.setTitle("Delete User");

        Label nameLabel = new Label("User Name:");
        TextField nameTextField = new TextField();

        Button deleteButton = new Button("Delete User");
        deleteButton.setOnAction(event -> {
            String userName = nameTextField.getText();
            if (!userName.isEmpty()) {

                boolean success = deleteUserFromDatabase(userName);
                if (success) {
                    System.out.println("User deleted successfully: " + userName);
                    recipeUI.loadDataFromDatabase();
                } else {
                    System.out.println("Failed to delete user.");
                }

                deleteUserStage.close();
            } else {
                showAlert("Invalid Input", "Please enter the username to delete.");
            }
        });

        VBox root = new VBox(nameLabel, nameTextField, deleteButton);
        root.setSpacing(10);
        Scene scene = new Scene(root, 250, 100);

        deleteUserStage.setScene(scene);
        deleteUserStage.show();
    }

    private static boolean insertUserIntoDatabase(String userName, String role, String password) {
        try (Connection connection = Database.getConnection()) {
            String insertUserQuery = "INSERT INTO Users (user_name, role, password) VALUES (?, ?, ?)";
            try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery)) {
                userStatement.setString(1, userName);
                userStatement.setString(2, role);
                userStatement.setString(3, password);
                userStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean deleteUserFromDatabase(String userName) {
        try (Connection connection = Database.getConnection()) {
            String deleteUserQuery = "DELETE FROM Users WHERE user_name = ?";
            try (PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserQuery)) {
                deleteUserStatement.setString(1, userName);
                int rowsDeleted = deleteUserStatement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void showAlert(String title, String content) {}


}
