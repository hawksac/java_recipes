package com.example;

import com.example.model.Ingredient;
import com.example.model.Step;
import com.example.model.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    public void initialize() {
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colAmount.setCellValueFactory(c -> c.getValue().amountProperty());
        colUnit.setCellValueFactory(c -> c.getValue().unitProperty());
    }

    @FXML
    private void onAddIngredient() {
        // TODO: show a small dialog to enter name/amount/unit, then add to
        // ingredientsTable
    }

    @FXML
    private void onRemoveIngredient() {
        Ingredient selected = ingredientsTable.getSelectionModel().getSelectedItem();
        if (selected != null)
            ingredientsTable.getItems().remove(selected);
    }

    @FXML
    private void onAddStep() {
        TextInputDialog dlg = new TextInputDialog();
        dlg.setHeaderText("Add Cooking Step");
        dlg.showAndWait()
                .ifPresent(step -> stepsList.getItems().add(step));
    }

    @FXML
    private void onRemoveStep() {
        String step = stepsList.getSelectionModel().getSelectedItem();
        if (step != null)
            stepsList.getItems().remove(step);
    }

    @FXML
    private void onSaveRecipe() {
        // TODO: collect nameField, descriptionArea, ingredientsTable.getItems(),
        // stepsList.getItems()
        // then serialize to JSON via Jackson + FileChooser
    }

    @FXML
    private void onCancel() {
        // go back to welcome screen or clear form
    }
}
