package com.example.foodreciple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodreciple.FoodDetails.FoodDetails;
import com.example.foodreciple.Fragment.Home;
import com.example.foodreciple.R;

import java.util.ArrayList;

public class adapter_Food extends RecyclerView.Adapter<adapter_Food.MyViewHolder> {
    private Context context;
    private ArrayList<String> FoodName;
    private ArrayList<byte[]> Image_food;
    private ArrayList<String> Time;
    private ArrayList<String> Ingredients;  // Add Ingredients
    private ArrayList<String> Steps;        // Add Steps

    public adapter_Food(Context context, ArrayList<String> foodName, ArrayList<byte[]> image_food, ArrayList<String> time, ArrayList<String> ingredients, ArrayList<String> steps) {
        this.context = context;
        this.FoodName = foodName;
        this.Image_food = image_food;
        this.Time = time;
        this.Ingredients = ingredients;
        this.Steps = steps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_foodName.setText(FoodName.get(position));
        holder.text_time.setText(Time.get(position));
        byte[] imageBlob = Image_food.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings) // Hình ảnh placeholder nếu không tải được
                .error(R.drawable.ic_baseline_settings) // Hình ảnh hiển thị khi có lỗi
                .into(holder.img_food);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, FoodDetails.class);
                    intent.putExtra("text_foodName", FoodName.get(position));
                    String imageString = Base64.encodeToString(Image_food.get(position), Base64.DEFAULT);
                    intent.putExtra("img_food", imageString);
                    intent.putExtra("text_time", Time.get(position));
                    intent.putExtra("text_ingredients", Ingredients.get(position)); // Add Ingredients
                    intent.putExtra("text_steps", Steps.get(position)); // Add Steps
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return FoodName.size();
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

