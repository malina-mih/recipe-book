package com.example.recipebookprojectfinal;

public class EmptyRecipeException extends Exception {
    public EmptyRecipeException() {
        super("Recipe must have at least one ingredient and one step.");
    }
}
