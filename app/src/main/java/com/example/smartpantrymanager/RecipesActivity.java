package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView rView;
    private RecipeAdapter adapter;
    private PantryDb dbHelper;
    private Cursor cursor;
    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        rView = findViewById(R.id.rvRecipes);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        rView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new PantryDb(this);
        cursor = dbHelper.getAvailableRecipes();

        adapter = new RecipeAdapter(this, cursor);
        rView.setAdapter(adapter);
        if (cursor != null && cursor.getCount() > 0) {
            rView.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);
        } else {
            rView.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}