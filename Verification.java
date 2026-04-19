package com.example.svaracoffeeandsnack.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // Tambahkan import TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.svaracoffeeandsnack.R;

public class VerificationActivity extends AppCompatActivity {

    EditText etOtp;
    Button btnVerifikasi;
    TextView tvVerifSubtitle;

    // OTP dummy (simulasi)
    private final String DUMMY_OTP = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);

        etOtp = findViewById(R.id.etOtp);
        btnVerifikasi = findViewById(R.id.btnVerifikasi);
        tvVerifSubtitle = findViewById(R.id.tvVerifSubtitle); // Pastikan ID ini ada di XML atau abaikan jika tidak ada


        String emailUser = getIntent().getStringExtra("user_email");
        if (emailUser != null && tvVerifSubtitle != null) {
            tvVerifSubtitle.setText("Masukkan kode yang dikirim ke " + emailUser);
        }

        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp = etOtp.getText().toString().trim();

                if (TextUtils.isEmpty(otp)) {
                    Toast.makeText(
                            VerificationActivity.this,
                            "Kode OTP tidak boleh kosong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (!otp.equals(DUMMY_OTP)) {
                    Toast.makeText(
                            VerificationActivity.this,
                            "Kode OTP salah",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    Toast.makeText(
                            VerificationActivity.this,
                            "Verifikasi berhasil",
                            Toast.LENGTH_SHORT
                    ).show();


                    Intent intent = new Intent(
                            VerificationActivity.this,
                            ResetPasswordActivity.class
                    );

                    intent.putExtra("user_email", emailUser);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
