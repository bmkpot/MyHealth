package com.obenacademy.myhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName, foodCalories;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCalories = (EditText) findViewById(R.id.caloriesEditText);
        Button submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }

        });
    }

    private void saveDataToDB() {

        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calsString = foodCalories.getText().toString().trim();

        int calories = Integer.parseInt(calsString);

        if (name.equals("") || calsString.equals("")){

            Toast.makeText(getApplicationContext(), "Empty fields not allowed", Toast.LENGTH_LONG).show();

        }else {
            food.setFoodName(name);
            food.setCalories(calories);

            dba.addFood(food);
            dba.close();

            //clear the form
            foodName.setText("");
            foodCalories.setText("");

            //take users to next screen (display all entered items)
           startActivity(new Intent(MainActivity.this, DisplayFoodActivity.class));
        }


        }

    }

