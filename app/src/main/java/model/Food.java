package model;

import java.io.Serializable;

public class Food implements Serializable {

    private static final long serialVersionUID = 10L;
    private String foodName;
    private String recordDate;
    private int calories;
    private int foodId;

    public Food(String foodName, String recordDate, int calories, int foodId) {
        this.foodName = foodName;
        this.recordDate = recordDate;
        this.calories = calories;
        this.foodId = foodId;
    }
    public Food(){
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
