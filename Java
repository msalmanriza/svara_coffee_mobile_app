package com.example.svaracoffeeandsnack.auth;

import android.content.Intent;
import android.content.SharedPreferences; // Tambahan Import
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.svaracoffeeandsnack.R;
import com.example.svaracoffeeandsnack.home.AdminDashboardActivity;
import com.example.svaracoffeeandsnack.home.MainActivity;
import com.example.svaracoffeeandsnack.home.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister, tvForgot;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgot   = findViewById(R.id.tvForgot);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Username dan Password wajib diisi",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (username.equals("admin") && password.equals("admin123")) {
                        Toast.makeText(LoginActivity.this,
                                "Login Berhasil sebagai Admin", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (dbHelper.checkUserLogin(username, password)) {
                        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("current_username", username); // Simpan nama buat Profile nanti
                        editor.apply();

                        Toast.makeText(LoginActivity.this,
                                "Login Berhasil sebagai " + username, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Username atau Password salah!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LupaPasswordActivity.class);
                startActivity(intent);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
