package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sizwe.smartpantrymanager.database.DatabaseHelper;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private Button btnSaveIngredient;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        dbHelper = new DatabaseHelper(this);

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

            boolean saved = dbHelper.addIngredient(
                    ingredientName,
                    quantity,
                    unit,
                    expiryDate
            );

            if (saved) {

                Toast.makeText(
                        this,
                        "Ingredient saved successfully",
                        Toast.LENGTH_SHORT
                ).show();

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