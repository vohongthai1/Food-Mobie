package com.example.doan_quanlynhanvien_android.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_quanlynhanvien_android.Domain.Food;
import com.example.doan_quanlynhanvien_android.MainActivity.Sub_Activity;
import com.example.doan_quanlynhanvien_android.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    private Context context;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.txtTitle.setText(food.getTenMon());
        holder.txtPrice.setText(food.getGia()+"K");
        holder.txtStar.setText(String.valueOf(food.getDanhGia()));
        holder.txtTime.setText(food.getViTri());

        // Set image
        byte[] imageBytes = food.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imgFood.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Sub_Activity.class);
                intent.putExtra("TEN_MON", food.getTenMon());
                intent.putExtra("GIA", food.getGia());
                intent.putExtra("DANH_GIA", food.getDanhGia());
                intent.putExtra("VI_TRI", food.getViTri());
                intent.putExtra("ANH", food.getAnh());
                context.startActivity(intent);

            }

        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtPrice, txtStar,txtTime;
        ImageView imgFood;

        public FoodViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txttitle);
            txtPrice = view.findViewById(R.id.txtPrice);
            txtStar = view.findViewById(R.id.txtStar);
            txtTime = view.findViewById(R.id.txtTime);
            imgFood = view.findViewById(R.id.pic);
        }
    }
}
