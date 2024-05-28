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

public class adapter_Food extends RecyclerView.Adapter<adapter_Food.MyViewHolder> {
    private Context context;
    private ArrayList<String> FoodName;
    private ArrayList<byte[]> Image_food;
    private ArrayList<String> Time;
    private ArrayList<String> Ingredients;
    private ArrayList<String> Steps;

    private Set<String> savedItems = new HashSet<>();

    public adapter_Food(Context context, ArrayList<String> foodName, ArrayList<byte[]> image_food, ArrayList<String> time, ArrayList<String> ingredients, ArrayList<String> steps) {
        this.context = context;
        this.FoodName = foodName;
        this.Image_food = image_food;
        this.Time = time;
        this.Ingredients = ingredients;
        this.Steps = steps;

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
        holder.text_foodName.setText(FoodName.get(position));
        holder.text_time.setText(Time.get(position));
        byte[] imageBlob = Image_food.get(position);

        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings)
                .error(R.drawable.ic_baseline_settings)
                .into(holder.img_food);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, FoodDetails.class);
                    intent.putExtra("text_foodName", FoodName.get(adapterPosition));
                    String imageString = Base64.encodeToString(Image_food.get(adapterPosition), Base64.DEFAULT);
                    intent.putExtra("img_food", imageString);
                    intent.putExtra("text_time", Time.get(adapterPosition));
                    intent.putExtra("text_ingredients", Ingredients.get(adapterPosition));
                    intent.putExtra("text_steps", Steps.get(adapterPosition));
                    context.startActivity(intent);
                }
            }
        });

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String foodName = FoodName.get(adapterPosition);
                    String imageString = Base64.encodeToString(Image_food.get(adapterPosition), Base64.DEFAULT);
                    String time = Time.get(adapterPosition);
                    String ingredients = Ingredients.get(adapterPosition);
                    String steps = Steps.get(adapterPosition);

                    String item = foodName + ";" + imageString + ";" + time + ";" + ingredients + ";" + steps;

                    if (savedItems.contains(item)) {
                        savedItems.remove(item);
                        holder.btnSave.setImageResource(R.drawable.bookmark);
                    } else {
                        savedItems.add(item);
                        holder.btnSave.setImageResource(R.drawable.bookmark);
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

        String foodName = FoodName.get(position);
        String imageString = Base64.encodeToString(Image_food.get(position), Base64.DEFAULT);
        String time = Time.get(position);
        String ingredients = Ingredients.get(position);
        String steps = Steps.get(position);

        String item = foodName + ";" + imageString + ";" + time + ";" + ingredients + ";" + steps;

        if (savedItems.contains(item)) {
            holder.btnSave.setImageResource(R.drawable.bookmark);
        } else {
            holder.btnSave.setImageResource(R.drawable.bookmark);
        }
    }

    @Override
    public int getItemCount() {
        return FoodName.size();
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
