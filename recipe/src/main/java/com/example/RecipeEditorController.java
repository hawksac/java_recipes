package com.example;

import com.example.model.Ingredient;
import com.example.model.Recipe;
import com.example.model.Step;
import com.example.util.JsonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;

public class RecipeEditorController {
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TableView<Ingredient> ingredientsTable;
    @FXML
    private TableColumn<Ingredient, String> colName;
    @FXML
    private TableColumn<Ingredient, String> colAmount;
    @FXML
    private TableColumn<Ingredient, String> colUnit;
    @FXML
    private ListView<String> stepsList;

    // backing lists
    private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    private final ObservableList<String> steps = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // bind table columns
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colAmount.setCellValueFactory(c -> c.getValue().amountProperty());
        colUnit.setCellValueFactory(c -> c.getValue().unitProperty());

        // set items
        ingredientsTable.setItems(ingredients);
        stepsList.setItems(steps);
    }

    @FXML
    private void onAddIngredient(ActionEvent e) {
        // simple dialog for demo; you can replace with a custom dialog
        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText("Enter ingredient as: name,amount,unit");
        dlg.showAndWait().ifPresent(input -> {
            String[] parts = input.split(",", 3);
            if (parts.length == 3) {
                ingredients.add(new Ingredient(parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim()));
            }
        });
    }

    @FXML
    private void onRemoveIngredient(ActionEvent e) {
        Ingredient sel = ingredientsTable.getSelectionModel().getSelectedItem();
        if (sel != null)
            ingredients.remove(sel);
    }

    @FXML
    private void onAddStep(ActionEvent e) {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText("Enter step description");
        dlg.showAndWait().ifPresent(steps::add);
    }

    @FXML
    private void onRemoveStep(ActionEvent e) {
        String sel = stepsList.getSelectionModel().getSelectedItem();
        if (sel != null)
            steps.remove(sel);
    }

    @FXML
    private void onSaveRecipe(ActionEvent e) {
        // collect into model
        Recipe recipe = new Recipe();
        recipe.setName(nameField.getText());
        recipe.setDescription(descriptionArea.getText());
        recipe.setIngredients(List.copyOf(ingredients));
        recipe.setSteps(steps.stream().map(Step::new).collect(Collectors.toList()));

        // choose file
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Recipe");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = chooser.showSaveDialog(getStage());
        if (file != null) {
            try {
                JsonUtil.getMapper().writeValue(file, recipe);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        // go back to welcome
        Stage stage = getStage();
        try {
            stage.getScene().setRoot(
                    FXMLLoader.load(getClass().getResource("/com/example/WelcomeView.fxml")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) nameField.getScene().getWindow();
    }
}
