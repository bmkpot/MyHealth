package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.obenacademy.myhealth.FoodItemActivity;
import com.obenacademy.myhealth.R;

import java.util.ArrayList;

import model.Food;

public class CustomListviewAdapter extends ArrayAdapter<Food> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Food> foodArrayList = new ArrayList<>();

    public CustomListviewAdapter(Activity act, int resource, ArrayList<Food> data) {
        super(act, resource, data);

        layoutResource = resource;
        activity = act;
        foodArrayList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodArrayList.size();
    }

    @Override
    public Food getItem(int position) {
        return foodArrayList.get(position);
    }

    @Override
    public int getPosition(Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null || (row.getTag() == null)) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.foodName = (TextView) row.findViewById(R.id.name);
            holder.foodDate = (TextView) row.findViewById(R.id.dateText);
            holder.foodCalories = (TextView) row.findViewById(R.id.calories);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.food = getItem(position);

        holder.foodName.setText(holder.food.getFoodName());
        holder.foodDate.setText(holder.food.getRecordDate());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener(){
         @Override
                 public void onClick(View v){

             Intent i = new Intent(activity, FoodItemActivity.class);

             Bundle mBundle = new Bundle();
             mBundle.putSerializable("userObject", finalHolder.food);
             i.putExtras(mBundle);

             activity.startActivity(i);
        }

    });
        return row;
    }
        public class ViewHolder {
            Food food;
            TextView foodName;
            TextView foodCalories;
            TextView foodDate;
        }
    }
