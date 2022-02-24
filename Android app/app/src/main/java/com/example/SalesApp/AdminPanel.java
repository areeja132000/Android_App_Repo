package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mockAmazonApp.users.Admin;

import java.io.Serializable;

public class AdminPanel extends AppCompatActivity {

    Admin currentAdmin;
    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(AdminPanel.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Intent intent = getIntent();
        Integer adminId = intent.getIntExtra("admin", 0);
        Cursor adminDetails = mydb.getUserDetails(adminId);
        adminDetails.moveToFirst();
        currentAdmin = new Admin(adminId, adminDetails.getString(1), adminDetails.getInt(2), adminDetails.getString(3));

        Button buttonPromote = (Button) findViewById(R.id.adminPromoteBtn);
        buttonPromote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, AdminPromote.class);
                intent.putExtra("adminUser", (Serializable) currentAdmin);
                startActivity(intent);
                finish();
            }
        });

        Button buttonViewBook = (Button) findViewById(R.id.adminViewBookBtn);
        buttonViewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, ViewBooksAdmin.class);
                intent.putExtra("adminUser", (Serializable) currentAdmin);
                startActivity(intent);
                finish();
            }
        });

        Button buttonViewActive = (Button) findViewById(R.id.viewActiveBtn);
        buttonViewActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, ViewActiveAccounts.class);
                intent.putExtra("adminUser", (Serializable) currentAdmin);
                startActivity(intent);
                finish();
            }
        });

        Button buttonViewInactive = (Button) findViewById(R.id.viewInactiveBtn);
        buttonViewInactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, ViewInactiveAccounts.class);
                intent.putExtra("adminUser", (Serializable) currentAdmin);
                startActivity(intent);
                finish();
            }
        });

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
