package com.example.recipebookprojectfinal;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

public class FunctionalityTest {

    @Test
    public void testAddRecipeToApplication() {
        List<Recipe> recipes = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Application application = new Application(recipes, ingredients);

        RecipeModifierImpl recipeModifier = new RecipeModifierImpl(new Recipe("Pasta", Recipe.RecipeCategory.VEGAN, new Ingredient[0], new Step[0]));

        application.addRecipe(recipeModifier.getRecipe());
        assertTrue(application.getRecipeList().size() == 1);
        assertEquals("Pasta", application.getRecipeList().get(0).getRecipeName());
    }

    @Test
    public void testAddIngredientToApplication() {
        List<Recipe> recipes = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Application application = new Application(recipes, ingredients);

        Ingredient ingredient = new Ingredient("Tomato");
        RecipeModifier recipeModifier = new RecipeModifierImpl(new Recipe("Salad", Recipe.RecipeCategory.VEGAN, new Ingredient[0], new Step[0]));

        application.addIngredient(ingredient);
        assertThrows(DuplicateIngredientException.class, () -> application.checkDuplicateIngredient(ingredient));

        recipeModifier.addIngredient(ingredient);

        assertTrue(((RecipeModifierImpl) recipeModifier).getRecipe().getIngredients().contains(ingredient));
    }

    @Test
    public void testGenerateShoppingList() {
        Ingredient[] existingIngredients = {new Ingredient("Flour"), new Ingredient("Sugar"), new Ingredient("Eggs")};
        ShoppingListGenerator shoppingListGenerator = new ShoppingListGeneratorImpl(existingIngredients);

        Set<Ingredient> recipeIngredients = new HashSet<>(Arrays.asList(new Ingredient("Milk"), new Ingredient("Butter")));
        shoppingListGenerator.addRecipeIngredientsToShoppingList(recipeIngredients);

        shoppingListGenerator.saveShoppingListToFile("shopping_list.txt");

        File file = new File("shopping_list.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testRemoveIngredientFromRecipe() {
        Ingredient[] ingredients = {new Ingredient("Tomato"), new Ingredient("Lettuce"), new Ingredient("Cucumber")};
        Step[] steps = {new Step("Wash vegetables"), new Step("Chop vegetables")};

        Recipe recipe = new Recipe("Vegetable Salad", Recipe.RecipeCategory.VEGAN, ingredients, steps);


        assertTrue(recipe.getIngredients().contains(new Ingredient("Tomato")));
        assertTrue(recipe.getIngredients().contains(new Ingredient("Lettuce")));
        assertTrue(recipe.getIngredients().contains(new Ingredient("Cucumber")));

        recipe.removeIngredient(new Ingredient("Lettuce"));


        assertFalse(recipe.getIngredients().contains(new Ingredient("Lettuce")));
    }
}
