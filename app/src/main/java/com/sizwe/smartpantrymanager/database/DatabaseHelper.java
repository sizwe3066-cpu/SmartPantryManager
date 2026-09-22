package com.sizwe.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;

import android.database.Cursor;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String pantryTable = "CREATE TABLE pantry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ingredient_name TEXT," +
                "quantity REAL," +
                "unit TEXT," +
                "expiry_date TEXT)";

        String recipeTable = "CREATE TABLE recipes (" +
                "recipe_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipe_name TEXT," +
                "instructions TEXT)";

        String recipeIngredientTable = "CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "recipe_id INTEGER," +
                "ingredient_name TEXT," +
                "quantity REAL," +
                "unit TEXT)";

        db.execSQL(pantryTable);
        db.execSQL(recipeTable);
        db.execSQL(recipeIngredientTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS pantry");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");

        onCreate(db);
    }

    public boolean addIngredient(String ingredientName,
                                 double quantity,
                                 String unit,
                                 String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("ingredient_name", ingredientName);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        long result = db.insert("pantry", null, values);

        return result != -1;
    }
    public ArrayList<String> getAllIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ingredient_name FROM pantry",
                null
        );
        if (cursor.moveToFirst()) {
            do {
                ingredients.add(
                        cursor.getString(0)
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }
}