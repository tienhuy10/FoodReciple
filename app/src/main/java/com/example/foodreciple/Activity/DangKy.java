package com.example.foodreciple.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodreciple.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodreciple.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class DangKy extends AppCompatActivity {
    TextInputEditText signupUsername, signupEmail, signupPassword;
    Button singup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        DatabaseHelper DatabaseHelper = new DatabaseHelper(this);
        AnhXa();

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String username = signupUsername.getText().toString();

                if(email.equals("") || password.equals("") || username.equals("")) {
                    Toast.makeText(DangKy.this, "Vùi lòng nhập các thông tin", Toast.LENGTH_SHORT).show();
                } else {

                    boolean checkUserEmail = DatabaseHelper.checkEmail(email);
                    boolean checkUsername = DatabaseHelper.checkUser(username);

                    if(!checkUserEmail) {
                         if(!checkUsername) {
                            boolean insert = DatabaseHelper.SingUp(email, password, username);

                            if(insert) {
                                Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),DangNhap.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DangKy.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKy.this, "Tên người dùng đã tồn tại! Vui lòng chọn tên người dùng khác", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangKy.this, "Email này đã được đăng ký! Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }
    
    private void AnhXa() {
        signupUsername = findViewById(R.id.signupUsername);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        singup = findViewById(R.id.singup);
        login = findViewById(R.id.login);
    }
}