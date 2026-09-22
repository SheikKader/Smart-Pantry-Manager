package com.example.smartpantrymanager;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private PantryDb dbHelper;
    private PantryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewPantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new PantryDb(this);
        Cursor cursor = dbHelper.getInventory();
        adapter = new PantryAdapter(this, cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.swapCursor(null);
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}