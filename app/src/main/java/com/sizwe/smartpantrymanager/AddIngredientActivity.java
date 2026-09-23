package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.database.Cursor;

public class AddIngredientActivity extends AppCompatActivity {

    private AutoCompleteTextView etIngredientName;

    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnSaveIngredient;

    private DatabaseHelper dbHelper;

    private boolean isEdit = false;
    private String originalIngredientName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        etIngredientName = findViewById(R.id.etIngredientName);

        String[] pantryIngredients = {
                "Milk",
                "Eggs",
                "Bread",
                "Cheese",
                "Flour",
                "Chicken",
                "Beef",
                "Rice",
                "Pasta",
                "Tomato",
                "Tuna",
                "Banana",
                "Apple",
                "Peanut Butter",
                "Custard Powder",
                "Lettuce",
                "Potato",
                "Vegetables",
                "Wrap",
                "Water"
        };

        ArrayAdapter<String> ingredientAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        pantryIngredients
                );

        etIngredientName.setAdapter(
                ingredientAdapter
        );

        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        dbHelper = new DatabaseHelper(this);
        isEdit =
                getIntent().getBooleanExtra(
                        "isEdit",
                        false
                );
        originalIngredientName =
                getIntent().getStringExtra(
                        "ingredient_name"
                );

        if(isEdit && originalIngredientName != null){
            Cursor cursor =
                    dbHelper.getIngredientDetails(
                            originalIngredientName
                    );
            if(cursor.moveToFirst()){
                etIngredientName.setText(
                        cursor.getString(0)
                );
                etQuantity.setText(
                        String.valueOf(
                                cursor.getDouble(1)
                        )
                );
                etUnit.setText(
                        cursor.getString(2)
                );
                etExpiryDate.setText(
                        cursor.getString(3)
                );
            }
            cursor.close();
            btnSaveIngredient.setText(
                    "Update Ingredient"
            );
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String ingredientName =
                etIngredientName.getText().toString().trim();
        String quantityText =
                etQuantity.getText().toString().trim();
        String unit =
                etUnit.getText().toString().trim();
        String expiryDate =
                etExpiryDate.getText().toString().trim();
        if (ingredientName.isEmpty()) {
            etIngredientName.setError("Enter ingredient name");
            return;
        }
        if (quantityText.isEmpty()) {
            etQuantity.setError("Enter quantity");
            return;
        }
        double quantity = Double.parseDouble(quantityText);
        try {

            boolean saved;

            if(isEdit){

                saved =
                        dbHelper.updateIngredient(
                                originalIngredientName,
                                ingredientName,
                                quantity,
                                unit,
                                expiryDate
                        );
            } else {
                saved =
                        dbHelper.addIngredient(
                                ingredientName,
                                quantity,
                                unit,
                                expiryDate
                        );
            }

            if (saved) {
                Toast.makeText(
                        this,
                        "Ingredient saved successfully",
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent =
                        new Intent(
                                AddIngredientActivity.this,
                                PantryActivity.class
                        );
                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                );
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(
                        this,
                        "Failed to save ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        } catch (Exception e) {

            Toast.makeText(
                    this,
                    e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}