package com.example.foodreciple.AdminActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.foodreciple.Activity.MainActivity;
import com.example.foodreciple.databinding.ActivityAdminMainBinding;

import com.example.foodreciple.R;

public class Admin_Main extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Button btnManageCategory = findViewById(R.id.btnManageFoodCategories);
        Button btnManageFoodDetails = findViewById(R.id.btnManageFoodDetails);
        Button Home = findViewById(R.id.Home);

        // Thiết lập sự kiện click cho nút
        btnManageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Main.this, Admin_Category.class);
                startActivity(intent);
            }
        });
        btnManageFoodDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Main.this, Admin_Food.class);
                startActivity(intent);
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Main.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
