package com.example.foodreciple.AdminActivity;

import android.database.Cursor;
import android.os.Bundle;

import com.example.foodreciple.Adapter.Admin_adapter_food;
import com.example.foodreciple.DatabaseHelper;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodreciple.R;
import java.util.ArrayList;

public class Admin_Food extends AppCompatActivity {
    RecyclerView recycler_inactive, recycler_active;
    ArrayList<String> FoodName_inactive, Steps_inactive, Time_inactive, Ingredients_inactive;
    ArrayList<String> FoodName_active, Steps_active, Time_active, Ingredients_active;
    ArrayList<Integer> FoodID_inactive, FoodID_active;
    ArrayList<byte[]> Image_food_inactive, Image_food_active;
    DatabaseHelper databaseHelper;
    Admin_adapter_food admin_adapter_food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food);

        databaseHelper = new DatabaseHelper(this);

        //Món ăn chưa duyệt
        FoodName_inactive = new ArrayList<>();
        Image_food_inactive = new ArrayList<>();
        Steps_inactive = new ArrayList<>();
        Time_inactive = new ArrayList<>();
        Ingredients_inactive = new ArrayList<>();

        FoodName_active = new ArrayList<>();
        Image_food_active = new ArrayList<>();
        Steps_active = new ArrayList<>();
        Time_active = new ArrayList<>();
        Ingredients_active = new ArrayList<>();

        FoodID_inactive = new ArrayList<>();
        FoodID_active = new ArrayList<>();


        recycler_inactive = findViewById(R.id.recycler_inactive);
        recycler_active = findViewById(R.id.recycler_active);
        displaydata_inactive();
        displaydata_active();

    }

    private void displaydata_inactive() {
        Cursor cursor = databaseHelper.getDataFood_INACTIVE();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                FoodID_inactive.add(Integer.valueOf(cursor.getString(0)));
                FoodName_inactive.add(cursor.getString(1));
                Time_inactive.add(cursor.getString(7));
                Ingredients_inactive.add(cursor.getString(3));
                Steps_inactive.add(cursor.getString(4));
                byte[] imageBytes = cursor.getBlob(5);
                Image_food_inactive.add(imageBytes);
            }
            admin_adapter_food = new Admin_adapter_food(this, Image_food_inactive, FoodID_inactive, FoodName_inactive,Time_inactive,Ingredients_inactive,Steps_inactive);
            recycler_inactive.setAdapter(admin_adapter_food);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            recycler_inactive.setLayoutManager(linearLayoutManager);
        }
    }

    //Đồ ăn đã duyệt
    private void displaydata_active() {
        Cursor cursor = databaseHelper.getDataFood();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                FoodID_active.add(Integer.valueOf(cursor.getString(0)));
                FoodName_active.add(cursor.getString(1));
                Time_active.add(cursor.getString(7));
                Ingredients_active.add(cursor.getString(3));
                Steps_active.add(cursor.getString(4));
                byte[] imageBytes = cursor.getBlob(5);
                Image_food_active.add(imageBytes);
            }
            admin_adapter_food = new Admin_adapter_food(this, Image_food_active, FoodID_active, FoodName_active,Time_active,Steps_active,Ingredients_active);
            recycler_active.setAdapter(admin_adapter_food);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
            recycler_active.setLayoutManager(linearLayoutManager);
        }
    }
}