package com.sizwe.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.Switch;

import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Switch switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        Switch switchNotifications =
                findViewById(R.id.switchNotifications);

        SharedPreferences preferences =
                getSharedPreferences(
                        "PantrySettings",
                        MODE_PRIVATE
                );

        RadioGroup radioUnits =
                findViewById(R.id.radioUnits);

        RadioButton rbMetric =
                findViewById(R.id.rbMetric);

        RadioButton rbImperial =
                findViewById(R.id.rbImperial);

        String savedUnit =
                preferences.getString(
                        "preferredUnit",
                        "Metric"
                );

        if(savedUnit.equals("Metric")){
            rbMetric.setChecked(true);
        }
        else{
            rbImperial.setChecked(true);
        }

        radioUnits.setOnCheckedChangeListener(
                (group, checkedId) -> {

                    String selectedUnit =
                            "Metric";

                    if(checkedId ==
                            R.id.rbImperial){

                        selectedUnit =
                                "Imperial";
                    }

                    preferences.edit()
                            .putString(
                                    "preferredUnit",
                                    selectedUnit
                            )
                            .apply();
                }
        );

        switchExpiryAlerts.setChecked(
                preferences.getBoolean(
                        "expiryAlerts",
                        false
                )
        );

        switchNotifications.setChecked(
                preferences.getBoolean(
                        "notifications",
                        false
                )
        );

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean(
                                    "expiryAlerts",
                                    isChecked
                            )
                            .apply();
                }
        );
        switchNotifications.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean(
                                    "notifications",
                                    isChecked
                            )
                            .apply();
                }
        );

        Button btnBack =
                findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());



    }



}