package com.example.svaracoffeeandsnack.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.svaracoffeeandsnack.R;
import com.example.svaracoffeeandsnack.home.DatabaseHelper; // Tambahkan Import

public class LupaPasswordActivity extends AppCompatActivity {

    EditText etEmail;
    Button btnKirim;
    DatabaseHelper dbHelper; // Deklarasi DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Hubungkan XML ke Java
        etEmail = findViewById(R.id.etEmail);
        btnKirim = findViewById(R.id.btnKirim);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LupaPasswordActivity.this,
                            "Email tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                } else {




                    Toast.makeText(LupaPasswordActivity.this,
                            "Memverifikasi email: " + email,
                            Toast.LENGTH_SHORT).show();


                    Toast.makeText(LupaPasswordActivity.this,
                            "Kode verifikasi telah dikirim ke email",
                            Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(
                            LupaPasswordActivity.this,
                            VerificationActivity.class
                    );

                    intent.putExtra("user_email", email);
                    startActivity(intent);
                }
            }
        });
    }
}
