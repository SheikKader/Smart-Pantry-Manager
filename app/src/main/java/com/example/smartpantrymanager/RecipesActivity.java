package com.example.smartpantrymanager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView rView;
    private RecipeAdapter adapter;
    private PantryDb dbHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        rView = findViewById(R.id.rvRecipes);
        rView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new PantryDb(this);
        cursor = dbHelper.getRecipes();

        adapter = new RecipeAdapter(this, cursor);
        rView.setAdapter(adapter);
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