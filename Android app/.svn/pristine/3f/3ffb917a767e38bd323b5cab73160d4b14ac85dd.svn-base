package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

    public class AdminPromote extends AppCompatActivity {

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(AdminPromote.this);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_promote);

            Button promoteButton = (Button) findViewById(R.id.promoteBtn);
            promoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText employeeId = (EditText) findViewById(R.id.promotedInput);
                    int employeeIdCleaned = Integer.parseInt(employeeId.getText().toString());
                    int roleId = mydb.getUserRole(employeeIdCleaned);
                    String role = mydb.getRole(roleId);
                    if (role.equals("EMPLOYEE")) {
                        Cursor allRoles = mydb.getRoles();
                        allRoles.moveToFirst();
                        int adminRoleId = 0;
                        while (!allRoles.isAfterLast()) {
                            if (allRoles.getString(1).equals("ADMIN")) {
                                adminRoleId = allRoles.getInt(0);
                            }
                            allRoles.moveToNext();
                        }
                        mydb.updateUserRole(adminRoleId, employeeIdCleaned);
                        new MaterialAlertDialogBuilder(AdminPromote.this).setMessage("Employee promoted. Thank you.").setPositiveButton("Ok", null).show();
                    } else {
                        new MaterialAlertDialogBuilder(AdminPromote.this).setMessage("This is not an employee. Promotion failed. ").setPositiveButton("Ok", null).show();
                    }

                }
            });

            Button buttonBack = (Button) findViewById(R.id.backButton);
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminPromote.this, AdminPanel.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

