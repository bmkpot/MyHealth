package com.obenacademy.myhealth;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
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
        i.putExtra(Intent.EXTRA_SUBJECT, "My Calories Intake");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {"mailto@example.com"});
        i.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{

            startActivity(Intent.createChooser(i, "Send mail..."));

        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Please install email client to send mail",
                    Toast.LENGTH_LONG).show();

        }
    }
@Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.menu_food_item, menu);
        return true;
    }
@Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.buttonHome:
                Intent intent = new Intent(FoodItemActivity.this, MainActivity.class);
                startActivity(intent);
        }

    switch (item.getItemId()) {
        case R.id.scannerItem:
            Intent intent = new Intent(FoodItemActivity.this, ScannerActivity.class);
            startActivity(intent);

    }
        int id = item.getItemId();
        //super.onOptionsItemSelected(item);
        if (id == R.id.deleteItem){

            AlertDialog.Builder alert = new AlertDialog.Builder(FoodItemActivity.this);
            alert.setTitle("Delete?");
            alert.setMessage("Do you want to delete this item?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteFood(foodId);

                    Toast.makeText(getApplicationContext(), "Food Item Delete!", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(FoodItemActivity.this, DisplayFoodActivity.class));

                    //remove this activity from activity stack
                    FoodItemActivity.this.finish();

                }
            });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
