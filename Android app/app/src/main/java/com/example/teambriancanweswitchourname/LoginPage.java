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

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        String passwordLogin;
        int userIdLogin;

        Button buttonOne = (Button) findViewById(R.id.loginButtton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(LoginPage.this);
                EditText userIdInput = (EditText) findViewById(R.id.loginIdInput);
                int userIdLogin = Integer.parseInt(userIdInput.getText().toString());
                EditText passwordInput = (EditText) findViewById(R.id.loginPassInput);
                String passwordLogin = passwordInput.getText().toString();

                Cursor allRoles = mydb.getRoles();
                allRoles.moveToFirst();
                int adminRoleId = 0;
                int employeeRoleId = 0;
                int customerRoleId = 0;

                while (!allRoles.isAfterLast()) {
                    if (allRoles.getString(1).equals("ADMIN")) {
                        adminRoleId = allRoles.getInt(0);
                    } else if (allRoles.getString(1).equals("EMPLOYEE")){
                        employeeRoleId = allRoles.getInt(0);
                    } else if (allRoles.getString(1).equals("CUSTOMER")){
                        customerRoleId = allRoles.getInt(0);
                    }
                    allRoles.moveToNext();
                }
                allRoles.close();


                if (PasswordHelpers.comparePassword(mydb.getPassword(userIdLogin), passwordLogin)) {
                    if (mydb.getUserRole(userIdLogin) == customerRoleId) {
                        Intent intent = new Intent(LoginPage.this, RestoreAccount.class);
                        intent.putExtra("customer", userIdLogin);
                        startActivity(intent);
                        finish();
                    } else if (mydb.getUserRole(userIdLogin) == employeeRoleId) {
                        Intent intent = new Intent(LoginPage.this, EmployeePanel.class);
                        intent.putExtra("employee", (Serializable) userIdLogin);
                        startActivity(intent);
                        finish();
                    } else if (mydb.getUserRole(userIdLogin) == adminRoleId) {
                        Intent intent = new Intent(LoginPage.this, AdminPanel.class);
                        intent.putExtra("admin", (Serializable) userIdLogin);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    new MaterialAlertDialogBuilder(LoginPage.this).setMessage("Incorrect password. Please try again.").setPositiveButton("Ok", null).show();
                }
            }
        });

    }
}