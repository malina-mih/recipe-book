package com.example.recipebookprojectfinal;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddRecipeHandler {

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void showAddRecipeDialog(RecipeUI recipeUI) {
        Dialog<RecipeData> dialog = new Dialog<>();
        dialog.setTitle("Add Recipe");

        Label idLabel = new Label("Recipe Id");
        TextField idTextField = new TextField();

        Label nameLabel = new Label("Recipe Name:");
        TextField nameTextField = new TextField();

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("VEGAN", "VEGETARIAN", "BREAKFAST", "SAVORY", "SWEET");

        Label ingredientsLabel = new Label("Ingredients (comma-separated):");
        TextField ingredientsTextField = new TextField();

        Label stepsLabel = new Label("Steps (each step on a new line):");
        TextArea stepsTextArea = new TextArea();

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(new StackPane(new VBox(idLabel, idTextField, nameLabel, nameTextField, categoryLabel, categoryComboBox,
                ingredientsLabel, ingredientsTextField, stepsLabel, stepsTextArea)));


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String idText = idTextField.getText();
                String recipeName = nameTextField.getText();
                String category = categoryComboBox.getValue();
                String ingredients = ingredientsTextField.getText();
                String steps = stepsTextArea.getText();


                if (idText.isEmpty() || recipeName.isEmpty() || category == null || ingredients.isEmpty() || steps.isEmpty()) {
                    showAlert("Invalid input" , "Please make sure everything is filled out correctly");
                    return null;
                }

                int recipeId;
                try {
                    recipeId = Integer.parseInt(idText);
                } catch (NumberFormatException e) {
                    showAlert("Invalid ID", "Please enter a valid numeric ID");
                    return null;
                }

                if (recipeIdExists(recipeId)) {
                    showAlert("ID already exists", "Please choose a unique ID");
                    return null;
                }

                List<String> ingredientsList = Arrays.asList(ingredients.split(","));
                Set<String> ingredientsSet = new HashSet<>(ingredientsList);
                if (ingredientsList.size() != ingredientsSet.size()) {
                    showAlert("Duplicate Ingredients", "Please ensure ingredients are unique within the recipe.");
                    return null;
                }
                RecipeData recipeData = new RecipeData(recipeId, recipeName, category, ingredients, steps);
                executorService.submit(() -> {
                    insertRecipeIntoDatabase(recipeData, recipeUI);
                    Platform.runLater(() -> {
                        recipeUI.getData().add(recipeData);
                    });
                });

                return recipeData;

            }
            return null;
        });

        dialog.showAndWait();
    }



    private static int insertRecipeIntoDatabase(RecipeData recipeData, RecipeUI recipeUI) {

        try (Connection connection = Database.getConnection()) {

            String insertRecipeQuery = "INSERT INTO Recipes (recipe_id, recipe_name, category) VALUES (?, ?, ?)";
            try (PreparedStatement recipeStatement = connection.prepareStatement(insertRecipeQuery, Statement.RETURN_GENERATED_KEYS)) {
                recipeStatement.setInt(1, recipeData.getRecipeId());
                recipeStatement.setString(2, recipeData.getRecipeName());
                recipeStatement.setString(3, recipeData.getCategory());
                recipeStatement.executeUpdate();

                Platform.runLater(() -> {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Recipe Added Successfully");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("The recipe has been added successfully.");
                    successAlert.showAndWait();
                });





            }

            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    private static boolean recipeIdExists(int recipeId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM Recipes WHERE recipe_id = ?")) {
            statement.setInt(1, recipeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}