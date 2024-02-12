package com.example.recipebookprojectfinal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class Recipe {
    private String recipeName;
    private RecipeCategory category;
    private Set<Ingredient> ingredients;
    private List<Step> steps;


    public enum RecipeCategory {
        VEGAN, VEGETARIAN, BREAKFAST, SAVORY, SWEET
    }

    public Recipe(String recipeName, RecipeCategory category, Ingredient[] ingredients, Step[] steps) {
        this.recipeName = recipeName;
        this.category = category;
        this.ingredients = new HashSet<>(List.of(ingredients));
        this.steps = new ArrayList<>(List.of(steps));

    }

    public String getRecipeName() {
        return recipeName;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void removeStep(Step step) {
        steps.remove(step);
    }
}
