package com.example.recipebookprojectfinal;

import java.util.Objects;

public class Ingredient {
    private String name;

    public Ingredient(String name)
    {
        this.name=name;
    }
    public String getIngredientName()
    {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ingredient ingredient = (Ingredient) obj;
        return Objects.equals(getIngredientName(), ingredient.getIngredientName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredientName());
    }
}

