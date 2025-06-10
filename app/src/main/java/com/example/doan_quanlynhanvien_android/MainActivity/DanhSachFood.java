package com.example.doan_quanlynhanvien_android.MainActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_quanlynhanvien_android.Adapter.DanhMucAdapter;
import com.example.doan_quanlynhanvien_android.Domain.Food;
import com.example.doan_quanlynhanvien_android.Helper.Database;
import com.example.doan_quanlynhanvien_android.R;

import java.util.ArrayList;

public class DanhSachFood extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Food> list;
    DanhMucAdapter adapter;
    SQLiteDatabase database;
    final String DATABASE_NAME = "Food.db";
    TextView txtDanhMuc;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_food);

        txtDanhMuc = findViewById(R.id.txtDanhMuc);
        recyclerView = findViewById(R.id.re_list);
        back = findViewById(R.id.back); // Khởi tạo nút back

        // Get the category name and code from the Intent
        String categoryName = getIntent().getStringExtra("NAME");
        String categoryCode = getIntent().getStringExtra("MA");

        // Set the category name to the TextView
        if (categoryName != null) {
            txtDanhMuc.setText(categoryName);
        }

        // Initialize RecyclerView
        list = new ArrayList<>();
        adapter = new DanhMucAdapter(this, list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));  // Set 2 columns
        recyclerView.setAdapter(adapter);

        // Load and filter data
        loadData(categoryCode);

        // Set up the back button click event
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại Activity trước đó
            }
        });
    }

    private void loadData(String categoryCode) {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.query("FOOD", new String[]{"TEN_MON", "GIA", "DANH_GIA", "VI_TRI", "ANH"},
                "MA LIKE ?", new String[]{categoryCode + "%"}, null, null, null);

        list.clear();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String tenMon = cursor.getString(0);
                    int gia = cursor.getInt(1);
                    double danhGia = cursor.getDouble(2);
                    String viTri = cursor.getString(3);
                    byte[] anh = cursor.getBlob(4);
                    list.add(new Food("", tenMon, danhGia, gia, viTri, anh)); // Add Food item to the list
                } while (cursor.moveToNext());
                adapter.notifyDataSetChanged(); // Notify adapter to update the RecyclerView
            } else {
                Log.d("DanhSachFood", "No data found for the given category code.");
            }
            cursor.close();
        } else {
            Log.d("DanhSachFood", "Cursor is null. Query might have failed.");
        }
    }
}
