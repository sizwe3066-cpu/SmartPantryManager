package com.sizwe.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        dbHelper.seedRecipes();
        dbHelper.seedRecipeIngredients();

        Intent intent = new Intent(this, PantryActivity.class);
        startActivity(intent);
        finish();
    }
}
