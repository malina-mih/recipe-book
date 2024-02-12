package com.example.recipebookprojectfinal;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class ShoppingListGeneratorImpl implements ShoppingListGenerator {
    private Set<Ingredient> shoppingList;

    public ShoppingListGeneratorImpl(Ingredient[] existingIngredients) {
        if (existingIngredients != null) {
            this.shoppingList = new HashSet<>(Arrays.asList(existingIngredients));
        } else {
            this.shoppingList = new HashSet<>();
        }
    }

    @Override
    public void addRecipeIngredientsToShoppingList(Set<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            shoppingList.add(ingredient);
        }

    }


    public void saveShoppingListToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Ingredient ingredient : shoppingList)
            {
                writer.write(ingredient.getIngredientName());
                writer.newLine();

            }
            writer.close();
            System.out.println("Shopping list saved to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found while saving shopping list: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error occurred while saving shopping list: " + e.getMessage());
        }
    }

    public Collection<Ingredient> getShoppingList() {
        return shoppingList;
    }
}

