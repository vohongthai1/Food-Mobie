package com.example.doan_quanlynhanvien_android.MainActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_quanlynhanvien_android.Adapter.Cart;
import com.example.doan_quanlynhanvien_android.Domain.Food;
import com.example.doan_quanlynhanvien_android.R;

public class Sub_Activity extends AppCompatActivity {
    ImageView sub_imgAnh, sub_back;
    TextView sub_txtTen, sub_txtGia, sub_txtVitri, sub_txtSao;
    TextView sub_btntru, sub_sl, sub_btncong, sub_txtGiaTong;
    TextView btnthemvaogiohang;

    int sl = 1; // Bắt đầu với 1 sản phẩm
    int gia = 0;
    Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        sub_imgAnh = findViewById(R.id.sub_imgAnh);
        sub_txtTen = findViewById(R.id.sub_txtTen);
        sub_txtGia = findViewById(R.id.sub_txtGia);
        sub_txtVitri = findViewById(R.id.sub_txtVitri);
        sub_txtSao = findViewById(R.id.sub_txtSao);
        sub_back = findViewById(R.id.sub_back);
        sub_btntru = findViewById(R.id.sub_btntru);
        sub_sl = findViewById(R.id.sub_sl);
        sub_btncong = findViewById(R.id.sub_btncong);
        sub_txtGiaTong = findViewById(R.id.sub_txtGiaTong);
        btnthemvaogiohang = findViewById(R.id.btnthemvaogiohang);

        // Lấy dữ liệu từ Intent
        String tenMon = getIntent().getStringExtra("TEN_MON");
        gia = getIntent().getIntExtra("GIA", 0);
        double danhGia = getIntent().getDoubleExtra("DANH_GIA", 0);
        String viTri = getIntent().getStringExtra("VI_TRI");
        byte[] anh = getIntent().getByteArrayExtra("ANH");

        // Tạo đối tượng Food
        food = new Food("", tenMon, danhGia, gia, viTri, anh);

        // Hiển thị dữ liệu lên giao diện
        sub_txtTen.setText(tenMon);
        sub_txtGia.setText(String.format("%,d K", gia));
        sub_txtSao.setText(String.format("%.1f", danhGia));
        sub_txtVitri.setText(viTri);
        sub_sl.setText(String.valueOf(sl));
        sub_txtGiaTong.setText(String.format("%,d K", gia * sl));

        if (anh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
            sub_imgAnh.setImageBitmap(bitmap);
        }

        sub_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại Activity trước đó
            }
        });

        sub_btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuantity();
            }
        });

        sub_btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity();
            }
        });

        btnthemvaogiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void incrementQuantity() {
        sl++;
        updateQuantityAndPrice();
    }

    private void decrementQuantity() {
        if (sl > 1) { // Giảm số lượng không dưới 1
            sl--;
            updateQuantityAndPrice();
        }
    }

    private void updateQuantityAndPrice() {
        sub_sl.setText(String.valueOf(sl));
        sub_txtGiaTong.setText(String.format("%,d K", gia * sl));
    }

    private void addToCart() {
        Cart cart = Cart.getInstance();
        cart.addItem(food, sl);
        Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
    }
}
