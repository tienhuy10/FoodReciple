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
import com.example.foodreciple.Activity.Food_to_Category;
import com.example.foodreciple.AdminActivity.Admin_update_food;
import com.example.foodreciple.R;

import java.util.ArrayList;

public class adapter_Category extends RecyclerView.Adapter<adapter_Category.MyViewHolder> {
    private Context context;
    private ArrayList<String> CategoryName;
    private ArrayList<Integer> ID_Cate;
    private ArrayList<byte[]> Image;

    public adapter_Category(Context context,ArrayList<Integer> ID_Cate, ArrayList<String> categoryName, ArrayList<byte[]> image) {
        this.context = context;
        this.ID_Cate = ID_Cate;
        this.CategoryName = categoryName;
        this.Image = image;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_food_category, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CategoryName.setText(String.valueOf(CategoryName.get(position)));
        holder.txt_id_category.setText(String.valueOf(ID_Cate.get(position)));
        byte[] imageBlob = Image.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(imageBlob)
                .placeholder(R.drawable.ic_baseline_settings) // Hình ảnh placeholder nếu không tải được
                .error(R.drawable.ic_baseline_settings) // Hình ảnh hiển thị khi có lỗi
                .into(holder.img_food_cate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Food_to_Category.class);
                intent.putExtra("ID", ID_Cate.get(position));
                intent.putExtra("CategoryName", CategoryName.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CategoryName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryName, txt_id_category;
        ImageView img_food_cate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.text_name_Cate);
            img_food_cate = itemView.findViewById(R.id.img_food_cate);
            txt_id_category = itemView.findViewById(R.id.txt_id_category);
        }
    }
}
