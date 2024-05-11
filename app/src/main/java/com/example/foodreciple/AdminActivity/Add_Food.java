package com.example.foodreciple.AdminActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodreciple.Adapter.adapter_Category;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Add_Food extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> CategoryName;
    Spinner spinnerCategory;
    EditText categoryNameEditText;
    Button addButton, choose_image;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        spinnerCategory = findViewById(R.id.spinner_category);
        categoryNameEditText = findViewById(R.id.category_name);
        addButton = findViewById(R.id.add_cate);
        choose_image = findViewById(R.id.choose_image);
        image = findViewById(R.id.image);

        databaseHelper = new DatabaseHelper(this);
        CategoryName = new ArrayList<>();

        // Hiển thị danh sách danh mục vào Spinner
        loadCategoriesIntoSpinner();

        // Xử lý sự kiện khi nhấn vào nút Thêm
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Add_Food.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    private void loadCategoriesIntoSpinner() {
        Cursor cursor = databaseHelper.getDataCategory();
        Spinner spinner = findViewById(R.id.spinner_category);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                CategoryName.add(cursor.getString(1));
            }
            // Tạo ArrayAdapter và thiết lập nó cho Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategoryName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    private void addFood() {
        String foodName = categoryNameEditText.getText().toString().trim();
        int selectedCategoryId = (int) spinnerCategory.getSelectedItemId() + 1; // Lấy ID của danh mục được chọn trong Spinner
        byte[] img  = imageViewToByte(image);

        if (!foodName.isEmpty()) {
            // Thêm dữ liệu vào bảng thức ăn
            databaseHelper.insertDataFood(foodName, selectedCategoryId, img); // Chú ý: Truyền foodName, selectedCategoryId và image vào phương thức insertData()

            Toast.makeText(this, "Dữ liệu thức ăn đã được thêm vào!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng nhập tên thức ăn!", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
