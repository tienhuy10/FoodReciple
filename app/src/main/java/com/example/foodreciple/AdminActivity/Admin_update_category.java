package com.example.foodreciple.AdminActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Admin_update_category extends AppCompatActivity {

    private EditText categoryName;
    private ImageView imageView;
    private Button updateButton;
    private Button chooseImageButton;
    private String imagePath; // Biến lưu đường dẫn của hình ảnh

    // Định nghĩa mã yêu cầu cho việc chọn hình ảnh từ bộ nhớ
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_category);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        imageView = findViewById(R.id.image);
        categoryName = findViewById(R.id.category_name);
        updateButton = findViewById(R.id.update_category);
        chooseImageButton = findViewById(R.id.choose_image);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String category_Name = intent.getStringExtra("CategoryName");
        byte[] image = intent.getByteArrayExtra("Image");
        int categoryID = intent.getIntExtra("ID", -1);

        categoryName.setText(category_Name);

        Glide.with(this).load(image).into(imageView);

        // Gắn sự kiện cho nút "Chọn hình ảnh"
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật danh mục
                if (categoryID != -1) {
                    byte[] imageData = convertImagePathToByteArray(imagePath);
                    if (imageData != null) {
                        updateCategory(categoryID, categoryName.getText().toString(), imageData);
                    } else {
                        Toast.makeText(Admin_update_category.this, "Không thể chuyển đổi hình ảnh sang mảng byte", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Admin_update_category.this, "ID danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteButton = findViewById(R.id.delete_category);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa danh mục
                deleteCategory(categoryID);
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của hình ảnh đã chọn
            Uri selectedImageUri = data.getData();

            // Hiển thị hình ảnh đã chọn lên ImageView
            Glide.with(this).load(selectedImageUri).into(imageView);

            // Lưu đường dẫn của hình ảnh vào biến imagePath
            imagePath = selectedImageUri.toString();
        }
    }

    private void deleteCategory(int categoryID) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean result = databaseHelper.deleteCategory(categoryID);
        if (result) {
            Toast.makeText(Admin_update_category.this, "Xóa danh mục thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_update_category.this, Admin_Category.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Admin_update_category.this, "Xóa danh mục thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCategory(int categoryID, String categoryName, byte[] image) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this); // Tạo đối tượng DatabaseHelper

        // Gọi phương thức updateCategory() từ đối tượng DatabaseHelper
        boolean result = databaseHelper.updateCategory(categoryID, categoryName, image);

        if (result) {
            Toast.makeText(Admin_update_category.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Admin_update_category.this, Admin_Category.class); // Quay lại activity trước đó
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Admin_update_category.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] convertImagePathToByteArray(String imagePath) {
        if (imagePath == null) {
            return null;
        }

        try {
            Uri uri = Uri.parse(imagePath);
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
