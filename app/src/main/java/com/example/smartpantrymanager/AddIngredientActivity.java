package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class AddIngredientActivity extends AppCompatActivity {

    private TextInputEditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave;
    private PantryDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);

        dbHelper = new PantryDb(this);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        btnSave = findViewById(R.id.btnSaveIngredient);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        String name = etName.getText().toString().trim();
        String qty = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }
        if (qty.isEmpty()) {
            etQuantity.setError("Quantity is required");
            return;
        }
        if (unit.isEmpty()) {
            etUnit.setError("Unit is required");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(qty);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid number");
            return;
        }

        if (quantity <= 0) {
            etQuantity.setError("Quantity must be more than 0");
            return;
        }

        if (ingredientAlreadyExists(name)) {
            etName.setError("Already in pantry");
            Toast.makeText(this, "This ingredient is already in your pantry", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.createIngredient(name, quantity, unit, expiry);

        if (success) {
            Toast.makeText(this, "Ingredient added!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ingredientAlreadyExists(String name) {
        Cursor cursor = dbHelper.getInventory();
        if (cursor == null) return false;

        try {
            int nameCol = cursor.getColumnIndexOrThrow("name");
            while (cursor.moveToNext()) {
                String existing = cursor.getString(nameCol);
                if (existing != null && existing.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } finally {
            cursor.close();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}