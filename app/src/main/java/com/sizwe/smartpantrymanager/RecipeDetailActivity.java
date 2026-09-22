package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;
import android.widget.Button;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        TextView txtRecipeName =
                findViewById(R.id.txtRecipeName);

        TextView txtInstructions =
                findViewById(R.id.txtInstructions);

        String recipeName =
                getIntent().getStringExtra(
                        "recipe_name"
                );

        DatabaseHelper dbHelper =
                new DatabaseHelper(this);

        txtRecipeName.setText(recipeName);

        txtInstructions.setText(
                dbHelper.getRecipeInstructions(
                        recipeName
                )
        );
        TextView txtIngredients =
                findViewById(R.id.txtIngredients);

        txtIngredients.setText(
                dbHelper.getRecipeIngredients(
                        recipeName
                )
        );
        Button btnBack =
                findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());
    }
}