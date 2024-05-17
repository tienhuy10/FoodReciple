// ListAdapter.java
package com.example.foodreciple.FoodDetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodreciple.R;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListData> dataArrayList;

    public ListAdapter(Context context, ArrayList<ListData> dataArrayList){
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListData listData = dataArrayList.get(position);
        holder.foodName.setText(listData.getName());
        holder.foodTime.setText(listData.getTime());
        holder.foodImage.setImageResource(listData.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodDetails.class);
            intent.putExtra("foodName", listData.getName());
            intent.putExtra("foodTime", listData.getTime());
            intent.putExtra("foodImage", listData.getImageResource());
            intent.putExtra("foodIngredients", listData.getIngredients());
            intent.putExtra("foodSteps", listData.getSteps());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodTime;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodTime = itemView.findViewById(R.id.food_time);
            foodImage = itemView.findViewById(R.id.food_image);
        }
    }
}
