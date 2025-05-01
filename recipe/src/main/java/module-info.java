module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires com.fasterxml.jackson.databind;

    // open your main package to both the FXML loader and the launcher
    opens com.example to javafx.fxml, javafx.graphics;

    // open your model package to Jackson
    opens com.example.model to com.fasterxml.jackson.databind;
}
