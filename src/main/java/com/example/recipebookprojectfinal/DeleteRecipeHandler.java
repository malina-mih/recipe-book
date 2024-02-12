package com.example.recipebookprojectfinal;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecipeHandler {

    public static void showDeleteConfirmationDialog(RecipeData selectedRecipe, RecipeUI recipeUI) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete the selected recipe?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteRecipe(selectedRecipe, recipeUI);
            }
        });
    }

    private static void deleteRecipe(RecipeData selectedRecipe, RecipeUI recipeUI) {
        try (Connection connection = Database.getConnection()) {
            String deleteQuery = "DELETE FROM Recipes WHERE recipe_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, selectedRecipe.getRecipeId());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Deletion Successful", "Recipe deleted.");
                    recipeUI.loadDataFromDatabase();
                } else {
                    showAlert("Deletion Failed", "Failed to delete recipe.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while deleting the recipe.");
        }
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
