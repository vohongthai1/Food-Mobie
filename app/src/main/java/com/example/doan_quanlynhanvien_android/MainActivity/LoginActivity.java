package com.example.doan_quanlynhanvien_android.MainActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_quanlynhanvien_android.Helper.Database;
import com.example.doan_quanlynhanvien_android.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Lấy các EditText
        edtEmail = findViewById(R.id.edtEmai); // Đảm bảo tên đúng là edtEmail
        edtPass = findViewById(R.id.edtPass);

        // Xử lý sự kiện đăng nhập
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                // Kiểm tra thông tin đăng nhập
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra đăng nhập với thông tin người dùng
                if (checkLogin(email, password)) {
                    // Chuyển sang MainActivity sau khi đăng nhập thành công
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng LoginActivity sau khi chuyển
                } else {
                    // Thông báo nếu đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện bấm vào "Sign up" để chuyển sang màn hình đăng ký
        findViewById(R.id.btnS_Up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức kiểm tra đăng nhập
    private boolean checkLogin(String email, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            // Khởi tạo cơ sở dữ liệu
            db = Database.initDatabase(this, "User.db");

            // Truy vấn cơ sở dữ liệu để kiểm tra thông tin đăng nhập
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            cursor = db.rawQuery(query, new String[]{email, password});

            if (cursor != null && cursor.getCount() > 0) {
                return true; // Đăng nhập thành công
            } else {
                return false; // Đăng nhập thất bại
            }
        } finally {
            // Đảm bảo đóng con trỏ và cơ sở dữ liệu để tránh rò rỉ tài nguyên
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
