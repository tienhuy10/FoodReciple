package com.example.foodreciple.AdminActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.foodreciple.Activity.DangNhap;
import com.example.foodreciple.Activity.Intro;
import com.example.foodreciple.Activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodreciple.databinding.ActivityAdminMainBinding;

import com.example.foodreciple.R;

public class Admin_Main extends AppCompatActivity {
    Button btnManageFoodDetails, Home;

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminMainBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btnManageFoodDetails = findViewById(R.id.btnManageFoodDetails);
        Home = findViewById(R.id.Home);

        btnManageFoodDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Main.this, Admin_Food.class);
                startActivity(intent);
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Main.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}