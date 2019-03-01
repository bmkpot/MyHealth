package com.obenacademy.myhealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import model.Food;

public class FoodItemActivity extends AppCompatActivity {

    private TextView foodName, calories, dateTaken;
    private Button shareButton;
    private int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        foodName = (TextView) findViewById(R.id.foodItemName);
        calories = (TextView) findViewById(R.id.caloriesValueText);
        dateTaken = (TextView) findViewById(R.id.consumeDateText);
        shareButton = (Button) findViewById(R.id.shareButtonText);

        Food food = (Food) getIntent().getSerializableExtra("userObject");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        foodId = food.getFoodId();

        calories.setTextSize(40.2f);
        calories.setTextColor(Color.RED);
    }
}
