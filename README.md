# Recipe Book Project

## Overview

The Recipe Book Project is a Java application designed to manage recipes, providing functionalities for adding, updating, and deleting recipes. The application supports different user roles such as Owner, Editor, and Viewer, each with specific permissions.

## Features

- **User Roles:**
  - **Owner:** Full access to add, update, and delete recipes. Can also manage user roles.
  - **Editor:** Can view and update recipes.
  - **Viewer:** Can view recipes and add the ingredients to a shopping list.

- **Recipe Management:**
  - Add, update, and delete recipes.
  - View a list of recipes with details like name, category, ingredients, and steps.

- **Shopping List:**
  - Add recipe ingredients to a shopping list.
  - View the shopping list.

- **Database Handling:**
  - Uses multithreading to perform database operations, preventing UI lag during data loading or updates.

## Project Structure

### Classes

1. **RecipeUI (Main Class):**
   - Launches the application and handles the primary UI.
   - Manages user roles and initiates the respective scenes.

2. **DatabaseThread:**
   - Handles database operations in a separate thread to prevent UI lag.
   - Used for loading data into the UI.

3. **ViewerSceneThread:**
   - Thread for the Viewer role.

4. **OwnerSceneThread:**
   - Thread for the Owner role.

5. **EditorSceneThread:**
   - Thread for the Editor role.

6. **RecipeData:**
   - Data model class representing a recipe.

7. **Ingredient:**
   - Data model class representing an ingredient, including properties like `ingredientId` and `ingredientName`.

8. **Recipe:**
   - Data model class representing a recipe, including properties like `recipeId`, `recipeName`, and `category`.

9. **Step:**
   - Data model class representing a step in a recipe, including properties like `stepId` and `description`.

10. **ShoppingListHandler:**
    - Manages shopping list operations such as adding ingredients and viewing the shopping list.

11. **UserManagementScene:**
    - Handles user management operations for the Owner role.

12. **AddRecipeHandler, UpdateRecipeHandler, DeleteRecipeHandler:**
    - Handle the UI interactions for adding, updating, and deleting recipes.

### Dependencies

- **JavaFX:** Used for building the graphical user interface.
- **Java SQL (JDBC):** Used for database connectivity.

