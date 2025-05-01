// src/test/java/com/example/model/RecipeSerializationTest.java
package com.example.model;

import com.example.util.JsonUtil;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeSerializationTest {

    @Test
    void testDeserializeAndSerialize() throws Exception {
        // 1) Load the “golden” test JSON from resources
        try (InputStream in = getClass()
                .getResourceAsStream("/testRecipe.json")) {
            assertNotNull(in, "Test JSON not found in resources");

            // 2) Deserialize into Recipe
            Recipe r = JsonUtil.getMapper()
                    .readValue(in, Recipe.class);

            // 3) Assert the fields
            assertEquals("Test Pancakes", r.getName());
            assertEquals("Fluffy test pancakes", r.getDescription());

            List<Ingredient> ing = r.getIngredients();
            assertEquals(2, ing.size());
            assertEquals("Flour", ing.get(0).getName());
            assertEquals("1", ing.get(0).getAmount());
            assertEquals("cup", ing.get(0).getUnit());

            List<Step> steps = r.getSteps();
            assertEquals(2, steps.size());
            assertEquals("Mix dry ingredients", steps.get(0).getDescription());

            // 4) Round-trip: serialize back to JSON string
            String jsonOut = JsonUtil.getMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(r);

            // 5) Re-read it into another Recipe and compare
            Recipe r2 = JsonUtil.getMapper()
                    .readValue(jsonOut, Recipe.class);
            assertEquals(r.getName(), r2.getName());
            assertEquals(r.getIngredients().size(), r2.getIngredients().size());
            assertEquals(r.getSteps().get(1).getDescription(),
                    r2.getSteps().get(1).getDescription());
        }
    }
}
