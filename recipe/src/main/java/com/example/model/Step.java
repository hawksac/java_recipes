// src/main/java/com/example/model/Step.java
package com.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Step {
    private final StringProperty description = new SimpleStringProperty();

    public Step() {
    }

    public Step(String desc) {
        this.description.set(desc);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String desc) {
        this.description.set(desc);
    }
}
