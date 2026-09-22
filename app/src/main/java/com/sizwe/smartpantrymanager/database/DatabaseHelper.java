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
    public void deleteIngredient(String ingredientName){
        SQLiteDatabase db =
                this.getWritableDatabase();
        db.delete(
                "pantry",
                "ingredient_name=?",
                new String[]{ingredientName}
        );
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
    public boolean updateIngredient(
            String oldName,
            String newName) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("ingredient_name", newName);

        int result = db.update(
                "pantry",
                values,
                "ingredient_name=?",
                new String[]{oldName}
        );

        return result > 0;
    }

    public void insertRecipe(
            String recipeName,
            String instructions) {

        SQLiteDatabase db =
                this.getWritableDatabase();
        ContentValues values =
                new ContentValues();
        values.put("recipe_name", recipeName);
        values.put("instructions", instructions);
        db.insert(
                "recipes",
                null,
                values
        );
    }
    public void seedRecipes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM recipes",
                        null
                );
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if(count > 0){
            return;
        }
        insertRecipe(
                "French Toast",
                "Beat eggs and milk. Dip bread and fry."
        );
        insertRecipe(
                "Omelette",
                "Mix eggs and milk then fry."
        );
        insertRecipe(
                "Grilled Cheese",
                "Place cheese between bread and toast."
        );
        insertRecipe(
                "Pancakes",
                "Mix flour, milk and eggs then fry."
        );
}
    public void insertRecipeIngredient(
            int recipeId,
            String ingredientName,
            double quantity,
            String unit){
        SQLiteDatabase db =
                this.getWritableDatabase();
        ContentValues values =
                new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("ingredient_name", ingredientName);
        values.put("quantity", quantity);
        values.put("unit", unit);
        db.insert(
                "recipe_ingredients",
                null,
                values
        );
    }
    public ArrayList<String> getAllRecipes(){
        ArrayList<String> recipes =
                new ArrayList<>();
        SQLiteDatabase db =
                this.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(
                        "SELECT recipe_name FROM recipes",
                        null
                );
        if(cursor.moveToFirst()){
            do{
                recipes.add(
                        cursor.getString(0)
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }

    public void seedRecipeIngredients(){
        SQLiteDatabase db =
                this.getWritableDatabase();
        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM recipe_ingredients",
                        null
                );
        cursor.moveToFirst();
        int count =
                cursor.getInt(0);
        cursor.close();
        if(count > 0){
            return;
        }
        insertRecipeIngredient(1,"Milk",1,"Cup");
        insertRecipeIngredient(1,"Eggs",2,"Units");
        insertRecipeIngredient(1,"Bread",2,"Slices");

        insertRecipeIngredient(2,"Eggs",2,"Units");
        insertRecipeIngredient(2,"Milk",1,"Cup");

        insertRecipeIngredient(3,"Bread",2,"Slices");
        insertRecipeIngredient(3,"Cheese",2,"Slices");

        insertRecipeIngredient(4,"Milk",1,"Cup");
        insertRecipeIngredient(4,"Eggs",2,"Units");
        insertRecipeIngredient(4,"Flour",2,"Cups");
    }

    public boolean ingredientExists(
            String ingredientName){
        SQLiteDatabase db =
                this.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM pantry WHERE ingredient_name=?",
                        new String[]{ingredientName}
                );
        boolean exists =
                cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public ArrayList<String> getSuggestedRecipes(){
        ArrayList<String> suggestions =
                new ArrayList<>();
        if(
                ingredientExists("Milk")
                        &&
                        ingredientExists("Eggs")
                        &&
                        ingredientExists("Bread")
        ){
            suggestions.add(
                    "French Toast"
            );
        }
        if(
                ingredientExists("Milk")
                        &&
                        ingredientExists("Eggs")
        ){
            suggestions.add(
                    "Omelette"
            );
        }
        if(
                ingredientExists("Bread")
                        &&
                        ingredientExists("Cheese")
        ){
            suggestions.add(
                    "Grilled Cheese"
            );
        }
        if(
                ingredientExists("Milk")
                        &&
                        ingredientExists("Eggs")
                        &&
                        ingredientExists("Flour")
        ){
            suggestions.add(
                    "Pancakes"
            );
        }
        return suggestions;
    }

    public String getRecipeInstructions(
            String recipeName){
        SQLiteDatabase db =
                this.getReadableDatabase();
        Cursor cursor =
                db.rawQuery(
                        "SELECT instructions FROM recipes WHERE recipe_name=?",
                        new String[]{recipeName}
                );
        String instructions = "";
        if(cursor.moveToFirst()){
            instructions =
                    cursor.getString(0);
        }
        cursor.close();
        return instructions;
    }

}