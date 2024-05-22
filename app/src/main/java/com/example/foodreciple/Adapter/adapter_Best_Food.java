package com.example.foodreciple.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodreciple.R;


import java.util.ArrayList;

public class adapter_Best_Food extends RecyclerView.Adapter<adapter_Best_Food.MyViewHolder> {
    private Context context;
    private ArrayList<String> bestFood_name;
    private ArrayList<byte[]> bestFood_image;
    private ArrayList<String> bestFood_time;

    public adapter_Best_Food(Context context, ArrayList<String> bestFood_name, ArrayList<byte[]> bestFood_image, ArrayList<String> bestFood_time) {
        this.context = context;
        this.bestFood_name = bestFood_name;
        this.bestFood_image = bestFood_image;
        this.bestFood_time = bestFood_time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_foodName.setText(String.valueOf(bestFood_name.get(position)));
        holder.text_time.setText(String.valueOf(bestFood_time.get(position)));
        byte[] imageBlob = bestFood_image.get(position);


        // Load image using Glide
        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings) // Hình ảnh placeholder nếu không tải được
                .error(R.drawable.ic_baseline_settings) // Hình ảnh hiển thị khi có lỗi
                .into(holder.img_food);

    }

    @Override
    public int getItemCount() {
        return bestFood_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_foodName, text_time;
        ImageView img_food;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_foodName = itemView.findViewById(R.id.text_foodName);
            img_food = itemView.findViewById(R.id.img_food);
            text_time = itemView.findViewById(R.id.txt_time);
        }
    }
}