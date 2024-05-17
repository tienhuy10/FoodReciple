// FoodDetailActivity.java
package com.example.foodreciple.FoodDetails;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodreciple.R;

public class FoodDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        String foodName = getIntent().getStringExtra("foodName");
        String foodTime = getIntent().getStringExtra("foodTime");
        int foodImage = getIntent().getIntExtra("foodImage", R.drawable.default_image);
        String foodIngredients = getIntent().getStringExtra("foodIngredients");
        String foodSteps = getIntent().getStringExtra("foodSteps");

        TextView detailName = findViewById(R.id.detailName);
        TextView detailTime = findViewById(R.id.detailTime);
        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailIngredients = findViewById(R.id.detailIngredients);
        TextView detailSteps = findViewById(R.id.detailSteps);

        detailName.setText(foodName);
        detailTime.setText(foodTime);
        detailImage.setImageResource(foodImage);
        detailIngredients.setText(foodIngredients);
        detailSteps.setText(foodSteps);
    }
}
