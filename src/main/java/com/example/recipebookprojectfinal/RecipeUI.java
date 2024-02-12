package com.example.recipebookprojectfinal;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.example.recipebookprojectfinal.UpdateRecipeHandler.showAlert;

public class RecipeUI extends Application {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static RecipeUI instance;


    public ObservableList<RecipeData> getData() {
        return data;
    }


    private final ObservableList<RecipeData> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        primaryStage.setTitle("Recipe Database");


        ChoiceBox<String> roleChoiceBox = new ChoiceBox<>();
        roleChoiceBox.getItems().addAll("Owner", "Editor", "Viewer");
        roleChoiceBox.setValue("Viewer"); // Default to Viewer
        Button selectRoleButton = new Button("Select Role");
        selectRoleButton.setOnAction(event -> {
            String selectedRole = roleChoiceBox.getValue();
            switch (selectedRole) {
                case "Owner":
                    openOwnerScene(primaryStage);
                    break;
                case "Editor":
                    openEditorScene(primaryStage);
                    break;
                case "Viewer":
                    openViewerScene(primaryStage);
                    break;
                default:
                    showAlert("Invalid Role", "Please select a valid role.");
            }
        });

        VBox roleSelectionRoot = new VBox(roleChoiceBox, selectRoleButton);
        Scene roleSelectionScene = new Scene(roleSelectionRoot, 400, 200);

        primaryStage.setScene(roleSelectionScene);
        primaryStage.show();
    }

    private void openOwnerScene(Stage primaryStage) {
        TextInputDialog loginDialog = new TextInputDialog();
        loginDialog.setTitle("Login");
        loginDialog.setHeaderText("Enter your credentials for the Owner role");
        loginDialog.setContentText("Username:");

        Optional<String> result = loginDialog.showAndWait();

        result.ifPresent(username -> {
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Password");
            passwordDialog.setHeaderText("Enter your password for the Owner role");
            passwordDialog.setContentText("Password:");

            Optional<String> passwordResult = passwordDialog.showAndWait();

            passwordResult.ifPresent(password -> {
                boolean validCredentials = checkUserCredentials(username, password, "Owner");
                if (validCredentials) {
                    new OwnerSceneThread(this, primaryStage).start();
                } else {
                    showAlert("Invalid Credentials", "Username or password is incorrect for the Owner role.");
                }
            });
        });
    }



    private void openEditorScene(Stage primaryStage) {
        TextInputDialog loginDialog = new TextInputDialog();
        loginDialog.setTitle("Login");
        loginDialog.setHeaderText("Enter your credentials for the Editor role");
        loginDialog.setContentText("Username:");

        Optional<String> result = loginDialog.showAndWait();

        result.ifPresent(username -> {
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Password");
            passwordDialog.setHeaderText("Enter your password for the Editor role");
            passwordDialog.setContentText("Password:");

            Optional<String> passwordResult = passwordDialog.showAndWait();

            passwordResult.ifPresent(password -> {
                boolean validCredentials = checkUserCredentials(username, password, "Editor");
                if (validCredentials) {
                    new EditorSceneThread(this, primaryStage).start();
                } else {
                    showAlert("Invalid Credentials", "Username or password incorrect for Editor role.");
                }
            });
        });
    }
    void openViewerScene(Stage primaryStage) {
        new ViewerSceneThread(this, primaryStage).start();
    }
    private static boolean checkUserCredentials(String userName, String password, String role) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM Users WHERE user_name = ? AND role = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, role);
                preparedStatement.setString(3, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






    TableView<RecipeData> createTableView() {

        TableView<RecipeData> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<RecipeData, Integer> idColumn = new TableColumn<>("Recipe ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("recipeId"));

        TableColumn<RecipeData, String> nameColumn = new TableColumn<>("Recipe Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("recipeName"));

        TableColumn<RecipeData, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<RecipeData, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        TableColumn<RecipeData, String> stepsColumn = new TableColumn<>("Steps");
        stepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));

        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, ingredientsColumn, stepsColumn);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        });

        tableView.setItems(data);

        return tableView;
    }

    void loadDataFromDatabase() {
        data.clear();

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT r.recipe_id, r.recipe_name, r.category, " +
                    "GROUP_CONCAT(DISTINCT i.ingredient_name) AS ingredients, " +
                    "GROUP_CONCAT(DISTINCT s.description ORDER BY rs.step_id SEPARATOR ' ') AS steps " +
                    "FROM Recipes r " +
                    "JOIN RecipeIngredients ri ON r.recipe_id = ri.recipe_id " +
                    "JOIN Ingredients i ON ri.ingredient_id = i.ingredient_id " +
                    "JOIN RecipeSteps rs ON r.recipe_id = rs.recipe_id " +
                    "JOIN Steps s ON rs.step_id = s.step_id " +
                    "GROUP BY r.recipe_id";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int recipeId = resultSet.getInt("recipe_id");
                String recipeName = resultSet.getString("recipe_name");
                String category = resultSet.getString("category");
                String ingredients = resultSet.getString("ingredients");
                String steps = resultSet.getString("steps");

                data.add(new RecipeData(recipeId, recipeName, category, ingredients, steps));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        executorService.shutdown();

    }
}


