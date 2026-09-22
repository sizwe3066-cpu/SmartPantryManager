package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;
import android.content.Intent;

import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

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

        TextView txtNoRecipes =
                findViewById(R.id.txtNoRecipes);

        ArrayList<String> recipes =
                dbHelper.getSuggestedRecipes();

        if(recipes.isEmpty()){

            txtNoRecipes.setVisibility(
                    View.VISIBLE
            );

            listRecipes.setVisibility(
                    View.GONE
            );
        }
        else{

            RecipeAdapter adapter =
                    new RecipeAdapter(
                            this,
                            recipes
                    );

            listRecipes.setAdapter(adapter);
        }

        Button btnBack =
                findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        listRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    String recipe =
                            recipes.get(position);
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