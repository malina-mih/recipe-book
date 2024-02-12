package com.example.recipebookprojectfinal;


import java.util.Set;

public interface ShoppingListGenerator {

    void addRecipeIngredientsToShoppingList(Set<Ingredient> ingredients);
    void saveShoppingListToFile(String filename);
}

