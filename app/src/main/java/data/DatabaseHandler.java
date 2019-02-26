package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Food> foodArrayList = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Table
        String CREATE_TABLE = "CREATE TABLE " + DatabaseConstants.TABLE_NAME + "(" +
                DatabaseConstants.UID + " INTEGER PRIMARY KEY," + DatabaseConstants.FOOD_NAME +
                " TEXT," + DatabaseConstants.CALORIES_FOOD_NAME + " INT," + DatabaseConstants.DATE_NAME +
                " LONG); ";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.TABLE_NAME);

        //Create a new table
        onCreate(db);

    }

    //Get total items saved
    public int getTotalItems(){
        int totalItems = 0;

        String query = "SELECT * FROM " + DatabaseConstants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();

        cursor.close();

        return totalItems;
    }

    //Get total calories consumed
    public int totalCalories(){
        int calories = 0;

        SQLiteDatabase dba = this.getReadableDatabase();

        String query = "SELECT SUM(" + DatabaseConstants.CALORIES_FOOD_NAME + ") " +
                "FROM " + DatabaseConstants.TABLE_NAME;

        Cursor cursor = dba.rawQuery(query, null);

        if (cursor.moveToFirst()){
            calories = cursor.getInt(0);
        }

        cursor.close();
        dba.close();

        return calories;
    }
    //delete food item
    public void deleteFood(int foodId) {

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(DatabaseConstants.TABLE_NAME, DatabaseConstants.UID + " = ?",
                new String[]{ String.valueOf(foodId)});

        dba.close();
    }

    // Add food to db

    public void addFood(Food foodName){

        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.FOOD_NAME, foodName.getFoodName());
        values.put(DatabaseConstants.CALORIES_FOOD_NAME, foodName.getCalories());
        values.put(DatabaseConstants.DATE_NAME, System.currentTimeMillis());

        long insert = dba.insert(DatabaseConstants.TABLE_NAME, null, values);

        Log.v("Food item added","Yes");

        dba.close();
    }

    //Get all foods
    public ArrayList<Food> getFoods(){
        foodArrayList.clear();

        SQLiteDatabase dba = this.getReadableDatabase();

        Cursor cursor = dba.query(DatabaseConstants.TABLE_NAME,
                new String[]{DatabaseConstants.UID, DatabaseConstants.FOOD_NAME, DatabaseConstants.CALORIES_FOOD_NAME,
                        DatabaseConstants.DATE_NAME}, null, null, null, null,
                DatabaseConstants.DATE_NAME + " DESC");

        // loop through...
        if (cursor.moveToFirst()) {

            do {
                Food foodName = new Food();
                foodName.setFoodName(cursor.getString(cursor.getColumnIndex(DatabaseConstants.FOOD_NAME)));
                foodName.setCalories(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.CALORIES_FOOD_NAME)));
                foodName.setFoodId(cursor.getInt(cursor.getColumnIndex(DatabaseConstants.UID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String recordDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(DatabaseConstants.DATE_NAME))).getTime());

                foodName.setRecordDate(recordDate);

                foodArrayList.add(foodName);


            } while (cursor.moveToNext());
        }
        cursor.close();
        dba.close();

        return foodArrayList;
    }
}

