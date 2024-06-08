package com.example.foodreciple.AdminActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

public class Admin_update_food extends AppCompatActivity {

    private EditText foodName, ingredients, steps, time;
    private ImageView imageView;
    private Button updateButton;
    private Spinner statusSpinner, Spiner_bestFood;
    private int selectedStatus, selected_bestFood; //mac dinh la 0

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_food);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        imageView = findViewById(R.id.image);
        foodName = findViewById(R.id.food_name);
        ingredients = findViewById(R.id.nguyen_lieu);
        steps = findViewById(R.id.edt_step);
        time = findViewById(R.id.edt_time);
        updateButton = findViewById(R.id.update_food);
        statusSpinner = findViewById(R.id.status_spinner);
        Spiner_bestFood = findViewById(R.id.best_food_spinner);

        // Thiết lập Adapter cho Spinner duyệt đồ ăn
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStatus = 0; // Mặc định là "Không duyệt"
            }
        });

        // Thiết lập Adapter cho Spinner duyệt đồ ăn ngon
        ArrayAdapter<CharSequence> adapter_bestfood = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter_bestfood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spiner_bestFood.setAdapter(adapter_bestfood);

        Spiner_bestFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_bestFood = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_bestFood = 0; // Mặc định là "do an thuong"
            }
        });

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String food_Name = intent.getStringExtra("FoodName");
        String thoi_gian = intent.getStringExtra("Time");
        String nguyen_lieu = intent.getStringExtra("Ingredients");
        String cac_buoc = intent.getStringExtra("Steps");
        byte[] image = intent.getByteArrayExtra("Image");
        int foodID = intent.getIntExtra("ID", -1);

        foodName.setText(food_Name);
        time.setText(thoi_gian);
        ingredients.setText(nguyen_lieu);
        steps.setText(cac_buoc);
        Glide.with(this).load(image).into(imageView);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật món ăn
                if (foodID != -1) {
                    updateFood(foodID);
                } else {
                    Toast.makeText(Admin_update_food.this, "ID món ăn không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteButton = findViewById(R.id.delete_food);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa món ăn
                deleteFood(foodID);
            }
        });
    }

    private void deleteFood(int foodID) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean result = databaseHelper.deleteFood(foodID);
        if (result) {
            Toast.makeText(Admin_update_food.this, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_update_food.this, Admin_Food.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Admin_update_food.this, "Xóa món ăn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFood(int foodID) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Tạo đối tượng DatabaseHelper
        String updatedFoodName = foodName.getText().toString();
        String updatedIngredients = ingredients.getText().toString();
        String updatedSteps = steps.getText().toString();
        String updatedTime = time.getText().toString();

        // Gọi phương thức updateFood() từ đối tượng DatabaseHelper
        boolean result = databaseHelper.updateFood(foodID, updatedFoodName, updatedIngredients, updatedSteps, updatedTime, selected_bestFood, selectedStatus);

        if (result) {
            Toast.makeText(Admin_update_food.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_update_food.this, Admin_Food.class); // Replace with your previous activity
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Admin_update_food.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }

        // Đóng Activity sau khi cập nhật
        finish();
    }
}
