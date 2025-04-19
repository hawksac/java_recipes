// src/main/java/com/example/model/Recipe.java
package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    public Recipe() {
    }

    // getters/setters for name & description
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ing) {
        this.ingredients = ing;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
