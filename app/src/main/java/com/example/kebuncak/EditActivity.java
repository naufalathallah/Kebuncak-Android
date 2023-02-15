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

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText sayurText, hargaText;
    private Button updateBtn, deleteBtn,btHome, btAdd, btExit;
    private long _id;
    private DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbManager = new DBManager(this);
        dbManager.open();

        sayurText = (EditText) findViewById(R.id.sayurr_edittext);
        hargaText = (EditText) findViewById(R.id.harga_edittext);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        /* Membuat objek Intent dengan nilai yang dikirim objek Intent
        yang telah memanggil kelas ini sebelumnya */
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String sayur = intent.getStringExtra("sayur");
        String harga = intent.getStringExtra("harga");

        _id = Long.parseLong(id);
        sayurText.setText(sayur);
        hargaText.setText(harga);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        btHome = (Button) findViewById(R.id.btHome);
        btAdd = (Button) findViewById(R.id.btAdd);
        btExit = (Button) findViewById(R.id.btExit);

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(EditActivity.this, DataActivity.class);
                startActivity(intents);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(EditActivity.this, AddActivity.class);
                startActivity(intents);
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                Intent intents = new Intent(EditActivity.this, LoginActivity.class);
                startActivity(intents);
                finish();
            }
        });
    }

    //update atau delete
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Pilihan Update
            case R.id.btn_update:
                // Menyimpan nilai kelas dan nama baru ke variabel
                String sayur = sayurText.getText().toString();
                String harga = hargaText.getText().toString();
                if(harga.equals("0")){
                    Toast.makeText(getBaseContext(), "Tidak boleh 0", Toast.LENGTH_LONG).show();
                }else {
                    /* Memanggil fungsi update melalui objek dbManager
                fungsi ini membawa tiga parameter yakni _id, kelas, nama */
                    dbManager.update(_id, sayur, harga);
                    Toast.makeText(getBaseContext(), "Berhasil diubah", Toast.LENGTH_LONG).show();
                /* Setelah selesai, akan memanggil fungsi returnHome()
                untuk kembali kehalaman utama */
                    this.returnHome();
                }
                break;
            // Pilihan Update
            case R.id.btn_delete:
                // Memanggil fungsi delete dengan parameter _id
                dbManager.delete(_id);
                Toast.makeText(getBaseContext(), "Berhasil dihapus", Toast.LENGTH_LONG).show();
                this.returnHome();
                break;
        }
    }

    // Fungsi untuk kembali ke halaman awal
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), DataActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
