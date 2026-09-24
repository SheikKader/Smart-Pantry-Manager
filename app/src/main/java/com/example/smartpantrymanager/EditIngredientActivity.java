package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditIngredientActivity extends AppCompatActivity {

    private TextInputEditText etName, etQuantity, etUnit, etExpiry;
    private MaterialButton btnUpdate;
    private PantryDb dbHelper;
    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ingredient);

        dbHelper = new PantryDb(this);

        etName = findViewById(R.id.etEditName);
        etQuantity = findViewById(R.id.etEditQuantity);
        etUnit = findViewById(R.id.etEditUnit);
        etExpiry = findViewById(R.id.etEditExpiry);
        btnUpdate = findViewById(R.id.btnUpdateIngredient);
        ingredientId = getIntent().getIntExtra("ID", -1);
        String currentName = getIntent().getStringExtra("NAME");
        double currentQty = getIntent().getDoubleExtra("QUANTITY", 0.0);
        String currentUnit = getIntent().getStringExtra("UNIT");
        String currentExpiry = getIntent().getStringExtra("EXPIRY");

        if (currentName != null) etName.setText(currentName);
        if (currentQty > 0) etQuantity.setText(String.valueOf(currentQty));
        if (currentUnit != null) etUnit.setText(currentUnit);
        if (currentExpiry != null) etExpiry.setText(currentExpiry);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB();
            }
        });
    }

    private void updateDB() {
        if (ingredientId == -1) return;
        String name = etName.getText().toString().trim();
        String qtyStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty()) return;

        double quantity;
        try { quantity = Double.parseDouble(qtyStr); }
        catch (NumberFormatException e) { return; }

        boolean update = dbHelper.updateIngredient(ingredientId, name, quantity, unit, expiry);

        if (update) {
            Toast.makeText(this, "Ingredient has been updated", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) dbHelper.close();
    }
}