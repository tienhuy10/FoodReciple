package com.example.foodreciple.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodreciple.FoodDetails.FoodDetails;
import com.example.foodreciple.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class adapter_Best_Food extends RecyclerView.Adapter<adapter_Best_Food.MyViewHolder> {
    private Context context;
    private ArrayList<String> bestFood_name;
    private ArrayList<byte[]> bestFood_image;
    private ArrayList<String> bestFood_time;
    private ArrayList<String> bestFood_ingredients;
    private ArrayList<String> bestFood_steps;

    private Set<String> savedItems = new HashSet<>();

    public adapter_Best_Food(Context context, ArrayList<String> bestFood_name, ArrayList<byte[]> bestFood_image, ArrayList<String> bestFood_time, ArrayList<String> bestFood_ingredients, ArrayList<String> bestFood_steps) {
        this.context = context;
        this.bestFood_name = bestFood_name;
        this.bestFood_image = bestFood_image;
        this.bestFood_time = bestFood_time;
        this.bestFood_ingredients = bestFood_ingredients;
        this.bestFood_steps = bestFood_steps;

        SharedPreferences sharedPreferences = context.getSharedPreferences("SavedFoods", Context.MODE_PRIVATE);
        savedItems = sharedPreferences.getStringSet("savedFoodSet", new HashSet<>());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_foodName.setText(bestFood_name.get(position));
        holder.text_time.setText(bestFood_time.get(position));
        byte[] imageBlob = bestFood_image.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings)
                .error(R.drawable.ic_baseline_settings)
                .into(holder.img_food);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, FoodDetails.class);
                    intent.putExtra("text_foodName", bestFood_name.get(position));
                    String imageString = Base64.encodeToString(bestFood_image.get(position), Base64.DEFAULT);
                    intent.putExtra("img_food", imageString);
                    intent.putExtra("text_time", bestFood_time.get(position));
                    intent.putExtra("text_ingredients", bestFood_ingredients.get(position));
                    intent.putExtra("text_steps", bestFood_steps.get(position));
                    context.startActivity(intent);
                }
            }
        });

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String foodName = bestFood_name.get(adapterPosition);
                    String imageString = Base64.encodeToString(bestFood_image.get(adapterPosition), Base64.DEFAULT);
                    String time = bestFood_time.get(adapterPosition);
                    String ingredients = bestFood_ingredients.get(adapterPosition);
                    String steps = bestFood_steps.get(adapterPosition);

                    String item = foodName + ";" + imageString + ";" + time + ";" + ingredients + ";" + steps;

                    if (savedItems.contains(item)) {
                        savedItems.remove(item);
                        holder.btnSave.setImageResource(R.drawable.bookmark); // Replace with the unfilled bookmark icon
                    } else {
                        savedItems.add(item);
                        holder.btnSave.setImageResource(R.drawable.bookmark); // Replace with the filled bookmark icon
                    }

                    // Notify adapter about item change
                    notifyItemChanged(adapterPosition);

                    // Update saved items in SharedPreferences
                    SharedPreferences sharedPreferences = context.getSharedPreferences("SavedFoods", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet("savedFoodSet", savedItems);
                    editor.apply();
                }
            }
        });

        String foodName = bestFood_name.get(position);
        String imageString = Base64.encodeToString(bestFood_image.get(position), Base64.DEFAULT);
        String time = bestFood_time.get(position);
        String ingredients = bestFood_ingredients.get(position);
        String steps = bestFood_steps.get(position);

        String item = foodName + ";" + imageString + ";" + time + ";" + ingredients + ";" + steps;

        if (savedItems.contains(item)) {
            holder.btnSave.setImageResource(R.drawable.bookmark); // Replace with the filled bookmark icon
        } else {
            holder.btnSave.setImageResource(R.drawable.bookmark); // Replace with the unfilled bookmark icon
        }
    }

    @Override
    public int getItemCount() {
        return bestFood_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_foodName, text_time;
        ImageView img_food;
        ImageButton btnSave;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_foodName = itemView.findViewById(R.id.text_foodName);
            img_food = itemView.findViewById(R.id.img_food);
            text_time = itemView.findViewById(R.id.txt_time);
            btnSave = itemView.findViewById(R.id.save);
        }
    }
}
