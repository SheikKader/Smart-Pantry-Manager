package com.example.smartpantrymanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pantry.db";
    public static final int VERSION = 1;

    public PantryDb(Context c) {
        super(c, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // make the pantry table
        db.execSQL("CREATE TABLE pantry_stock (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, quantity REAL, unit TEXT, expiry_date TEXT)");

        // recipes
        String createRecipes = "CREATE TABLE recipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "recipe_name TEXT,"
                + "instructions TEXT)";
        db.execSQL(createRecipes);

        db.execSQL("CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER, " +
                "ingredient_name TEXT, " +
                "required_quantity REAL, " +
                "unit TEXT, " +
                "FOREIGN KEY (recipe_id) REFERENCES recipes(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        System.out.println("Upgrading db...");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS pantry_stock");
        onCreate(db);
    }
}