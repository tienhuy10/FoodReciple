package com.example.foodreciple.Adapter;

import android.annotation.SuppressLint;
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
    private ArrayList<Integer> FoodID;
    private ArrayList<String> FoodName, Time, Ingredients, Steps;

    public Admin_adapter_food(Context context, ArrayList<byte[]> image_food, ArrayList<Integer> foodID, ArrayList<String> foodName, ArrayList<String> time, ArrayList<String> ingredients,ArrayList<String> steps) {
        this.context = context;
        this.Image_food = image_food;
        this.FoodID = foodID;
        this.FoodName = foodName;
        this.Time = time;
        this.Ingredients = ingredients;
        this.Steps = steps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_item_food, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_id_txt.setText(String.valueOf(FoodID.get(position)));
//        holder.food_title_txt.setText(FoodName.get(position));
//        holder.food_time_txt.setText(Time.get(position));
//        holder.food_ingredients_txt.setText(Ingredients.get(position));
//        holder.food_Steps_txt.setText(Steps.get(position));
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
//                intent.putExtra("ID", FoodID.get(position));
//                intent.putExtra("FoodName", FoodName.get(position));
//                intent.putExtra("Time", Time.get(position));
//                intent.putExtra("Ingredients", Ingredients.get(position));
//                intent.putExtra("Steps", Steps.get(position));
//                intent.putExtra("Image", Image_food.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FoodName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView food_title_txt, food_ingredients_txt, food_Steps_txt, food_time_txt, food_id_txt;
        ImageView img_food;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_id_txt = itemView.findViewById(R.id.food_id_txt);
            food_title_txt = itemView.findViewById(R.id.food_title_txt);
            food_ingredients_txt = itemView.findViewById(R.id.food_ingredients_txt);
            food_Steps_txt = itemView.findViewById(R.id.food_Steps_txt);
            food_time_txt = itemView.findViewById(R.id.food_time_txt);
            img_food = itemView.findViewById(R.id.image_food);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
