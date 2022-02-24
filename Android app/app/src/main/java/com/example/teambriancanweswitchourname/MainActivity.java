package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.security.PasswordHelpers;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(MainActivity.this);
        if (mydb.getRoles()!= null && mydb.getRoles().getCount() > 0) {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, InitializeApp.class);
            startActivity(intent);
            finish();
        }

    }
}
