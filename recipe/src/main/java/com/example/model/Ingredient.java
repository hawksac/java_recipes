// src/main/java/com/example/model/Ingredient.java
package com.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty amount = new SimpleStringProperty();
    private final StringProperty unit = new SimpleStringProperty();

    public Ingredient() {
    }

    public Ingredient(String name, String amount, String unit) {
        this.name.set(name);
        this.amount.set(amount);
        this.unit.set(unit);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public String getAmount() {
        return amount.get();
    }

    public void setAmount(String amt) {
        this.amount.set(amt);
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String u) {
        this.unit.set(u);
    }
}
