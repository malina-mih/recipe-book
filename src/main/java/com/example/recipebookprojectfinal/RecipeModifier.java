package com.example.recipebookprojectfinal;

public interface RecipeModifier {
    void addIngredient(Ingredient ingredient);
    void removeIngredient(Ingredient ingredient);
    void addStep(Step step);
    void removeStep(Step step);
}

