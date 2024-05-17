package com.example.foodreciple.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodreciple.R;

import java.util.ArrayList;
import java.util.List;

public class adapter_image_slider extends RecyclerView.Adapter<adapter_image_slider.ViewHolder> {
    private Context context;
    private ArrayList<byte[]> Image_food;

    public adapter_image_slider(Context context, ArrayList<byte[]> image_food) {
        this.context = context;
        Image_food = image_food;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        byte[] imageBlob = Image_food.get(position);


        // Load image using Glide
        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings) // Hình ảnh placeholder nếu không tải được
                .error(R.drawable.ic_baseline_settings) // Hình ảnh hiển thị khi có lỗi
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return Image_food.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
