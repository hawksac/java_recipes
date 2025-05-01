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
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

import java.io.File;
import java.io.FileOutputStream;
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

    /**
     * Called by WelcomeController when opening an existing recipe.
     */
    public void loadRecipe(Recipe recipe) {
        // populate form
        nameField.setText(recipe.getName());
        descriptionArea.setText(recipe.getDescription());
        ingredients.setAll(recipe.getIngredients());
        // convert Step objects to their string descriptions
        List<String> descs = recipe.getSteps()
                .stream()
                .map(Step::getDescription)
                .collect(Collectors.toList());
        steps.setAll(descs);
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
        // 1) Build the domain object
        Recipe recipe = new Recipe();
        recipe.setName(nameField.getText());
        recipe.setDescription(descriptionArea.getText());
        recipe.setIngredients(List.copyOf(ingredients));
        recipe.setSteps(
                steps.stream()
                        .map(Step::new)
                        .collect(Collectors.toList()));

        try {
            // 2) Pretty-print JSON and log it
            var mapper = JsonUtil.getMapper().writerWithDefaultPrettyPrinter();
            String json = mapper.writeValueAsString(recipe);
            System.out.println("▶ Saving recipe JSON:\n" + json);

            // 3) File chooser
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Recipe");
            chooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = chooser.showSaveDialog(getStage());
            if (file == null)
                return;

            // 4) Write out
            try (FileOutputStream out = new FileOutputStream(file)) {
                mapper.writeValue(out, recipe);
            }

            System.out.println("✔ Recipe saved to " + file.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
            // optionally show an alert so the user sees the error
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Unable to save recipe:\n" + ex.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        // go back to welcome
        Stage stage = getStage();
        try {
            Parent welcome = FXMLLoader.load(
                    getClass().getResource("/com/example/WelcomeView.fxml"));
            stage.setScene(new Scene(welcome));
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Unable to load the welcome view:\n" + ex.getMessage(),
                    ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Stage getStage() {
        return (Stage) nameField.getScene().getWindow();
    }
}
