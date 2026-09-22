package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;
import android.content.Intent;

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
                        dbHelper.getSuggestedRecipes()
                );
        listRecipes.setAdapter(adapter);

        Button btnBack =
                findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        listRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {
                    String recipe =
                            adapter.getItem(position);
                    Intent intent =
                            new Intent(
                                    SuggestedRecipesActivity.this,
                                    RecipeDetailActivity.class
                            );
                    intent.putExtra(
                            "recipe_name",
                            recipe
                    );
                    startActivity(intent);
                });

    }

}