package com.example.doan_quanlynhanvien_android.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_quanlynhanvien_android.Adapter.FoodAdapter;
import com.example.doan_quanlynhanvien_android.Domain.Food;
import com.example.doan_quanlynhanvien_android.Helper.Database;
import com.example.doan_quanlynhanvien_android.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String DATABASE_NAME = "Food.db";
    SQLiteDatabase database;
    RecyclerView recyclerView;
    ArrayList<Food> list;
    FoodAdapter adapter;
    Spinner spVitri, spGia, spSao;
    AutoCompleteTextView edtTimkiem;  // Thay đổi thành AutoCompleteTextView
    ImageView btnTimKiem,logout;
    ImageView btnPizza, btnBugger, btnChicken, btnSushi, btnMeat, btnHotdog, btnDrink, btnMore;
    ImageView btnGioHang;



    String selectedVitri = "Tất cả";
    String selectedGia = "Tất cả";
    String selectedSao = "Tất cả";
    String searchQuery = "";  // Lưu trữ từ khóa tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        readData();// Lấy dữ liệu từ database và hiển thị

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }

            private void showLogoutDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn có muốn đăng xuất không?");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });
        LinearLayout linearLayout = findViewById(R.id.thanhcongcu);
        linearLayout.setVisibility(View.GONE);
        //lọc danh sách tìm kiếm món ăn
        ImageView loc = findViewById(R.id.btnLoc);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem LinearLayout hiện tại có đang hiển thị không
                if (linearLayout.getVisibility() == View.VISIBLE) {
                    // Nếu đang hiển thị thì ẩn đi
                    linearLayout.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Đã tắt chức năng lọc", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu đang ẩn thì hiển thị
                    linearLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Đã bật chức năng lọc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện Spinner vị trí
        spVitri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVitri = parent.getItemAtPosition(position).toString();
                filterData(); // Lọc dữ liệu
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedVitri = "Tất cả";
            }
        });

        // Xử lý sự kiện Spinner giá
        spGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGia = parent.getItemAtPosition(position).toString();
                filterData(); // Lọc dữ liệu
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGia = "Tất cả";
            }
        });

        // Xử lý sự kiện Spinner sao
        spSao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSao = parent.getItemAtPosition(position).toString();
                filterData(); // Lọc dữ liệu
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSao = "Tất cả";
            }
        });

        // Xử lý sự kiện tìm kiếm khi nhấn nút tìm kiếm
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = edtTimkiem.getText().toString().trim();
                filterData(); // Lọc dữ liệu theo từ khóa tìm kiếm
            }
        });

        // Cấu hình AutoCompleteTextView với danh sách món ăn
        setAutoCompleteSuggestions();
    }

    private void addControls() {
        recyclerView = findViewById(R.id.bestFood); // RecyclerView thay cho ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        adapter = new FoodAdapter(this, list); // Sử dụng FoodAdapter để hiển thị dữ liệu
        recyclerView.setAdapter(adapter); // Đặt adapter cho RecyclerView

        // Khởi tạo Spinner vị trí
        spVitri = findViewById(R.id.SpVitri);
        String[] vt = {"Tất cả", "Hải Châu", "Thanh Khê", "Sơn Trà", "Ngũ Hành Sơn", "Liên Chiểu", "Cẩm Lệ", "Hòa Vang", "Hoàng Sa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vt);
        spVitri.setAdapter(adapter);

        // Khởi tạo Spinner giá
        spGia = findViewById(R.id.SpGia);
        String[] priceRanges = {"Tất cả", "0-100", "100-200", "Trên 200"};
        ArrayAdapter<String> priceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priceRanges);
        spGia.setAdapter(priceAdapter);

        // Khởi tạo Spinner sao
        spSao = findViewById(R.id.SpSao);
        String[] saoRanges = {"Tất cả", "1-2 sao", "2-3 sao", "3-4 sao", "4-5 sao", "Trên 3 sao"};
        ArrayAdapter<String> saoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, saoRanges);
        spSao.setAdapter(saoAdapter);

        // Khởi tạo AutoCompleteTextView và ImageView
        edtTimkiem = findViewById(R.id.edtTimkiem);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        ImageView btnPizza = findViewById(R.id.btnPizza);
        ImageView btnBugger = findViewById(R.id.btnBugger);
        ImageView btnChiken = findViewById(R.id.btnChiken);
        ImageView btnSushi = findViewById(R.id.btnSushi);
        ImageView btnMeat = findViewById(R.id.btnMeat);
        ImageView btnHotdog = findViewById(R.id.btnHotdog);
        ImageView btnDrink = findViewById(R.id.btnDrink);
        ImageView btnMore = findViewById(R.id.btnMore);
        btnGioHang = findViewById(R.id.btnGioHang);

        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });
        // Set OnClickListener for each ImageView button
        btnPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDanhSachFood("Pizza","L");
            }
        });
        btnBugger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDanhSachFood("Bugger","H");
            }
        });
        btnChiken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDanhSachFood("Chiken","G"); }
        });
        btnSushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDanhSachFood("Sushi","S");
            }
        });
        btnMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDanhSachFood("Meat","M");
            }
        });
        btnHotdog.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigateToDanhSachFood("Hotdog","U");
            }
        });
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigateToDanhSachFood("Drink","D");
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigateToDanhSachFood("More","K");
            }
        });
    }
    private void navigateToDanhSachFood(String ten,String ma) {
        Intent intent = new Intent(MainActivity.this, DanhSachFood.class);
        intent.putExtra("NAME", ten);
        intent.putExtra("MA",ma);
        startActivity(intent);
    }

    private void setAutoCompleteSuggestions() {
        // Lấy tất cả tên món ăn để làm gợi ý
        ArrayList<String> allFoodNames = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.query("FOOD", new String[]{"TEN_MON"}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenMon = cursor.getString(0);
                allFoodNames.add(tenMon); // Thêm tên món ăn vào danh sách
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Tạo ArrayAdapter cho AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allFoodNames);
        edtTimkiem.setAdapter(adapter);  // Gán adapter cho AutoCompleteTextView
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.query("FOOD", new String[]{"TEN_MON", "GIA", "DANH_GIA", "VI_TRI", "ANH"}, null, null, null, null, null);
        list.clear();  // Xóa danh sách cũ trước khi thêm dữ liệu mới
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenMon = cursor.getString(0);
                int gia = cursor.getInt(1);
                double danhGia = cursor.getDouble(2);
                String viTri = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);
                list.add(new Food("", tenMon, danhGia, gia, viTri, anh)); // Tạo đối tượng Food và thêm vào list
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();  // Thông báo adapter để cập nhật dữ liệu
    }

    private void filterData() {
        String selection = "";
        ArrayList<String> selectionArgs = new ArrayList<>();

        // Lọc theo tên món ăn
        if (!searchQuery.isEmpty()) {
            selection += "TEN_MON LIKE ?";
            selectionArgs.add("%" + searchQuery + "%");
        }

        // Lọc theo vị trí
        if (!selectedVitri.equals("Tất cả")) {
            if (!selection.isEmpty()) selection += " AND ";
            selection += "VI_TRI = ?";
            selectionArgs.add(selectedVitri);
        }

        // Lọc theo giá
        if (!selectedGia.equals("Tất cả")) {
            if (!selection.isEmpty()) selection += " AND ";

            switch (selectedGia) {
                case "0-100":
                    selection += "GIA >= ? AND GIA <= ?";
                    selectionArgs.add("0");
                    selectionArgs.add("100");
                    break;
                case "100-200":
                    selection += "GIA >= ? AND GIA <= ?";
                    selectionArgs.add("100");
                    selectionArgs.add("200");
                    break;
                case "Trên 200":
                    selection += "GIA > ?";
                    selectionArgs.add("200");
                    break;
            }
        }

        // Lọc theo sao
        if (!selectedSao.equals("Tất cả")) {
            if (!selection.isEmpty()) selection += " AND ";

            switch (selectedSao) {
                case "1-2 sao":
                    selection += "DANH_GIA >= ? AND DANH_GIA <= ?";
                    selectionArgs.add("1");
                    selectionArgs.add("2");
                    break;
                case "2-3 sao":
                    selection += "DANH_GIA >= ? AND DANH_GIA <= ?";
                    selectionArgs.add("2");
                    selectionArgs.add("3");
                    break;
                case "3-4 sao":
                    selection += "DANH_GIA >= ? AND DANH_GIA <= ?";
                    selectionArgs.add("3");
                    selectionArgs.add("4");
                    break;
                case "4-5 sao":
                    selection += "DANH_GIA >= ? AND DANH_GIA <= ?";
                    selectionArgs.add("4");
                    selectionArgs.add("5");
                    break;
                case "Trên 3 sao":
                    selection += "DANH_GIA > ?";
                    selectionArgs.add("3");
                    break;
            }
        }

        // Đọc lại dữ liệu từ database với các tiêu chí lọc
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.query("FOOD", new String[]{"TEN_MON", "GIA", "DANH_GIA", "VI_TRI", "ANH"}, selection, selectionArgs.toArray(new String[0]), null, null, null);
        list.clear();  // Xóa danh sách cũ trước khi thêm dữ liệu mới
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenMon = cursor.getString(0);
                int gia = cursor.getInt(1);
                double danhGia = cursor.getDouble(2);
                String viTri = cursor.getString(3);
                byte[] anh = cursor.getBlob(4);
                list.add(new Food("", tenMon, danhGia, gia, viTri, anh)); // Tạo đối tượng Food và thêm vào list
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();  // Thông báo adapter để cập nhật dữ liệu
    }

}


