package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.store.StoreInitializer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.sql.SQLException;

public class InitializeApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_app);

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(InitializeApp.this);
        try {
            StoreInitializer.fullInitialize(mydb);
        } catch (DatabaseInsertException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button buttonOne = (Button) findViewById(R.id.first_admin);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(InitializeApp.this);

                EditText name  = (EditText)findViewById(R.id.adminName);
                String nameFinal = name.getText().toString();
                EditText age = (EditText)findViewById(R.id.adminAge);
                int ageFinal = Integer.parseInt(age.getText().toString());
                EditText address = (EditText)findViewById(R.id.adminAddress);
                String addressFinal = address.getText().toString();
                EditText password = (EditText)findViewById(R.id.adminPassword);
                String passwordFinal = password.getText().toString();

                int adminId = Math.toIntExact(mydb.insertNewUser(nameFinal, ageFinal, addressFinal, passwordFinal));
                mydb.insertUserRole(adminId, 2);

                new MaterialAlertDialogBuilder(InitializeApp.this).setMessage("You have create an Administrator with id " +adminId+
                        " . Thank you.").setPositiveButton("Ok", null).show();

            }
        });

        Button buttonTwo = (Button) findViewById(R.id.first_employee);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(InitializeApp.this);

                EditText name  = (EditText)findViewById(R.id.employName);
                String nameFinal = name.getText().toString();
                EditText age = (EditText)findViewById(R.id.employAge);
                int ageFinal = Integer.parseInt(age.getText().toString());
                EditText address = (EditText)findViewById(R.id.employAddress);
                String addressFinal = address.getText().toString();
                EditText password = (EditText)findViewById(R.id.employPass);
                String passwordFinal = password.getText().toString();

                int employId = Math.toIntExact(mydb.insertNewUser(nameFinal, ageFinal, addressFinal, passwordFinal));
                mydb.insertUserRole(employId, 1);

                new MaterialAlertDialogBuilder(InitializeApp.this).setMessage("You have create an Employee with id " + employId+
                        " . Thank you.").setPositiveButton("Ok", null).show();

            }
        });

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitializeApp.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
