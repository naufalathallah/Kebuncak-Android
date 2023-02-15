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

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addBtn,btHome, btAdd, btExit;
    private EditText sayurEditText, hargaEditText;
    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        sayurEditText = (EditText) findViewById(R.id.sayurr_edittext);
        hargaEditText = (EditText) findViewById(R.id.harga_edittext);
        addBtn = (Button) findViewById(R.id.btn_add);
        // Membuat objek dari kelas DBManager
        dbManager = new DBManager(this);
        dbManager.open();
        addBtn.setOnClickListener(this);

        btHome = (Button) findViewById(R.id.btHome);
        btAdd = (Button) findViewById(R.id.btAdd);
        btExit = (Button) findViewById(R.id.btExit);

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(AddActivity.this, DataActivity.class);
                startActivity(intents);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(AddActivity.this, AddActivity.class);
                startActivity(intents);
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                Intent intents = new Intent(AddActivity.this, LoginActivity.class);
                startActivity(intents);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                // Mengambil data inputan user
                final String sayur = sayurEditText.getText().toString();
                final String harga = hargaEditText.getText().toString();

                /* Memanggil fungsi insert melalui objek dbManager dengan parameter
                nilaikelas dan nama */
                dbManager.insert(sayur, harga);
                // Memindahkan halaman kembali ke daftar siswa
                Toast.makeText(getBaseContext(), "Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                Intent main = new Intent(AddActivity.this, DataActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
        }
    }
}
