package com.example.recipebookprojectfinal;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Set;


public class Application {
    private List<Recipe> recipeList;

    private Set<Ingredient> ingredientList;


    public Application(List<Recipe> recipes, Set<Ingredient> ingredients) {
        this.recipeList = recipes;
        this.ingredientList = ingredients;

    }

    public void addRecipe(Recipe recipe) {
        recipeList.add(recipe);

    }


    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    public void checkDuplicateIngredient(Ingredient ingredient) {
        if (ingredientList.contains(ingredient)) {
            throw new DuplicateIngredientException("Duplicate ingredient found: " + ingredient.getIngredientName());
        }
    }



    public void saveRecipesToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Recipe recipe : recipeList) {
                writer.write("Recipe Name: " + recipe.getRecipeName());
                writer.newLine();
                writer.write("Category: " + recipe.getCategory().toString());
                writer.newLine();
                writer.write("Ingredients:");
                writer.newLine();


                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (ingredient != null)
                        writer.write("- " + ingredient.getIngredientName());
                    writer.newLine();
                }
                writer.write("Steps:");
                writer.newLine();

                for (Step step : recipe.getSteps()) {
                    writer.write("- " + step.getDescription());
                    writer.newLine();
                }

                writer.newLine();
            }
            writer.close();
            System.out.println("Recipes added to the file.");
        } catch (IOException e) {
            System.out.println("Error occurred while adding recipes to the file: " + e.getMessage());
        }
    }
    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }







}






