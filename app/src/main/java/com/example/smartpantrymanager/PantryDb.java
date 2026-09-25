package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        db.execSQL("CREATE TABLE pantry_stock (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, quantity REAL, unit TEXT, expiry_date TEXT)");

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

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        System.out.println("upgrading db...");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS pantry_stock");
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        ContentValues r1 = new ContentValues();
        r1.put("recipe_name", "Cheese Omelette");
        r1.put("instructions", "Scramble eggs. Pour into a heated pan with butter. Add cheese and fold. Serve once melted.");
        long id1 = db.insert("recipes", null, r1);
        insertRecipeIngredient(db, id1, "Eggs", 3, "whole");
        insertRecipeIngredient(db, id1, "Cheddar Cheese", 50, "grams");
        insertRecipeIngredient(db, id1, "Butter", 1, "tbsp");

        ContentValues r2 = new ContentValues();
        r2.put("recipe_name", "Toasted Cheese Sandwich");
        r2.put("instructions", "Butter the outside of the bread. Place cheese inside and grill in a pan until golden and melted.");
        long id2 = db.insert("recipes", null, r2);
        insertRecipeIngredient(db, id2, "Bread", 2, "slices");
        insertRecipeIngredient(db, id2, "Cheddar Cheese", 2, "slices");
        insertRecipeIngredient(db, id2, "Butter", 1, "tbsp");

        ContentValues r3 = new ContentValues();
        r3.put("recipe_name", "Chicken and Rice");
        r3.put("instructions", "Cook rice. Season and put chicken in air-fryer. Serve on rice.");
        long id3 = db.insert("recipes", null, r3);
        insertRecipeIngredient(db, id3, "Chicken", 250, "grams");
        insertRecipeIngredient(db, id3, "Rice", 1, "cup");

        ContentValues r4 = new ContentValues();
        r4.put("recipe_name", "Beef and Rice");
        r4.put("instructions", "Cook rice. Fry chopped beef with diced onions. Serve on rice.");
        long id4 = db.insert("recipes", null, r4);
        insertRecipeIngredient(db, id4, "Beef", 200, "grams");
        insertRecipeIngredient(db, id4, "Rice", 2, "cups");
        insertRecipeIngredient(db, id4, "Onion", 1, "whole");

        ContentValues r5 = new ContentValues();
        r5.put("recipe_name", "Toasted Tuna Sandwich");
        r5.put("instructions", "Mix tuna with mayonnaise and salt and pepper. Spread between bread slices, butter the outside and toast in a pan.");
        long id5 = db.insert("recipes", null, r5);
        insertRecipeIngredient(db, id5, "Canned Tuna", 1, "can");
        insertRecipeIngredient(db, id5, "Bread", 2, "slices");
        insertRecipeIngredient(db, id5, "Mayonnaise", 2, "tbsp");

        ContentValues r6 = new ContentValues();
        r6.put("recipe_name", "Chicken Stir Fry");
        r6.put("instructions", "Slice chicken and stir-fry on high heat. Toss in mixed vegetables and soy sauce. Serve hot.");
        long id6 = db.insert("recipes", null, r6);
        insertRecipeIngredient(db, id6, "Chicken Breast", 200, "grams");
        insertRecipeIngredient(db, id6, "Mixed Vegetables", 2, "cups");
        insertRecipeIngredient(db, id6, "Soy Sauce", 2, "tbsp");

        ContentValues r7 = new ContentValues();
        r7.put("recipe_name", "Pap, Sausages & Chakalaka");
        r7.put("instructions", "Cook maize meal into thick pap. Grill sausages and serve together topped with warm chakalaka.");
        long id7 = db.insert("recipes", null, r7);
        insertRecipeIngredient(db, id7, "Maize Meal", 2, "cups");
        insertRecipeIngredient(db, id7, "Sausages", 3, "whole");
        insertRecipeIngredient(db, id7, "Chakalaka", 1, "can");

        ContentValues r8 = new ContentValues();
        r8.put("recipe_name", "Baked Beans and Sausages");
        r8.put("instructions", "Fry sausages until brown. Cook baked beans separately in a pot. Serve side by side");
        long id8 = db.insert("recipes", null, r8);
        insertRecipeIngredient(db, id8, "Sausages", 4, "whole");
        insertRecipeIngredient(db, id8, "Baked Beans", 1, "can");

        ContentValues r9 = new ContentValues();
        r9.put("recipe_name", "Baked Potato and Cheese");
        r9.put("instructions", "Bake potato in the oven until soft. Slice open, fluff the inside with butter, and load with grated cheese.");
        long id9 = db.insert("recipes", null, r9);
        insertRecipeIngredient(db, id9, "Large Potato", 1, "whole");
        insertRecipeIngredient(db, id9, "Cheddar Cheese", 50, "grams");
        insertRecipeIngredient(db, id9, "Butter", 1, "tbsp");

        ContentValues r10 = new ContentValues();
        r10.put("recipe_name", "Snoek and Chips");
        r10.put("instructions", "Cut potatoes into thick chips and fry. Batter and fry the snoek until golden and crispy.");
        long id10 = db.insert("recipes", null, r10);
        insertRecipeIngredient(db, id10, "Snoek", 200, "grams");
        insertRecipeIngredient(db, id10, "Potatoes", 3, "whole");

        ContentValues r11 = new ContentValues();
        r11.put("recipe_name", "Chicken and Chips");
        r11.put("instructions", "Cut potatoes into thick chips and fry. Season chicken and roast or fry in airfryer.");
        long id11 = db.insert("recipes", null, r11);
        insertRecipeIngredient(db, id11, "Chicken Pieces", 300, "grams");
        insertRecipeIngredient(db, id11, "Potatoes", 3, "whole");

        ContentValues r12 = new ContentValues();
        r12.put("recipe_name", "Spaghetti Bolognese");
        r12.put("instructions", "Grate tomatoes and cook into pasta sauce.Fry beef, add tomato pasta sauce and simmer. Serve over cooked spaghetti.");
        long id12 = db.insert("recipes", null, r12);
        insertRecipeIngredient(db, id12, "Spaghetti", 150, "grams");
        insertRecipeIngredient(db, id12, "Beef", 250, "grams");
        insertRecipeIngredient(db, id12, "Tomato", 3, "whole");

        ContentValues r13 = new ContentValues();
        r13.put("recipe_name", "Fried Rice");
        r13.put("instructions", "Stir-fry cold cooked rice. Scramble an egg in the pan, mix together. Add soy sauce.");
        long id13 = db.insert("recipes", null, r13);
        insertRecipeIngredient(db, id13, "Rice", 2, "cups");
        insertRecipeIngredient(db, id13, "Eggs", 2, "whole");
        insertRecipeIngredient(db, id13, "Soy Sauce", 2, "tbsp");

        ContentValues r14 = new ContentValues();
        r14.put("recipe_name", "Scrambled Eggs on Toast");
        r14.put("instructions", "Scramble eggs. Cook slowly in a buttered pan and serve over hot buttered toast.");
        long id14 = db.insert("recipes", null, r14);
        insertRecipeIngredient(db, id14, "Eggs", 3, "whole");
        insertRecipeIngredient(db, id14, "Bread", 2, "slices");
        insertRecipeIngredient(db, id14, "Butter", 1, "tbsp");

        ContentValues r15 = new ContentValues();
        r15.put("recipe_name", "Tuna Pasta Bake");
        r15.put("instructions", "Mix cooked pasta, and canned tuna in a baking dish. Top with cheese and bake.");
        long id15 = db.insert("recipes", null, r15);
        insertRecipeIngredient(db, id15, "Pasta", 200, "grams");
        insertRecipeIngredient(db, id15, "Canned Tuna", 1, "can");
        insertRecipeIngredient(db, id15, "Cheddar Cheese", 100, "grams");

        ContentValues r16 = new ContentValues();
        r16.put("recipe_name", "Mac and Cheese");
        r16.put("instructions", "Boil macaroni. Drain and return to the hot pot. Stir in milk and grated cheese until melted and creamy.");
        long id16 = db.insert("recipes", null, r16);
        insertRecipeIngredient(db, id16, "Macaroni", 200, "grams");
        insertRecipeIngredient(db, id16, "Cheddar Cheese", 100, "grams");
        insertRecipeIngredient(db, id16, "Milk", 100, "ml");

        ContentValues r18 = new ContentValues();
        r18.put("recipe_name", "Chicken Mayo Sandwich");
        r18.put("instructions", "Shred cold cooked chicken and mix with mayonnaise. Season and serve between two slices of bread.");
        long id18 = db.insert("recipes", null, r18);
        insertRecipeIngredient(db, id18, "Cooked Chicken", 150, "grams");
        insertRecipeIngredient(db, id18, "Mayonnaise", 3, "tbsp");
        insertRecipeIngredient(db, id18, "Bread", 2, "slices");
    }

    private void insertRecipeIngredient(SQLiteDatabase db, long recipeId, String name, double qty, String unit) {
        ContentValues values = new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("ingredient_name", name);
        values.put("required_quantity", qty);
        values.put("unit", unit);
        db.insert("recipe_ingredients", null, values);
    }

    public boolean createIngredient(String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);
        long result = db.insert("pantry_stock", null, values);
        return result != -1;
    }

    public Cursor getInventory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM pantry_stock", null);
    }

    public Cursor getRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM recipes", null);
    }

    public Cursor getAvailableRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor allRecipes = db.rawQuery("SELECT * FROM recipes", null);
        StringBuilder validIds = new StringBuilder();

        while (allRecipes.moveToNext()) {
            int recipeId = allRecipes.getInt(allRecipes.getColumnIndexOrThrow("id"));

            Cursor reqs = db.rawQuery("SELECT * FROM recipe_ingredients WHERE recipe_id = ?",
                    new String[]{String.valueOf(recipeId)});

            boolean canCook = true;

            while (reqs.moveToNext()) {
                String reqName = reqs.getString(reqs.getColumnIndexOrThrow("ingredient_name"));
                double reqQty = reqs.getDouble(reqs.getColumnIndexOrThrow("required_quantity"));

                Cursor pantryCheck = db.rawQuery("SELECT * FROM pantry_stock WHERE name = ? COLLATE NOCASE",
                        new String[]{reqName});

                if (pantryCheck.moveToFirst()) {
                    double pantryQty = pantryCheck.getDouble(pantryCheck.getColumnIndexOrThrow("quantity"));
                    if (pantryQty < reqQty) {
                        canCook = false;
                    }
                } else {
                    canCook = false;
                }
                pantryCheck.close();

                if (!canCook) {
                    break;
                }
            }
            reqs.close();

            if (canCook) {
                if (validIds.length() > 0) {
                    validIds.append(",");
                }
                validIds.append(recipeId);
            }
        }
        allRecipes.close();

        if (validIds.length() == 0) {
            return db.rawQuery("SELECT * FROM recipes WHERE id = -1", null);
        }

        return db.rawQuery("SELECT * FROM recipes WHERE id IN (" + validIds.toString() + ")", null);
    }
    public Cursor getRecipeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM recipes WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getRecipeIngredients(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM recipe_ingredients WHERE recipe_id = ?", new String[]{String.valueOf(recipeId)});
    }

    public boolean updateIngredient(int id, String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);
        int result = db.update("pantry_stock", values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("pantry_stock", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}