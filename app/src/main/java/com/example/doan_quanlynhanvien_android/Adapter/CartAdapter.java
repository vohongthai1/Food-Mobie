package com.example.doan_quanlynhanvien_android.Adapter;

import android.content.Context;
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
import com.example.doan_quanlynhanvien_android.MainActivity.ThanhToanActivity;
import com.example.doan_quanlynhanvien_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Map.Entry<Food, Integer>> cartItems;

    public CartAdapter(Context context, Map<Food, Integer> items) {
        this.context = context;
        this.cartItems = new ArrayList<>(items.entrySet());
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhtoan, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Map.Entry<Food, Integer> item = cartItems.get(position);
        Food food = item.getKey();
        int soluong = item.getValue();

        // Hiển thị thông tin món ăn
        holder.TT_Ten.setText(food.getTenMon());
        holder.TT_gia.setText(String.format("%,d K", food.getGia()));
        holder.TT_txt_soluong.setText(String.valueOf(soluong));
        holder.TT_txtTong1Mon.setText(String.format("%,d K", food.getGia() * soluong));

        Bitmap bitmap = BitmapFactory.decodeByteArray(food.getAnh(), 0, food.getAnh().length);
        holder.TT_imgpic.setImageBitmap(bitmap);

        // Xử lý tăng/giảm số lượng
        holder.TT_txtCong.setOnClickListener(v -> Tang(position));
        holder.TT_txtTru.setOnClickListener(v -> Giam(position));
    }

    private void Tang(int position) {
        // Tăng số lượng món
        Map.Entry<Food, Integer> item = cartItems.get(position);
        Food food = item.getKey();
        int soluong = item.getValue();
        soluong++;

        // Cập nhật dữ liệu và giao diện
        CapnhatCart(position, food, soluong);
    }

    private void Giam(int position) {
        // Giảm số lượng món
        Map.Entry<Food, Integer> item = cartItems.get(position);
        Food food = item.getKey();
        int soluong = item.getValue();

        if (soluong > 1) {
            // Nếu số lượng > 1, chỉ giảm số lượng
            soluong--;
            CapnhatCart(position, food, soluong);
        } else {
            // Nếu số lượng = 1, xóa món khỏi giỏ hàng
            XoaKhoiCart(position, food);
        }
    }

    private void CapnhatCart(int position, Food food, int soluong) {
        // Cập nhật số lượng trong danh sách và Cart
        cartItems.set(position, Map.entry(food, soluong));
        Cart.getInstance().updateItem(food, soluong);

        // Cập nhật giao diện RecyclerView
        notifyItemChanged(position);

        // Cập nhật tổng giá trị đơn hàng
        CapNhatTongGia();
    }

    private void XoaKhoiCart(int position, Food food) {
        // Xóa món ăn khỏi danh sách và Cart
        cartItems.remove(position);
        Cart.getInstance().removeItem(food);

        // Cập nhật giao diện RecyclerView
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartItems.size());

        // Cập nhật tổng giá trị đơn hàng
        CapNhatTongGia();
    }

    private void CapNhatTongGia() {
        if (context instanceof ThanhToanActivity) {
            ((ThanhToanActivity) context).CapNhatTongDonHang();
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView TT_imgpic;
        TextView TT_Ten, TT_gia, TT_txt_soluong, TT_txtTong1Mon;
        TextView TT_txtTru, TT_txtCong;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            TT_imgpic = itemView.findViewById(R.id.TT_imgpic);
            TT_Ten = itemView.findViewById(R.id.TT_Ten);
            TT_gia = itemView.findViewById(R.id.TT_gia);
            TT_txt_soluong = itemView.findViewById(R.id.TT_txt_soluong);
            TT_txtTong1Mon = itemView.findViewById(R.id.TT_txtTong1Mon);
            TT_txtTru = itemView.findViewById(R.id.TT_txtTru);
            TT_txtCong = itemView.findViewById(R.id.TT_txtCong);
        }
    }
}
