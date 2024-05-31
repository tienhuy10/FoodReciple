package com.example.foodreciple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodreciple.AdminActivity.Admin_update_food;
import com.example.foodreciple.R;

import java.util.ArrayList;

public class Admin_adapter_food extends RecyclerView.Adapter<Admin_adapter_food.MyViewHolder>{
    private Context context;
    private ArrayList<byte[]> Image_food;
    private ArrayList<String> FoodName, Time, Ingredients, Steps, BestFood, UserID;

    public Admin_adapter_food(Context context, ArrayList<byte[]> image_food, ArrayList<String> foodName) {
        this.context = context;
        this.Image_food = image_food;
        this.FoodName = foodName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_item_food, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.food_title_txt.setText(FoodName.get(position));
        byte[] imageBlob = Image_food.get(position);

        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings)
                .error(R.drawable.ic_baseline_settings)
                .into(holder.img_food);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Admin_update_food.class);

            }
        });

    }

    @Override
    public int getItemCount() {
        return FoodName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView food_title_txt;
        ImageView img_food;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_title_txt = itemView.findViewById(R.id.food_title_txt);
            img_food = itemView.findViewById(R.id.image_food);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


}
