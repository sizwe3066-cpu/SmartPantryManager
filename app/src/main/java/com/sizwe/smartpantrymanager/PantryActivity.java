package com.sizwe.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class PantryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        ListView listIngredients =
                findViewById(R.id.listIngredients);
        DatabaseHelper dbHelper =
                new DatabaseHelper(this);
        ArrayList<String> ingredients =
                dbHelper.getAllIngredients();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        ingredients
                );
        listIngredients.setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    String ingredient =
                            ingredients.get(position);
                    dbHelper.deleteIngredient(
                            ingredient
                    );
                    Toast.makeText(
                            this,
                            ingredient + " deleted",
                            Toast.LENGTH_SHORT
                    ).show();
                    recreate();
                    return true;
                });
        listIngredients.setAdapter(adapter);
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