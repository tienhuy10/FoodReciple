package com.example.foodreciple.AdminActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodreciple.Adapter.Admin_adapter_category;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

import java.util.ArrayList;

public class Admin_Category extends AppCompatActivity {
    RecyclerView recycler_inactive;
    ArrayList<String> CategoryName_inactive;
    ArrayList<Integer> CategoryID_inactive;
    ArrayList<byte[]> Image_category_inactive;
    DatabaseHelper databaseHelper;
    Admin_adapter_category admin_adapter_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories);

        databaseHelper = new DatabaseHelper(this);

        // Initialize arrays
        CategoryName_inactive = new ArrayList<>();
        Image_category_inactive = new ArrayList<>();
        CategoryID_inactive = new ArrayList<>();

        recycler_inactive = findViewById(R.id.recyclerViewCategories);

        displaydata_inactive();
    }

    private void displaydata_inactive() {
        Cursor cursor = databaseHelper.getDataCategory_INACTIVE();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            CategoryID_inactive = new ArrayList<>();
            CategoryName_inactive = new ArrayList<>();
            Image_category_inactive = new ArrayList<>();

            while (cursor.moveToNext()) {
                CategoryID_inactive.add(cursor.getInt(0));
                CategoryName_inactive.add(cursor.getString(1));
                byte[] imageBytes = cursor.getBlob(2);
                Image_category_inactive.add(imageBytes);
            }
            cursor.close();

            admin_adapter_category = new Admin_adapter_category(this, CategoryID_inactive, CategoryName_inactive, Image_category_inactive);
            recycler_inactive.setAdapter(admin_adapter_category);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recycler_inactive.setLayoutManager(linearLayoutManager);
        }
    }
}
