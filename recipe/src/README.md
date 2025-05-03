# Recipe Builder (JavaFX)

A simple desktop application to create, edit, and manage cooking recipes. This JavaFX-based app persists recipes to JSON files (no database required) and supports:

- **Create New Recipe**: Enter name, description, ingredients, and steps.
- **Edit Existing Recipe**: Load a saved JSON recipe file and modify it.
- **Save Recipe**: Export recipes to prettified JSON files.
- **Unit Tests**: JUnit tests for serialization/deserialization.

---

## Table of Contents

1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Project Structure](#project-structure)
4. [Build & Run](#build--run)
5. [Usage](#usage)
6. [Testing](#testing)
7. [Packaging](#packaging)
8. [License](#license)

---

## Features

- **Welcome Screen**: New vs. open recipe options.
- **Recipe Editor**:
  - Name & description fields.
  - Ingredients in a `TableView` (add/remove).
  - Steps in a `ListView` (add/remove).
- **Persistence**: Uses Jackson to serialize `Recipe` model to JSON and read back.
- **Modular Design**: Java modules with `module-info.java`, clean package separation:
  - `com.example.model`: domain classes (`Recipe`, `Ingredient`, `Step`).
  - `com.example`: controllers and `App` launcher.
  - `com.example.util`: JSON utility.
- **JSON Serialization Tests**: JUnit test for round‑trip verification.

---

## Prerequisites

- **Java 11** or higher
- **Maven** 3.6+
- **macOS/Linux/Windows** (JavaFX classifier in `pom.xml` may need adjustment)

Note: JavaFX 17 modules are referenced in `pom.xml` with classifier `mac`. For Windows or Linux, adjust to `win` or `linux` accordingly.

---

## Project Structure

recipe/
├─ pom.xml
├─ module-info.java
├─ src/
│ ├─ main/
│ │ ├─ java/
│ │ │ └─ com/example/
│ │ │ ├─ App.java # JavaFX Application launcher
│ │ │ ├─ WelcomeController.java # handles new/open actions
│ │ │ ├─ RecipeEditorController.java # recipe form logic
│ │ │ ├─ util/
│ │ │ │ └─ JsonUtil.java # Jackson ObjectMapper setup
│ │ │ └─ model/
│ │ │ ├─ Recipe.java
│ │ │ ├─ Ingredient.java
│ │ │ └─ Step.java
│ │ └─ resources/
│ │ └─ com/example/
│ │ ├─ WelcomeView.fxml
│ │ └─ RecipeEditorView.fxml
│ └─ test/
│ ├─ java/
│ │ └─ com/example/model/
│ │ └─ RecipeSerializationTest.java
│ └─ resources/
│ └─ testRecipe.json
└─ README.md

---

## Usage

- **Create New Recipe**: Fill out the name, description, ingredients, and steps; click **Save Recipe** and choose a `.json` file location.
- **Open Existing Recipe**: Click **Open Existing Recipe**, select a previously saved `.json` file, then modify and re-save as needed.

---

## Testing

Run JSON serialization tests with:

```bash
mvn test
```

A sample `testRecipe.json` in `src/test/resources` is used to verify the serialization round‑trip.

---

## Packaging

To package as an executable JAR with dependencies:

```bash
mvn clean package
```

Then run:

```bash
java --module-path path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/recipe-1.0-SNAPSHOT.jar
```
