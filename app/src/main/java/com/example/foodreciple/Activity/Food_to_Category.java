package com.example.foodreciple.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodreciple.Adapter.adapter_Food;
import com.example.foodreciple.Adapter.adapter_food_to_category;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

import java.util.ArrayList;

public class Food_to_Category extends AppCompatActivity {
    TextView CategoryName;
    RecyclerView recyview_category_food;
    DatabaseHelper databaseHelper;
    ArrayList<String> FoodName, Time;
    ArrayList<byte[]> Image_food;
    adapter_food_to_category adapter_food_to_category;
    adapter_Food adapter_food;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_to_category);

        CategoryName = findViewById(R.id.CategoryName);
        recyview_category_food = findViewById(R.id.recyview_category_food);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        String Category_Name = intent.getStringExtra("CategoryName");
        int categoryId = intent.getIntExtra("ID", -1);

        CategoryName.setText(Category_Name);

        FoodName = new ArrayList<>();
        Image_food = new ArrayList<>();
        Time = new ArrayList<>();

        Cursor cursor = databaseHelper.getDataFoodByCategory(categoryId);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ArrayList<String> Ingredients = new ArrayList<>();
            ArrayList<String> Steps = new ArrayList<>();

            while (cursor.moveToNext()) {
                FoodName.add(cursor.getString(1));
                byte[] imageBytes = cursor.getBlob(5);
                Image_food.add(imageBytes);
                Time.add(cursor.getString(7));
                Ingredients.add(cursor.getString(3)); // Assuming column 3 is for ingredients
                Steps.add(cursor.getString(4));      // Assuming column 4 is for steps
            }
            adapter_food = new adapter_Food(this, FoodName, Image_food, Time, Ingredients, Steps);
            recyview_category_food.setAdapter(adapter_food);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            recyview_category_food.setLayoutManager(layoutManager);
        }

    }

//    private void displaydata_food() {
//        Cursor cursor = databaseHelper.getDataFoodByCategory(categoryId);
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            ArrayList<String> Ingredients = new ArrayList<>();
//            ArrayList<String> Steps = new ArrayList<>();
//
//            while (cursor.moveToNext()) {
//                FoodName.add(cursor.getString(1));
//                byte[] imageBytes = cursor.getBlob(5);
//                Image_food.add(imageBytes);
//                Time.add(cursor.getString(7));
//                Ingredients.add(cursor.getString(3)); // Assuming column 3 is for ingredients
//                Steps.add(cursor.getString(4));      // Assuming column 4 is for steps
//            }
//            adapter_food = new adapter_Food(this, FoodName, Image_food, Time, Ingredients, Steps);
//            recyview_category_food.setAdapter(adapter_food);
//            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//            recyview_category_food.setLayoutManager(layoutManager);
//        }
//    }
}