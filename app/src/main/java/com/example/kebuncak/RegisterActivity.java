package com.example.kebuncak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addbutton;
    private EditText uname;
    private EditText password;
    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        addbutton = (Button) findViewById(R.id.button);

        // Membuat objek dari kelas DBManager
        dbManager = new DBManager(this);
        dbManager.open();
        addbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // Mengambil data inputan user
                final String nama = uname.getText().toString();
                final String pw = password.getText().toString();

                if (nama.isEmpty() || pw.isEmpty()){
                    Toast.makeText(getBaseContext(), "Tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    /* Memanggil fungsi insert melalui objek dbManager */
                    dbManager.addUser(nama, pw);
                    Toast.makeText(getBaseContext(), "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
        }
    }
}
