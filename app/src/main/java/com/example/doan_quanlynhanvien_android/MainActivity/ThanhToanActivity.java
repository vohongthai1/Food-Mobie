package com.example.doan_quanlynhanvien_android.MainActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_quanlynhanvien_android.Adapter.Cart;
import com.example.doan_quanlynhanvien_android.Adapter.CartAdapter;
import com.example.doan_quanlynhanvien_android.Domain.Food;
import com.example.doan_quanlynhanvien_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {
    ImageView tt_btnback;
    TextView txtDN;
    RecyclerView recyclerView;
    TextView txtTongMon, txtVanChuyen, txtGiamGia, txtTongDonHang;
    Spinner spQuan, spPhuong;
    EditText etdMaGiamGia;
    CartAdapter adapter;
    TextView txtThongTinDonHang;

    // Dữ liệu các quận và phường
    private final Map<String, List<String>> dataQuanPhuong = new HashMap<>();
    private final List<String> listQuan = new ArrayList<>();
    private final List<String> listPhuong = new ArrayList<>();
    private final Map<String, Integer> phiVanChuyen = new HashMap<>();

    private int tongGiaTriMonAn = 0; // Tổng giá trị món hàng
    private int giamGia = 0; // Mã giảm giá
    private int phiVC = 0; // Phí vận chuyển

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        TextView txtDN = findViewById(R.id.txtDa_nhiem);

        // Ánh xạ các thành phần giao diện
        tt_btnback = findViewById(R.id.tt_btnback);
        recyclerView = findViewById(R.id.Re_ThanhToan);
        txtTongMon = findViewById(R.id.txtTongMon);
        txtVanChuyen = findViewById(R.id.txtVanChuyen);
        txtGiamGia = findViewById(R.id.txtGiamGia);
        txtTongDonHang = findViewById(R.id.txtTongCong);
        spQuan = findViewById(R.id.sp_Quan);
        spPhuong = findViewById(R.id.sp_Phuong);
        etdMaGiamGia = findViewById(R.id.etdMaGiamGia);
        TextView buttonDatHang = findViewById(R.id.button2);
        Button btnApMa = findViewById(R.id.btnApMa);
        txtThongTinDonHang = findViewById(R.id.txtThongTinDonHang);
        Button btnHuyPhieu = findViewById(R.id.btnHuyPhieu);

        etdMaGiamGia.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Khi người dùng rời khỏi ô nhập
                MaGiamGia();
                updateTongCong();
            }
        });

        // Sự kiện khi bấm nút Áp dụng mã giảm giá
        btnApMa.setOnClickListener(v -> {
            MaGiamGia(); // Kiểm tra mã giảm giá khi bấm nút
            updateTongCong();  // Cập nhật tổng cộng
        });
        // Quay lại màn hình trước
        tt_btnback.setOnClickListener(v -> finish());

        // Cài đặt dữ liệu quận và phường
        setupData();

        // Thiết lập Adapter cho Spinner Quận
        ArrayAdapter<String> adapterQuan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listQuan);
        adapterQuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuan.setAdapter(adapterQuan);

        // Sự kiện chọn quận

        spQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuan = listQuan.get(position);
                updatePhuongSpinner(selectedQuan);
                calculatePhiVanChuyen(selectedQuan);
                updateTongCong();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Sự kiện chọn phường
        spPhuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPhuong = listPhuong.get(position);
                updateQuanSpinner(selectedPhuong);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Sự kiện nhập mã giảm giá
        etdMaGiamGia.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Khi người dùng rời khỏi ô nhập
                MaGiamGia();
                updateTongCong();
            }
        });

        // Hiển thị danh sách giỏ hàng
        Cart cart = Cart.getInstance();
        adapter = new CartAdapter(this, cart.getItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Cập nhật tổng tiền ban đầu
        CapNhatTongMon();
        updateTongCong();

        // Sự kiện đặt hàng
        buttonDatHang.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(ThanhToanActivity.this)
                    .setTitle("Xác nhận đặt hàng")
                    .setMessage("Bạn có chắc chắn muốn đặt hàng không?")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        // Khi người dùng nhấn Xác nhận
                        Toast.makeText(ThanhToanActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();

                        // Xóa giỏ hàng
//                        cart.clear();
//                        adapter.notifyDataSetChanged();
                        txtDN.setText("Đơn Hàng Của Bạn");

                        // Lấy giá trị tổng đơn hàng từ txtTongDonHang
                        String totalAmount = txtTongDonHang.getText().toString();

                        // Update text with order details
                        txtThongTinDonHang.setText("Đơn hàng của bạn tổng " + totalAmount+ " sẽ sớm được giao");

                        // Ẩn các thành phần không cần thiết sau khi đặt hàng
                        recyclerView.setVisibility(View.VISIBLE);
                        buttonDatHang.setVisibility(View.GONE);
                        txtThongTinDonHang.setVisibility(View.VISIBLE);
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        // Khi người dùng nhấn Hủy
                        dialog.dismiss();
                    })
                    .show();  // Hiển thị hộp thoại
        });
        btnHuyPhieu.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(ThanhToanActivity.this)
                    .setTitle("Xác nhận hủy đơn hàng")
                    .setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?")
                    .setPositiveButton("Xác nhận", (dialog, which) -> {
                        // Khi người dùng nhấn Xác nhận

                        txtThongTinDonHang.setText("");  // Xóa thông tin đơn hàng
                        txtThongTinDonHang.setVisibility(View.GONE);  // Ẩn thông tin đơn hàng
                        recyclerView.setVisibility(View.VISIBLE);  // Hiển thị lại giỏ hàng
                        buttonDatHang.setVisibility(View.VISIBLE);  // Hiển thị nút đặt hàng
                        Toast.makeText(ThanhToanActivity.this, "Đơn hàng đã được hủy!", Toast.LENGTH_SHORT).show();
                        cart.clear();
                        adapter.notifyDataSetChanged();
                        finish();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        // Khi người dùng nhấn Hủy
                        dialog.dismiss(); // Đóng hộp thoại
                    })
                    .show();  // Hiển thị hộp thoại
        });
    }

    // Hàm thiết lập dữ liệu quận và phường
    private void setupData() {
        dataQuanPhuong.put("All Quận", List.of("All"));
        dataQuanPhuong.put("Hải Châu", List.of("Hòa Thuận Đông", "Bình Thuận", "Thạch Thang"));
        dataQuanPhuong.put("Thanh Khê", List.of("Vĩnh Trung", "Tân Chính", "Thạc Gián"));
        dataQuanPhuong.put("Sơn Trà", List.of("Mân Thái", "An Hải Bắc", "Phước Mỹ"));
        dataQuanPhuong.put("Ngũ Hành Sơn", List.of("Hòa Quý", "Khuê Mỹ"));
        dataQuanPhuong.put("Liên Chiểu", List.of("Hòa Hiệp Bắc", "Hòa Hiệp Nam", "Hòa Minh"));
        dataQuanPhuong.put("Cẩm Lệ", List.of("Khuê Trung", "Hòa Thọ Đông", "Hòa An"));
        dataQuanPhuong.put("Hòa Vang", List.of("Hòa Sơn", "Hòa Nhơn", "Hòa Khánh"));

        phiVanChuyen.put("All Phường", 0);
        phiVanChuyen.put("Hải Châu", 5);
        phiVanChuyen.put("Thanh Khê", 5);
        phiVanChuyen.put("Sơn Trà", 10);
        phiVanChuyen.put("Cẩm Lệ", 10);
        phiVanChuyen.put("Ngũ Hành Sơn", 15);
        phiVanChuyen.put("Liên Chiểu", 15);
        phiVanChuyen.put("Hòa Vang", 15);

        listQuan.addAll(dataQuanPhuong.keySet());
    }

    // Cập nhật danh sách phường khi chọn quận
    private void updatePhuongSpinner(String selectedQuan) {
        listPhuong.clear();
        listPhuong.addAll(dataQuanPhuong.get(selectedQuan));
        ArrayAdapter<String> adapterPhuong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listPhuong);
        adapterPhuong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhuong.setAdapter(adapterPhuong);
    }

    // Cập nhật danh sách quận khi chọn phường
    private void updateQuanSpinner(String selectedPhuong) {
        for (Map.Entry<String, List<String>> entry : dataQuanPhuong.entrySet()) {
            if (entry.getValue().contains(selectedPhuong)) {
                spQuan.setSelection(listQuan.indexOf(entry.getKey()));
                return;
            }
        }
    }

    // Tính phí vận chuyển
    private void calculatePhiVanChuyen(String selectedQuan) {
        phiVC = phiVanChuyen.getOrDefault(selectedQuan, 0);
        txtVanChuyen.setText(String.format("%,d K", phiVC));
    }

    // Kiểm tra mã giảm giá
    private void MaGiamGia() {
        String maGiamGia = etdMaGiamGia.getText().toString().trim();
        giamGia = "NGON".equalsIgnoreCase(maGiamGia) ? 5 : 0;
        txtGiamGia.setText(String.format("-%d K", giamGia));
    }

    // Cập nhật tổng tiền món ăn
    public void CapNhatTongMon() {
        Cart cart = Cart.getInstance();
        tongGiaTriMonAn = 0;
        for (Map.Entry<Food, Integer> entry : cart.getItems().entrySet()) {
            tongGiaTriMonAn += entry.getKey().getGia() * entry.getValue();
        }
        txtTongMon.setText(String.format("%,d K", tongGiaTriMonAn));
    }

    // Cập nhật tổng cộng
    private void updateTongCong() {
        int tongCong = tongGiaTriMonAn + phiVC - giamGia;
        txtTongDonHang.setText(String.format("%,d K", tongCong));
    }

    public void CapNhatTongDonHang() {
        CapNhatTongMon();
        updateTongCong();
    }
}
