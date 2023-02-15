package com.example.kebuncak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btLogin;
    private EditText uname, password;
    private DBManager dbManager;
    private TextView textViewLinkRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btLogin = (Button) findViewById(R.id.button);
        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);

        // Membuat objek dari kelas DBManager
        dbManager = new DBManager(this);
        dbManager.open();

        btLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (dbManager.checkUser(uname.getText().toString().trim()
                , password.getText().toString().trim())) {
            Preferences.setLoggedInStatus(getBaseContext(),true);
            Toast.makeText(getBaseContext(), "Login Berhasil", Toast.LENGTH_LONG).show();
            Intent intents = new Intent(LoginActivity.this, DataActivity.class);
            startActivity(intents);
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(getBaseContext(), "Username atau Password salah", Toast.LENGTH_LONG).show();
        }
    }
}
