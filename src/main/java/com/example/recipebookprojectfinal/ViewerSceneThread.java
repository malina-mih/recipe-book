package com.example.recipebookprojectfinal;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ViewerSceneThread extends Thread {
    private RecipeUI recipeUI;
    private Stage primaryStage;

    public ViewerSceneThread(RecipeUI recipeUI, Stage primaryStage) {
        this.recipeUI = recipeUI;
        this.primaryStage = primaryStage;
    }

    @Override
    public void run() {
        recipeUI.loadDataFromDatabase();

        Platform.runLater(() -> {
            TableView<RecipeData> tableView = recipeUI.createTableView();

            Button addToShoppingListButton = new Button("Add to Shopping List");
            addToShoppingListButton.setOnAction(event -> {
                RecipeData selectedRecipe = tableView.getSelectionModel().getSelectedItem();
                if (selectedRecipe != null) {
                    boolean success = ShoppingListHandler.addToShoppingList(selectedRecipe);
                    if (success) {
                        showConfirmationAlert("Added to Shopping List", "Ingredients added to shopping list successfully.");
                    } else {
                        showAlert("Already in Shopping List", "Selected recipe ingredients are already in the shopping list.");
                    }
                } else {
                    showAlert("No Recipe Selected", "Please select a recipe to add to the shopping list.");
                }
            });

            Button viewShoppingListButton = new Button("View Shopping List");
            viewShoppingListButton.setOnAction(event -> {
                ShoppingListHandler.openShoppingListScene(primaryStage, recipeUI);
            });

            VBox root = new VBox(tableView, addToShoppingListButton, viewShoppingListButton);
            root.setSpacing(10);

            primaryStage.setScene(new Scene(root, 800, 600));
        });
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

