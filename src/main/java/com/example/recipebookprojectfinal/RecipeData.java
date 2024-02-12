package com.example.recipebookprojectfinal;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecipeData {
    private final SimpleIntegerProperty recipeId;
    private final StringProperty recipeName;
    private final StringProperty category;
    private final StringProperty ingredients;
    private final StringProperty steps;

    public RecipeData(int recipeId, String recipeName, String category, String ingredients, String steps) {
        this.recipeId = new SimpleIntegerProperty(recipeId);
        this.recipeName = new SimpleStringProperty(recipeName);
        this.category = new SimpleStringProperty(category);
        this.ingredients = new SimpleStringProperty(ingredients);
        this.steps = new SimpleStringProperty(steps);
    }

    public void setRecipeId(int recipeId) {
        this.recipeId.set(recipeId);
    }

    public int getRecipeId() {
        return recipeId.get();
    }

    public SimpleIntegerProperty recipeIdProperty() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName.get();
    }

    public StringProperty recipeNameProperty() {
        return recipeName;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getIngredients() {
        return ingredients.get();
    }



    public String getSteps() {
        return steps.get();
    }

    public StringProperty stepsProperty() {
        return steps;
    }

}
