package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.security.PasswordHelpers;
import com.b07.store.FindWantedData;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;

public class Authenticate extends AppCompatActivity {

    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(Authenticate.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);


        Button authenticateButton = (Button) findViewById(R.id.authBtn);
        authenticateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userId = (EditText) findViewById(R.id.idInput);
                //UserId
                int userIdCleaned = Integer.parseInt(userId.getText().toString());
                //RoleId
                int roleId = mydb.getUserRole(userIdCleaned);
                /*                String role = mydb.getRole(mydb.getUserRole(userIdCleaned));*/

                String actualPass = mydb.getPassword(userIdCleaned);
                EditText userPass = (EditText) findViewById(R.id.passwordInput);
                String enteredPass = userPass.getText().toString();
                String hashedEntered = PasswordHelpers.passwordHash(enteredPass);
                String together = actualPass + " " + enteredPass;
                if (actualPass.equals(hashedEntered)) {
                    new MaterialAlertDialogBuilder(Authenticate.this).setMessage("Authentication succeeded. Thank you.").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(Authenticate.this).setMessage("Authentication failed. \nYou are not an employee or you passed in the wrong password. \nPlease try again.").setPositiveButton("Ok", null).show();
                }
            }
        });

    }
}
