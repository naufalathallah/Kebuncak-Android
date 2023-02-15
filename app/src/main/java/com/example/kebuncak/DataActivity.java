package com.example.kebuncak;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {
    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private Button btHome, btAdd, btExit;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.SAYUR, DatabaseHelper.HARGA };

    final int[] to = new int[] { R.id.id, R.id.sayur, R.id.harga };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        /* Adapter untuk menunjuk data di database
        untuk di tampilkan dalam list siswa yang mana data tersebut
        menunjuk ke fragment dari ListView */
        adapter = new SimpleCursorAdapter(this, R.layout.activity_fragment, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner untuk Data Siswa yang telah ada di database
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                // Mengambil nilai list yang dipilih
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView sayurTextView = (TextView) view.findViewById(R.id.sayur);
                TextView hargaTextView = (TextView) view.findViewById(R.id.harga);

                // Menyimpan nilai list yang di pilih ke variabel
                String id = idTextView.getText().toString();
                String sayur = sayurTextView.getText().toString();
                String harga = hargaTextView.getText().toString();

                // Proses Intent untuk mengirim data ke halaman Edit
                Intent modify_intent = new Intent(getApplicationContext(), EditActivity.class);
                modify_intent.putExtra("sayur", sayur);
                modify_intent.putExtra("harga", harga);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        btHome = (Button) findViewById(R.id.btHome);
        btAdd = (Button) findViewById(R.id.btAdd);
        btExit = (Button) findViewById(R.id.btExit);

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(DataActivity.this, DataActivity.class);
                startActivity(intents);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(DataActivity.this, AddActivity.class);
                startActivity(intents);
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                Intent intents = new Intent(DataActivity.this, LoginActivity.class);
                startActivity(intents);
                finish();
            }
        });
    }
}
