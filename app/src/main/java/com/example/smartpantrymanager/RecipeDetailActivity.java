package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView tvName, tvIngredients, tvInstructions;
    private PantryDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tvName = findViewById(R.id.tvRecipeName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvInstructions = findViewById(R.id.tvInstructions);

        dbHelper = new PantryDb(this);

        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        }
    }

    private void loadRecipeDetails(int id) {
        Cursor recipeCursor = dbHelper.getRecipeById(id);
        if (recipeCursor.moveToFirst()) {
            String name = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow("recipe_name"));
            String instructions = recipeCursor.getString(recipeCursor.getColumnIndexOrThrow("instructions"));
            tvName.setText(name);
            tvInstructions.setText(instructions);
        }
        recipeCursor.close();

        Cursor ingredientsCursor = dbHelper.getRecipeIngredients(id);
        StringBuilder ingredientsList = new StringBuilder();
        while (ingredientsCursor.moveToNext()) {
            String ingName = ingredientsCursor.getString(ingredientsCursor.getColumnIndexOrThrow("ingredient_name"));
            double qty = ingredientsCursor.getDouble(ingredientsCursor.getColumnIndexOrThrow("required_quantity"));
            String unit = ingredientsCursor.getString(ingredientsCursor.getColumnIndexOrThrow("unit"));
            ingredientsList.append("- ").append(ingName).append(" (").append(qty).append(" ").append(unit).append(")\n");
        }
        ingredientsCursor.close();
        tvIngredients.setText(ingredientsList.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}