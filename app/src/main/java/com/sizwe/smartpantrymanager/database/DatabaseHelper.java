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
        insertRecipe(
                "Scrambled Eggs",
                "Whisk eggs and cook slowly."
        );
        insertRecipe(
                "Egg Sandwich",
                "Cook eggs and place between bread slices."
        );
        insertRecipe(
                "Chicken Salad",
                "Mix chicken and vegetables."
        );
        insertRecipe(
                "Tuna Sandwich",
                "Mix tuna and place in bread."
        );
        insertRecipe(
                "Tomato Soup",
                "Cook tomatoes and blend."
        );
        insertRecipe(
                "Chicken Wrap",
                "Wrap chicken and vegetables in a tortilla."
        );
        insertRecipe(
                "Spaghetti Bolognese",
                "Cook pasta and beef sauce."
        );
        insertRecipe(
                "Chicken Stir Fry",
                "Cook chicken and vegetables together."
        );
        insertRecipe(
                "Beef Stew",
                "Slow cook beef and vegetables."
        );
        insertRecipe(
                "Fried Rice",
                "Cook rice with eggs and vegetables."
        );
        insertRecipe(
                "Mac and Cheese",
                "Cook pasta and add cheese sauce."
        );
        insertRecipe(
                "Fruit Salad",
                "Mix assorted fruits."
        );
        insertRecipe(
                "Peanut Butter Toast",
                "Spread peanut butter on toast."
        );
        insertRecipe(
                "Cheese Toastie",
                "Toast bread with cheese filling."
        );
        insertRecipe(
                "Banana Pancakes",
                "Mix banana into pancake batter."
        );
        insertRecipe(
                "Custard Pudding",
                "Mix milk and custard then chill."
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

        // Recipe 1 - French Toast
        insertRecipeIngredient(1,"Milk",1,"Cup");
        insertRecipeIngredient(1,"Eggs",2,"Units");
        insertRecipeIngredient(1,"Bread",2,"Slices");

        // Recipe 2 - Omelette
        insertRecipeIngredient(2,"Eggs",2,"Units");
        insertRecipeIngredient(2,"Milk",1,"Cup");

        // Recipe 3 - Grilled Cheese
        insertRecipeIngredient(3,"Bread",2,"Slices");
        insertRecipeIngredient(3,"Cheese",2,"Slices");

        // Recipe 4 - Pancakes
        insertRecipeIngredient(4,"Milk",1,"Cup");
        insertRecipeIngredient(4,"Eggs",2,"Units");
        insertRecipeIngredient(4,"Flour",2,"Cups");

        // Recipe 5 - Scrambled Eggs
        insertRecipeIngredient(5,"Eggs",2,"Units");

        // Recipe 6 - Egg Sandwich
        insertRecipeIngredient(6,"Eggs",2,"Units");
        insertRecipeIngredient(6,"Bread",2,"Slices");

        // Recipe 7 - Chicken Salad
        insertRecipeIngredient(7,"Chicken",200,"Grams");
        insertRecipeIngredient(7,"Lettuce",1,"Head");
        insertRecipeIngredient(7,"Tomato",2,"Units");

        // Recipe 8 - Tuna Sandwich
        insertRecipeIngredient(8,"Tuna",1,"Can");
        insertRecipeIngredient(8,"Bread",2,"Slices");

        // Recipe 9 - Tomato Soup
        insertRecipeIngredient(9,"Tomato",4,"Units");
        insertRecipeIngredient(9,"Water",2,"Cups");

        // Recipe 10 - Chicken Wrap
        insertRecipeIngredient(10,"Chicken",200,"Grams");
        insertRecipeIngredient(10,"Wrap",1,"Unit");

        // Recipe 11 - Spaghetti Bolognese
        insertRecipeIngredient(11,"Pasta",200,"Grams");
        insertRecipeIngredient(11,"Beef",200,"Grams");

        // Recipe 12 - Chicken Stir Fry
        insertRecipeIngredient(12,"Chicken",200,"Grams");
        insertRecipeIngredient(12,"Vegetables",1,"Pack");

        // Recipe 13 - Beef Stew
        insertRecipeIngredient(13,"Beef",300,"Grams");
        insertRecipeIngredient(13,"Potato",2,"Units");

        // Recipe 14 - Fried Rice
        insertRecipeIngredient(14,"Rice",200,"Grams");
        insertRecipeIngredient(14,"Eggs",2,"Units");

        // Recipe 15 - Mac and Cheese
        insertRecipeIngredient(15,"Pasta",200,"Grams");
        insertRecipeIngredient(15,"Cheese",150,"Grams");

        // Recipe 16 - Fruit Salad
        insertRecipeIngredient(16,"Apple",1,"Unit");
        insertRecipeIngredient(16,"Banana",1,"Unit");

        // Recipe 17 - Peanut Butter Toast
        insertRecipeIngredient(17,"Bread",2,"Slices");
        insertRecipeIngredient(17,"Peanut Butter",2,"Tablespoons");

        // Recipe 18 - Cheese Toastie
        insertRecipeIngredient(18,"Bread",2,"Slices");
        insertRecipeIngredient(18,"Cheese",2,"Slices");

        // Recipe 19 - Banana Pancakes
        insertRecipeIngredient(19,"Banana",1,"Unit");
        insertRecipeIngredient(19,"Milk",1,"Cup");
        insertRecipeIngredient(19,"Flour",1,"Cup");

        // Recipe 20 - Custard Pudding
        insertRecipeIngredient(20,"Milk",2,"Cups");
        insertRecipeIngredient(20,"Custard Powder",2,"Tablespoons");


        insertRecipeIngredient(
                15,
                "Pasta",
                200,
                "Grams"
        );

        insertRecipeIngredient(
                15,
                "Cheese",
                150,
                "Grams"
        );

        insertRecipeIngredient(
                16,
                "Apple",
                1,
                "Unit"
        );

        insertRecipeIngredient(
                16,
                "Banana",
                1,
                "Unit"
        );
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
    public ArrayList<String> getSuggestedRecipes() {

        ArrayList<String> suggestedRecipes =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor recipeCursor =
                db.rawQuery(
                        "SELECT recipe_id, recipe_name FROM recipes",
                        null
                );

        if(recipeCursor.moveToFirst()){

            do{

                int recipeId =
                        recipeCursor.getInt(0);

                String recipeName =
                        recipeCursor.getString(1);

                Cursor ingredientCursor =
                        db.rawQuery(
                                "SELECT ingredient_name " +
                                        "FROM recipe_ingredients " +
                                        "WHERE recipe_id=?",
                                new String[]{
                                        String.valueOf(recipeId)
                                }
                        );
                boolean canMakeRecipe =
                        true;
                if(ingredientCursor.moveToFirst()){

                    do{
                        String ingredient =
                                ingredientCursor.getString(0);
                        if(!ingredientExists(ingredient)){
                            canMakeRecipe =
                                    false;
                            break;
                        }
                    }
                    while(ingredientCursor.moveToNext());
                }
                ingredientCursor.close();
                if(canMakeRecipe){
                    suggestedRecipes.add(
                            recipeName
                    );
                }
            }
            while(recipeCursor.moveToNext());
        }
        recipeCursor.close();
        return suggestedRecipes;
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

    public String getRecipeIngredientsText(int recipeId){

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT ingredient_name FROM recipe_ingredients WHERE recipe_id=?",
                        new String[]{String.valueOf(recipeId)}
                );
        StringBuilder builder =
                new StringBuilder();
        if(cursor.moveToFirst()){
            do{
                builder.append(
                        cursor.getString(0)
                ).append("\n");
            }while(cursor.moveToNext());
        }
        cursor.close();
        return builder.toString();
    }
    public String getRecipeIngredients(
            String recipeName){
        if(recipeName.equals("French Toast")){
            return "Milk\nEggs\nBread";
        }
        if(recipeName.equals("Omelette")){
            return "Eggs\nMilk";
        }
        if(recipeName.equals("Grilled Cheese")){
            return "Bread\nCheese";
        }
        if(recipeName.equals("Pancakes")){
            return "Milk\nEggs\nFlour";
        }
        return "";
    }
    public String getRecipeIngredientsByRecipeId(
            int recipeId){

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT ingredient_name " +
                                "FROM recipe_ingredients " +
                                "WHERE recipe_id=?",
                        new String[]{
                                String.valueOf(recipeId)
                        }
                );
        StringBuilder builder =
                new StringBuilder();
        if(cursor.moveToFirst()){
            do{
                builder.append(
                        cursor.getString(0)
                ).append("\n");

            }while(cursor.moveToNext());
        }
        cursor.close();
        return builder.toString();
    }

}