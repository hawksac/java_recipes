package com.example;

import java.io.File;
import java.io.IOException;

import com.example.model.Recipe;
import com.example.util.JsonUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class WelcomeController {
    @FXML
    private void onCreateNewRecipe(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/RecipeEditorView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onOpenRecipe(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Recipe");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = chooser.showOpenDialog(getStage(e));
        if (file == null)
            return;

        try {
            Recipe recipe = JsonUtil.getMapper().readValue(file, Recipe.class);
            loadEditor(recipe, e);
        } catch (IOException ex) {
            ex.printStackTrace();
            // optionally show an Alert here
        }
    }

    /**
     * Shared logic to load the RecipeEditorView, passing in a Recipe (or null for
     * new).
     */
    private void loadEditor(Recipe recipe, ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/RecipeEditorView.fxml"));
        Parent root = loader.load();

        // grab the controller and give it the recipe to edit
        RecipeEditorController controller = loader.getController();
        if (recipe != null) {
            controller.loadRecipe(recipe);
        }

        Stage stage = getStage(e);
        stage.setScene(new Scene(root));
    }

    private Stage getStage(ActionEvent e) {
        return (Stage) ((Node) e.getSource()).getScene().getWindow();
    }
}
