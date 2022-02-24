package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.teambriancanweswitchourname.R;

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
