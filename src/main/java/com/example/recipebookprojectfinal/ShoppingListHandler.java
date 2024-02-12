package com.example.recipebookprojectfinal;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ShoppingListHandler {

    public static boolean addToShoppingList(RecipeData recipe) {
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {

            String insertQuery = "INSERT INTO ShoppingList (ingredient_id, ingredient_name) " +
                    "SELECT DISTINCT ri.ingredient_id, i.ingredient_name " +
                    "FROM RecipeIngredients ri " +
                    "JOIN Ingredients i ON ri.ingredient_id = i.ingredient_id " +
                    "WHERE ri.recipe_id = " + recipe.getRecipeId();

            int rowsAffected = statement.executeUpdate(insertQuery);

            return rowsAffected > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void openShoppingListScene(Stage primaryStage, RecipeUI recipeUI) {
        ListView<String> shoppingListView = new ListView<>();
        loadShoppingListDataAsync(shoppingListView);

        Button backButton = new Button("Back to Recipes");
        backButton.setOnAction(event -> {
            recipeUI.openViewerScene(primaryStage);
        });

        VBox root = new VBox(shoppingListView, backButton);
        root.setSpacing(10);

        primaryStage.setScene(new Scene(root, 400, 300));
    }

    private static void loadDataFromDatabase(ListView<String> shoppingListView) {
        shoppingListView.getItems().clear();

        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT ingredient_id, ingredient_name FROM ShoppingList";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredient_name");


                String displayText =  ingredientName;

                shoppingListView.getItems().add(displayText);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void loadShoppingListDataAsync(ListView<String> shoppingListView) {
        new Thread(() -> {
            loadDataFromDatabase(shoppingListView);
            Platform.runLater(() -> {

            });
        }).start();
    }




    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
