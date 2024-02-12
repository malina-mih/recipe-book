package com.example.recipebookprojectfinal;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class ConstructorTest {

    @Test
    public void testRecipeConstructor() {
        Ingredient[] ingredients = {new Ingredient("Ingredient1"), new Ingredient("Ingredient2")};
        Step[] steps = {new Step("Step1"), new Step("Step2")};

        Recipe recipe = new Recipe("TestRecipe", Recipe.RecipeCategory.VEGAN, ingredients, steps);

        assertEquals("TestRecipe", recipe.getRecipeName());
        assertEquals(Recipe.RecipeCategory.VEGAN, recipe.getCategory());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getSteps().size());
    }

    @Test
    public void testIngredientConstructor() {
        Ingredient ingredient = new Ingredient("TestIngredient");
        assertEquals("TestIngredient", ingredient.getIngredientName());
    }

    @Test
    public void testStepConstructor() {
        Step step = new Step("TestStep");
        assertEquals("TestStep", step.getDescription());
    }

    @Test
    public void testApplicationConstructor() {
        List<Recipe> recipes = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Application application = new Application(recipes, ingredients);
        assertEquals(recipes, application.getRecipeList());
        assertEquals(ingredients, application.getIngredientList());
    }

    @Test
    public void testShoppingListConstructor() {
        Ingredient[] existingIngredients = {new Ingredient("Existing1"), new Ingredient("Existing2")};
        ShoppingListGeneratorImpl shoppingListGenerator = new ShoppingListGeneratorImpl(existingIngredients);
        assertTrue(shoppingListGenerator.getShoppingList().containsAll(Arrays.asList(existingIngredients)));
    }

    @Test
    public void testRecipeModifier() {
        Recipe recipe = new Recipe("TestRecipe", Recipe.RecipeCategory.VEGAN, new Ingredient[0], new Step[0]);
        RecipeModifierImpl recipeModifier = new RecipeModifierImpl(recipe);

        assertEquals(recipe, recipeModifier.getRecipe());
    }
    @Test
    public void testEmptyRecipeException() {
        EmptyRecipeException emptyRecipeException = new EmptyRecipeException();
        assertEquals("Recipe must have at least one ingredient and one step.", emptyRecipeException.getMessage());
    }

    @Test
    public void testDuplicateIngredientException() {
        String errorMessage = "Test error message";
        DuplicateIngredientException duplicateIngredientException = new DuplicateIngredientException(errorMessage);
        assertEquals(errorMessage, duplicateIngredientException.getMessage());
    }

}

