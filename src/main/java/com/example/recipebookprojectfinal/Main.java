package com.example.recipebookprojectfinal;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private static boolean recipeNameExists(List<Recipe> recipes, String name) {

        return recipes.stream().anyMatch(recipe -> recipe.getRecipeName().equalsIgnoreCase(name));}

    private static boolean isValidString(String input) {
        return input.matches("^[a-zA-Z ]+$");
    }

    public static void main(String[] args) throws EmptyRecipeException {


        try (Connection connection = Database.getConnection()) {
            String query = "SELECT recipe_name, category FROM Recipes";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String recipeName = resultSet.getString("recipe_name");
                    String category = resultSet.getString("category");

                    System.out.println("Recipe Name: " + recipeName + ", Category: " + category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (args.length != 2) {
            System.out.println("Usage: java Main <recipes_filename> <shoppingList_filename>");
            System.exit(1);
        }

        String recipesFileName = args[0];
        String shoppingListFileName = args[1];

        Scanner scanner = new Scanner(System.in);
        List<Recipe> recipes = new ArrayList<>();
        Application application = new Application(recipes, new HashSet<>());
        ShoppingListGenerator shoppingListGenerator = new ShoppingListGeneratorImpl(new Ingredient[0]);




        Recipe.RecipeCategory category = null;

        System.out.println("Enter recipes (type 'done' when finished):");
        while (true) {
            System.out.print("Recipe name: ");
            String recipeName = scanner.nextLine();
            if (recipeName.equalsIgnoreCase("done")) {
                break;
            }
            while (recipeNameExists(recipes, recipeName)) {
                System.out.println("Recipe with this name already exists. Please enter a unique name.");
                System.out.print("Recipe name: ");
                recipeName = scanner.nextLine();
            }

            boolean validCategory = false;
            while (!validCategory) {
                System.out.print("Recipe category (VEGAN, VEGETARIAN, BREAKFAST, SAVORY, SWEET): ");
                String categoryInput = scanner.nextLine().toUpperCase();

                try {
                    category = Recipe.RecipeCategory.valueOf(categoryInput);
                    validCategory = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid category. Please choose from VEGAN, VEGETARIAN, BREAKFAST, SAVORY, SWEET.");
                }
            }

            Set<Ingredient> recipeIngredients = new HashSet<>();
            System.out.println("Enter recipe ingredients (type 'done' when finished):");
            while (true) {
                String ingredientName = scanner.nextLine();
                while (!isValidString(ingredientName)) {
                    System.out.println("Invalid ingredient name. Please enter a valid string.");
                    ingredientName = scanner.nextLine();
                }
                if (ingredientName.equalsIgnoreCase("done")) {
                    break;
                }

                Ingredient newIngredient = new Ingredient(ingredientName);

                try {
                    application.checkDuplicateIngredient(newIngredient);
                    recipeIngredients.add(newIngredient);
                    application.addIngredient(newIngredient);
                } catch (DuplicateIngredientException e) {
                    System.out.println(e.getMessage());
                }

                recipeIngredients.add(newIngredient);
                application.addIngredient(newIngredient);
            }

            System.out.println("Enter recipe steps (type 'done' when finished):");
            List<Step> steps = new ArrayList<>();
            while (true) {
                String stepDescription = scanner.nextLine();
                while (!isValidString(stepDescription)) {
                    System.out.println("Invalid step. Please enter a valid string.");
                    stepDescription = scanner.nextLine();
                }
                if (stepDescription.equalsIgnoreCase("done")) {
                    break;
                }
                steps.add(new Step(stepDescription));
            }
            if (recipeIngredients.isEmpty() || steps.isEmpty()) {
                throw new EmptyRecipeException();
            }
            shoppingListGenerator.addRecipeIngredientsToShoppingList(recipeIngredients);


            Recipe recipe = new Recipe(recipeName, category, recipeIngredients.toArray(new Ingredient[0]), steps.toArray(new Step[0]));
            application.addRecipe(recipe);
        }



        shoppingListGenerator.saveShoppingListToFile(shoppingListFileName);
        application.saveRecipesToFile(recipesFileName);


        scanner.close();
    }




}

