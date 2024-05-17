package com.example.foodreciple.Fragment;

import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodreciple.Adapter.adapter_Food;
import com.example.foodreciple.Adapter.adapter_image_slider;
import com.example.foodreciple.DatabaseHelper;
import com.example.foodreciple.R;
import com.example.foodreciple.Adapter.adapter_Category;

import java.util.ArrayList;

public class Home extends Fragment {

    private ViewPager2 viewPager2;
    private adapter_image_slider imagePagerAdapter;
    RecyclerView recyview_category, recyview_new_food;
    ArrayList<String> CategoryName, FoodName, Time;
    ArrayList<byte[]> Image, Image_food, Image_slider;
    DatabaseHelper databaseHelper;
    adapter_Category adapter_category;
    adapter_Food adapter_food;

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

        Image_slider = new ArrayList<>();

        CategoryName = new ArrayList<>();
        Image = new ArrayList<>();

        FoodName = new ArrayList<>();
        Image_food = new ArrayList<>();
        Time = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager2 = view.findViewById(R.id.viewPager2);
        recyview_category = view.findViewById(R.id.recyview_category);
        recyview_new_food = view.findViewById(R.id.recyview_new_food);
        display_image_slider();
        autoScrollImages();
        displaydata_category();
        displaydata_food();
        return view;
    }

    private void display_image_slider() {
        Cursor cursor = databaseHelper.getDataImage();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                byte[] imageBytes = cursor.getBlob(0);
                Image_slider.add(imageBytes);
            }
            imagePagerAdapter = new adapter_image_slider(getContext(), Image_slider);
            viewPager2.setAdapter(imagePagerAdapter);
        }
    }

    private void autoScrollImages() {
        final int speedScroll = 3000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if (count == imagePagerAdapter.getItemCount()) {
                    count = 0;
                }
                if (count < imagePagerAdapter.getItemCount()) {
                    viewPager2.setCurrentItem(++count, true);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    private void displaydata_food() {
        Cursor cursor = databaseHelper.getDataFood();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                FoodName.add(cursor.getString(1));
                byte[] imageBytes = cursor.getBlob(5); // Lấy dữ liệu Blob dưới dạng byte array
                Image_food.add(imageBytes); // Thêm byte array vào danh sách Image
                Time.add(cursor.getString(6));
            }
            adapter_food = new adapter_Food(getContext(), FoodName, Image_food, Time);
            recyview_new_food.setAdapter(adapter_food);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            recyview_new_food.setLayoutManager(layoutManager);
        }
    }

    private void displaydata_category() {
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
            adapter_category = new adapter_Category(getContext(), CategoryName, Image);
            recyview_category.setAdapter(adapter_category);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
            recyview_category.setLayoutManager(linearLayoutManager);
        }
    }
}
