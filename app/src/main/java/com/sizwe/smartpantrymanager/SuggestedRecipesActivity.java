package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;

public class SuggestedRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_suggested_recipes
        );
        ListView listRecipes =
                findViewById(R.id.listRecipes);
        DatabaseHelper dbHelper =
                new DatabaseHelper(this);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        dbHelper.getAllRecipes()
                );
        listRecipes.setAdapter(adapter);
    }
}