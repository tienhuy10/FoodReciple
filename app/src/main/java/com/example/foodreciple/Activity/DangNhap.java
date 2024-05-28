package com.example.foodreciple.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodreciple.AdminActivity.Admin_Main;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.Model.User;
import com.example.foodreciple.R;
import com.google.android.material.textfield.TextInputEditText;

public class DangNhap extends AppCompatActivity {
    TextInputEditText edt_username, edt_password;
    Button btn_DangNhap;
    TextView txt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        AnhXa();

        DatabaseHelper DatabaseHelper = new DatabaseHelper(this);

        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                if (username.equals("") || password.equals(""))
                    Toast.makeText(DangNhap.this, "Vui lòng nhập tên tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                else {
                    User user = DatabaseHelper.Login_admin(username, password);
                    if (user != null) {
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        if (user.phanquyen == 2) {
                            // Nếu là admin
                            Intent intent = new Intent(getApplicationContext(), Admin_Main.class);
                            startActivity(intent);
                        } else {
                            // Nếu không phải admin
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(DangNhap.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });


    }

    private void AnhXa() {
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        txt_signup = findViewById(R.id.txt_signup);
    }


}