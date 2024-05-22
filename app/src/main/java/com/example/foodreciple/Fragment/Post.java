package com.example.foodreciple.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Post extends Fragment {
    DatabaseHelper databaseHelper;
    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> CategoryName;
    Spinner spinnerCategory;
    EditText categoryNameEditText, edt_nguyen_lieu, edt_step, edt_time;
    Button addButton, choose_image;
    ImageView image;

    public Post() {
        // Required empty public constructor
    }

    public static Post newInstance(String param1, String param2) {
        Post fragment = new Post();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
        CategoryName = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        spinnerCategory = view.findViewById(R.id.spinner_category);
        categoryNameEditText = view.findViewById(R.id.category_name);
        addButton = view.findViewById(R.id.add_cate);
        choose_image = view.findViewById(R.id.choose_image);
        image = view.findViewById(R.id.image);
        edt_nguyen_lieu = view.findViewById(R.id.nguyen_lieu);
        edt_step = view.findViewById(R.id.edt_step);
        edt_time = view.findViewById(R.id.edt_time);

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
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        return view;
    }

    private void loadCategoriesIntoSpinner() {
        Cursor cursor = databaseHelper.getDataCategory();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                CategoryName.add(cursor.getString(1));
            }
            // Tạo ArrayAdapter và thiết lập nó cho Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, CategoryName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);
        }
    }

    private void addFood() {
        String foodName = categoryNameEditText.getText().toString().trim();
        int selectedCategoryId = (int) spinnerCategory.getSelectedItemId() + 1;
        String Ingredients = edt_nguyen_lieu.getText().toString().trim();
        String Step = edt_step.getText().toString().trim();
        String Time = edt_time.getText().toString().trim();
        byte[] img = imageViewToByte(image);

        if (!foodName.isEmpty()) {
            // Thêm dữ liệu vào bảng thức ăn
            databaseHelper.insertDataFood(foodName, selectedCategoryId, Ingredients, Step, img, Time); // Chú ý: Truyền foodName, selectedCategoryId và image vào phương thức insertData()

            Toast.makeText(getActivity(), "Dữ liệu thức ăn đã được thêm vào!", Toast.LENGTH_SHORT).show();
            categoryNameEditText.setText("");
            edt_nguyen_lieu.setText("");
            edt_step.setText("");
            edt_time.setText("");
            image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Toast.makeText(getActivity(), "Vui lòng nhập tên thức ăn!", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getActivity(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
