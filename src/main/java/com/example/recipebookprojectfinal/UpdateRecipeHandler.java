package com.example.recipebookprojectfinal;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateRecipeHandler {

    public static void showUpdateRecipeDialog(RecipeData selectedRecipe) {
        TextInputDialog nameDialog = new TextInputDialog(selectedRecipe.getRecipeName());
        nameDialog.setTitle("Update Recipe");
        nameDialog.setHeaderText("Update Recipe Name");
        nameDialog.setContentText("Enter the new recipe name:");

        ChoiceDialog<String> categoryDialog = new ChoiceDialog<>(selectedRecipe.getCategory(), getAvailableCategories());
        categoryDialog.setTitle("Update Recipe");
        categoryDialog.setHeaderText("Update Recipe Category");
        categoryDialog.setContentText("Choose the new recipe category:");

        nameDialog.showAndWait().ifPresent(newRecipeName -> {
            categoryDialog.showAndWait().ifPresent(newCategory -> {
                try (Connection connection = Database.getConnection()) {
                    String updateQuery = "UPDATE Recipes SET recipe_name = ?, category = ? WHERE recipe_id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                        preparedStatement.setString(1, newRecipeName);
                        preparedStatement.setString(2, newCategory);
                        preparedStatement.setInt(3, selectedRecipe.getRecipeId());

                        int affectedRows = preparedStatement.executeUpdate();
                        if (affectedRows > 0) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Update Successful");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Recipe updated successfully.");
                            successAlert.showAndWait();

                            RecipeUI recipeUI = new RecipeUI();
                            recipeUI.loadDataFromDatabase();
                        } else {
                            showAlert("Update Failed", "Failed to update recipe. Please try again.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Database Error", "An error occurred while updating the recipe.");
                }
            });
        });
    }

    private static List<String> getAvailableCategories() {

        return List.of("Savory", "Sweet", "Breakfast", "Vegetarian", "Vegan");
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


