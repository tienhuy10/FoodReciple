package com.example.foodreciple.AdminActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodreciple.Activity.DangNhap;
import com.example.foodreciple.Activity.Intro;
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
    Button btnManageFoodDetails;

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btnManageFoodDetails = findViewById(R.id.btnManageFoodDetails);

        btnManageFoodDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Main.this, Admin_Food.class);
                startActivity(intent);
            }
        });

    }
}