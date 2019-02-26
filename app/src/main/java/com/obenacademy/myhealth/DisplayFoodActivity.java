package com.obenacademy.myhealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.util;


public class  DisplayFoodActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Food> dbFoods = new ArrayList<>();
    private CustomListviewAdapter foodAdapter;
    private ListView listView;

    private Food myFood;
    private TextView totalCalories, totalFoods;
    private util Utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food);

        listView = (ListView) findViewById(R.id.list_item);
        totalCalories = (TextView) findViewById(R.id.totalAmountTextView);
        totalFoods = (TextView) findViewById(R.id.totalItemsTextView);

        refreshData();
    }

    private void refreshData() {
        dbFoods.clear();

        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Food> foodsFromDB = dba.getFoods();

        int caloriesValue = dba.totalCalories();
        int totalItems = dba.getTotalItems();

        String formattedValue = Utils.formatNumber(caloriesValue);
        String formattedItems = Utils.formatNumber(totalItems);

        totalCalories.setText("Total Calories: " + formattedValue);
        totalFoods.setText("Total Foods: " + formattedItems);

        for (int i = 0; i < foodsFromDB.size(); i++){

            String name = foodsFromDB.get(i).getFoodName();
            String dateText = foodsFromDB.get(i).getRecordDate();
            int calories = foodsFromDB.get(i).getCalories();
            int foodId = foodsFromDB.get(i).getFoodId();

            myFood = new Food();
            myFood.setFoodName(name);
            myFood.setRecordDate(dateText);
            myFood.setCalories(calories);
            myFood.setFoodId(foodId);

            dbFoods.add(myFood);
        }
        dba.close();
       // ((DatabaseHandler) dba).close();

        //setup adapter

        foodAdapter = new CustomListviewAdapter(DisplayFoodActivity.this, R.layout.list_row, dbFoods);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }

}
