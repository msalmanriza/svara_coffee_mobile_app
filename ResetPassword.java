package com.example.svaracoffeeandsnack.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.svaracoffeeandsnack.R;
import com.example.svaracoffeeandsnack.home.DatabaseHelper; // Import DatabaseHelper

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etPasswordBaru, etKonfirmasiPassword;
    Button btnReset;
    DatabaseHelper dbHelper; // Deklarasi DatabaseHelper
    String emailUser; // Variabel penampung email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        emailUser = getIntent().getStringExtra("user_email");

        etPasswordBaru = findViewById(R.id.etPasswordBaru);
        etKonfirmasiPassword = findViewById(R.id.etKonfirmasiPassword);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passwordBaru = etPasswordBaru.getText().toString().trim();
                String konfirmasi = etKonfirmasiPassword.getText().toString().trim();

                if (TextUtils.isEmpty(passwordBaru) || TextUtils.isEmpty(konfirmasi)) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Password tidak boleh kosong",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (passwordBaru.length() < 6) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Password minimal 6 karakter",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (!passwordBaru.equals(konfirmasi)) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Password tidak sama",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {

                    boolean isUpdated = updatePasswordInSQLite(emailUser, passwordBaru);

                    if (isUpdated) {
                        Toast.makeText(
                                ResetPasswordActivity.this,
                                "Password berhasil diperbarui di Database",
                                Toast.LENGTH_LONG
                        ).show();


                        Intent intent = new Intent(
                                ResetPasswordActivity.this,
                                LoginActivity.class
                        );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Gagal memperbarui password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private boolean updatePasswordInSQLite(String email, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_PASSWORD, newPass);


        int result = db.update(DatabaseHelper.TABLE_USERS, values,
                DatabaseHelper.COL_USER_EMAIL + " = ?", new String[]{email});
        return result > 0;
    }
}
