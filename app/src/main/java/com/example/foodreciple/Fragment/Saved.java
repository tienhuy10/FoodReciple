package com.example.foodreciple.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodreciple.Adapter.adapter_Food;
import com.example.foodreciple.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Saved extends Fragment {

    private RecyclerView recyclerView;
    private adapter_Food adapter;
    private ArrayList<String> savedFoodName = new ArrayList<>();
    private ArrayList<byte[]> savedImageFood = new ArrayList<>();
    private ArrayList<String> savedTime = new ArrayList<>();
    private ArrayList<String> savedIngredients = new ArrayList<>();
    private ArrayList<String> savedSteps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        recyclerView = view.findViewById(R.id.recycler_saved_foods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        // Load saved items
        loadSavedItems();

        // Set up adapter
        adapter = new adapter_Food(getContext(), savedFoodName, savedImageFood, savedTime, savedIngredients, savedSteps);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadSavedItems() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SavedFoods", Context.MODE_PRIVATE);
        Set<String> savedItems = sharedPreferences.getStringSet("savedFoodSet", new HashSet<>());

        // Clear current data
        savedFoodName.clear();
        savedImageFood.clear();
        savedTime.clear();
        savedIngredients.clear();
        savedSteps.clear();

        // Iterate over saved items and populate lists
        for (String item : savedItems) {
            String[] parts = item.split(";");
            if (parts.length >= 5) { // Check if array has enough elements
                savedFoodName.add(parts[0]);
                savedImageFood.add(Base64.decode(parts[1], Base64.DEFAULT));
                savedTime.add(parts[2]);
                savedIngredients.add(parts[3]);
                savedSteps.add(parts[4]);
            } else {
                // Handle invalid data case (e.g., print an error message)
                System.out.println("Invalid data format: " + item);
            }
        }

        // Notify adapter about data change
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
