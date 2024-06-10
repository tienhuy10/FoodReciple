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
import com.example.foodreciple.AdminActivity.Admin_update_category;
import com.example.foodreciple.AdminActivity.Admin_update_food;
import com.example.foodreciple.R;

import java.util.ArrayList;


public class Admin_adapter_category extends RecyclerView.Adapter<Admin_adapter_category.MyViewHolder> {
    private Context context;
    private ArrayList<byte[]> Image_category;
    private ArrayList<Integer> CategoryID;
    private ArrayList<String> CategoryName;

    public Admin_adapter_category(Context context, ArrayList<Integer> categoryID, ArrayList<String> categoryName, ArrayList<byte[]> imageCategory) {
        this.context = context;
        this.Image_category = imageCategory;
        this.CategoryID = categoryID;
        this.CategoryName = categoryName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_item_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category_id_txt.setText(String.valueOf(CategoryID.get(position)));
        holder.category_title_txt.setText(CategoryName.get(position));
        byte[] imageBlob = Image_category.get(position);

        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings)
                .error(R.drawable.ic_baseline_settings)
                .into(holder.img_category);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, Admin_update_category.class);
                    intent.putExtra("ID", CategoryID.get(currentPosition));
                    intent.putExtra("CategoryName", CategoryName.get(currentPosition));
                    intent.putExtra("Image", Image_category.get(currentPosition));
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return CategoryName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category_title_txt, category_id_txt;
        ImageView img_category;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_id_txt = itemView.findViewById(R.id.category_id_txt);
            category_title_txt = itemView.findViewById(R.id.category_title_txt);
            img_category = itemView.findViewById(R.id.image_category);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

