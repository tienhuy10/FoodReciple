package com.example.foodreciple.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodreciple.R;

public class Intro extends AppCompatActivity {
    Button btn_start, btn_Dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        AnhXa();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this, DangNhap.class);
                startActivity(intent);
            }
        });

        btn_Dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this, DangKy.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btn_start = findViewById(R.id.btn_start);
        btn_Dangky = findViewById(R.id.btn_Dangky);
    }
}