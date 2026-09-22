package com.sizwe.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.TextView;
import com.sizwe.smartpantrymanager.database.DatabaseHelper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        TextView txtIngredients =
                findViewById(R.id.txtIngredients);
        DatabaseHelper dbHelper =
                new DatabaseHelper(this);
        ArrayList<String> ingredients =
                dbHelper.getAllIngredients();
        StringBuilder builder =
                new StringBuilder();
        for(String ingredient : ingredients) {
            builder.append(ingredient)
                    .append("\n");
        }
        txtIngredients.setText(
                builder.toString()
        );
        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent =
                    new Intent(
                            PantryActivity.this,
                            AddIngredientActivity.class
                    );
            startActivity(intent);
        });
    }
}