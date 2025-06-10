package com.example.doan_quanlynhanvien_android.MainActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_quanlynhanvien_android.Helper.Database;
import com.example.doan_quanlynhanvien_android.R;
public class SignupActivity extends AppCompatActivity {

    private EditText edtEmail, edtPass, edtRePass;
    private Button btnSignup;
    private TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtEmail = findViewById(R.id.edtEmai);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRe_Pass);
        btnSignup = findViewById(R.id.btnSignup);
        txtlogin = findViewById(R.id.txtlogin);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPass.getText().toString().trim();
            String rePassword = edtRePass.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
                Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(rePassword)) {
                Toast.makeText(SignupActivity.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEmailExist(email)) {
                Toast.makeText(SignupActivity.this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }

            saveUserToDatabase(email, password);
        });

    }

    private boolean isEmailExist(String email) {
        SQLiteDatabase db = Database.initDatabase(SignupActivity.this, "User.db");
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM users WHERE email = ?";
            cursor = db.rawQuery(query, new String[]{email});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void saveUserToDatabase(String email, String password) {
        SQLiteDatabase db = Database.initDatabase(SignupActivity.this, "User.db");
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
