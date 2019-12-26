package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.NotEnoughResourcesException;
import com.b07.exceptions.UnauthorizedException;
import com.b07.store.AdminFunctions;
import com.b07.store.CustomerFunctions;
import com.b07.store.EmployeeFunctions;
import com.b07.store.FindWantedData;
import com.b07.store.StoreInitializer;
import com.b07.store.frontend.GeneralLogIn;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    String passwordLogin;
    int userIdLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOne = (Button) findViewById(R.id.loginButtton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(MainActivity.this);

                EditText userIdInput = (EditText) findViewById(R.id.loginIdInput);
                int userIdLogin = Integer.parseInt(userIdInput.getText().toString());
                EditText passwordInput = (EditText) findViewById(R.id.loginPassInput);
                String passwordLogin = passwordInput.getText().toString();

                if (mydb.getUserRole(userIdLogin) == 3) {
                    Intent intent = new Intent(MainActivity.this, RestoreAccount.class);
                    intent.putExtra("customer", userIdLogin);
                    startActivity(intent);
                    finish();
                } else if (mydb.getUserRole(userIdLogin) == 2) {
                    Intent intent = new Intent(MainActivity.this, EmployeePanel.class);
                    intent.putExtra("employee", (Serializable) userIdLogin);
                    startActivity(intent);
                    finish();
                } else if (mydb.getUserRole(userIdLogin) == 1) {
                    Intent intent = new Intent(MainActivity.this, AdminPanel.class);
                    intent.putExtra("admin", (Serializable) userIdLogin);
                    startActivity(intent);
                    finish();
                }
            }
        });


        Button buttonTwo = (Button) findViewById(R.id.registerButton);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InitializeApp.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
