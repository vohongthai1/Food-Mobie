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

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHolder> {
    private Context context;
    private ArrayList<Food> foodList;

    public DanhMucAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dsfood, parent, false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.txtTenMon.setText(food.getTenMon());
        holder.txtGia.setText(food.getGia()+"K");
        holder.txtSao.setText(String.valueOf(food.getDanhGia()));
        holder.txtViTri.setText(food.getViTri());

        // Load image from byte array
        Bitmap bitmap = BitmapFactory.decodeByteArray(food.getAnh(), 0, food.getAnh().length);
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

    public static class DanhMucViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtTenMon, txtGia, txtSao, txtViTri;

        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_ids);
            txtTenMon = itemView.findViewById(R.id.txt_ten_ids);
            txtGia = itemView.findViewById(R.id.txt_gia_ids);
            txtSao = itemView.findViewById(R.id.txt_sao_ids);
            txtViTri = itemView.findViewById(R.id.txt_vitri_ids);
        }
    }
}
