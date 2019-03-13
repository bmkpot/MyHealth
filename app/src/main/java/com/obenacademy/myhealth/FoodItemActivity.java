package com.obenacademy.myhealth;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        shareButton = (Button) findViewById(R.id.shareBtText);

        Food food = (Food) getIntent().getSerializableExtra("userObject");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        foodId = food.getFoodId();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCals();
            }
        });


    }
    public void shareCals(){
        StringBuilder dataString = new StringBuilder();

        String name = foodName.getText().toString();
        String cals = calories.getText().toString();
        String date = dateTaken.getText().toString();

        dataString.append(" Food: " + name + "\n");
        dataString.append(" Calories: " + cals + "\n");
        dataString.append(" Date Eaten: " + date);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "My Weekly Calories Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"mailto@example.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{

            startActivity(Intent.createChooser(i, "Send mail..."));

        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Please install the email client to send mail",
                    Toast.LENGTH_LONG).show();

        }
    }
}
