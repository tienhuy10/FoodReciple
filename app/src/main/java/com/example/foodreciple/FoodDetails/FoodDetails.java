package com.example.foodreciple.FoodDetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodreciple.R;

public class FoodDetails extends AppCompatActivity {
    TextView detailName, detailTime, detailIngredients, detailSteps;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        detailName = findViewById(R.id.detailName);
        detailTime = findViewById(R.id.detailTime);
        detailIngredients = findViewById(R.id.detailIngredients);
        detailSteps = findViewById(R.id.detailSteps);
        detailImage = findViewById(R.id.detailImage);

        Intent intent = getIntent();
        String text_foodName = intent.getStringExtra("text_foodName");
        String img_food = intent.getStringExtra("img_food");
        String text_time = intent.getStringExtra("text_time");
        String text_ingredients = intent.getStringExtra("text_ingredients");
        String text_steps = intent.getStringExtra("text_steps");

        detailName.setText(text_foodName);
        detailTime.setText(text_time);
        detailIngredients.setText(text_ingredients);
        detailSteps.setText(text_steps);


        byte[] imageBytes = Base64.decode(img_food, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        detailImage.setImageBitmap(bitmap);
    }
}
