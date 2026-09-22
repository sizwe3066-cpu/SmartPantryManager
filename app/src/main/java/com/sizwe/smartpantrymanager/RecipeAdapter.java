package com.sizwe.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<String> {

    public RecipeAdapter(
            Context context,
            ArrayList<String> recipes) {

        super(
                context,
                0,
                recipes
        );
    }

    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView =
                    LayoutInflater.from(
                            getContext()
                    ).inflate(
                            android.R.layout.simple_list_item_1,
                            parent,
                            false
                    );
        }

        TextView txtRecipe =
                convertView.findViewById(
                        android.R.id.text1
                );

        txtRecipe.setText(
                getItem(position)
        );

        return convertView;
    }
}