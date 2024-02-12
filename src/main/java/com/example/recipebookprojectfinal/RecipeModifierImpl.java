package com.example.recipebookprojectfinal;

public class RecipeModifierImpl implements RecipeModifier {
    private Recipe recipe;

    public RecipeModifierImpl(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        recipe.addIngredient(ingredient);
    }

    @Override
    public void removeIngredient(Ingredient ingredient) {
        recipe.removeIngredient(ingredient);
    }

    @Override
    public void addStep(Step step) {
        recipe.addStep(step);
    }

    @Override
    public void removeStep(Step step) {
        recipe.removeStep(step);
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
