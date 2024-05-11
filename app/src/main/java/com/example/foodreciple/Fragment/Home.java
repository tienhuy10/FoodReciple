package com.example.foodreciple.Fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;
import com.example.foodreciple.Adapter.adapter_Category;

import java.util.ArrayList;

public class Home extends Fragment {
    RecyclerView recyview_category;
    ArrayList<String> CategoryName;
    ArrayList<byte[]> Image;
    DatabaseHelper databaseHelper;
    adapter_Category adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        CategoryName = new ArrayList<>();
        Image = new ArrayList<>(); // Khởi tạo Image
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyview_category = view.findViewById(R.id.recyview_category);
        displaydata(); // Gọi displaydata() ở đây để lấy dữ liệu từ database
        return view;
    }

    private void displaydata() {
        Cursor cursor = databaseHelper.getDataCategory();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                CategoryName.add(cursor.getString(1));
                byte[] imageBytes = cursor.getBlob(2); // Lấy dữ liệu Blob dưới dạng byte array
                Image.add(imageBytes); // Thêm byte array vào danh sách Image
            }
            adapter = new adapter_Category(getContext(), CategoryName, Image);
            recyview_category.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            recyview_category.setLayoutManager(linearLayoutManager);
        }
    }
}
