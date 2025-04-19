package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {
    @FXML
    private void onCreateNewRecipe(ActionEvent e) {
        // TODO: load the recipe form view
        System.out.println("Create New Recipe clicked");
    }

    @FXML
    private void onOpenRecipe(ActionEvent e) {
        // TODO: show file chooser and load a saved recipe
        System.out.println("Open Existing Recipe clicked");
    }
}
