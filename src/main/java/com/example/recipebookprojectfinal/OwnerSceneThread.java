package com.example.recipebookprojectfinal;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OwnerSceneThread extends Thread {
    private RecipeUI recipeUI;
    private Stage primaryStage;

    public OwnerSceneThread(RecipeUI recipeUI, Stage primaryStage) {
        this.recipeUI = recipeUI;
        this.primaryStage = primaryStage;
    }

    @Override
    public void run() {
        recipeUI.loadDataFromDatabase();

        Platform.runLater(() -> {

            TableView<RecipeData> tableView = recipeUI.createTableView();

            Button addButton = new Button("Add");
            addButton.setOnAction(event -> AddRecipeHandler.showAddRecipeDialog(recipeUI));

            Button updateButton = new Button("Update");
            updateButton.setOnAction(event -> {
                RecipeData selectedRecipe = tableView.getSelectionModel().getSelectedItem();
                if (selectedRecipe != null) {
                    UpdateRecipeHandler.showUpdateRecipeDialog(selectedRecipe);
                } else {
                    showAlert("No Recipe Selected", "Please select a recipe to update.");
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                RecipeData selectedRecipe = tableView.getSelectionModel().getSelectedItem();
                if (selectedRecipe != null) {
                    DeleteRecipeHandler.showDeleteConfirmationDialog(selectedRecipe, recipeUI);
                } else {
                    showAlert("No Recipe Selected", "Please select a recipe to delete.");
                }
            });


            Button manageUsersButton = new Button("Manage Users");
            manageUsersButton.setOnAction(event -> UserManagementScene.openUserManagementScene(primaryStage, recipeUI));

            VBox root = new VBox(tableView, addButton, updateButton, deleteButton, manageUsersButton);
            root.setSpacing(10);

            primaryStage.setScene(new Scene(root, 800, 600));
        });
    }

    private void showAlert(String title, String content) {
        ShoppingListHandler.showAlert(title, content);
    }
}
