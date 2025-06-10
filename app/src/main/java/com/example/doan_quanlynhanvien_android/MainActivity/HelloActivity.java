package com.example.doan_quanlynhanvien_android.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_quanlynhanvien_android.R;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        // Lấy các TextView (hoặc Button nếu bạn đổi thành Button)
        TextView btnLogin = findViewById(R.id.btn_login);
        TextView btnSignup = findViewById(R.id.btn_signup);

        // Thiết lập sự kiện cho nút Login
        btnLogin.setOnClickListener(view -> {
            // Mở LoginActivity
            startActivity(new Intent(HelloActivity.this, LoginActivity.class));
        });

        // Thiết lập sự kiện cho nút Signup
        btnSignup.setOnClickListener(view -> {
            // Mở SignupActivity
            startActivity(new Intent(HelloActivity.this, SignupActivity.class));
        });
    }
}
