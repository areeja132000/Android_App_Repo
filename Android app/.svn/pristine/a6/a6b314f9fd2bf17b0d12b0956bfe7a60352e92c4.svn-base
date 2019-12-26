package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MakeEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_employee);

        Button buttonOne = (Button) findViewById(R.id.registerButton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(MakeEmployee.this);

                EditText name  = (EditText)findViewById(R.id.nameInput);
                String nameFinal = name.getText().toString();
                EditText age = (EditText)findViewById(R.id.ageInput);
                int ageFinal = Math.toIntExact(Integer.parseInt(age.getText().toString()));
                EditText address = (EditText)findViewById(R.id.addressInput);
                String addressFinal = address.getText().toString();
                EditText password = (EditText)findViewById(R.id.passInput);
                String passwordFinal = password.getText().toString();

                int employId = Math.toIntExact(mydb.insertNewUser(nameFinal, ageFinal, addressFinal, passwordFinal));
                mydb.insertUserRole(employId, 2);

                new MaterialAlertDialogBuilder(MakeEmployee.this).setMessage("You have create an Employee with id " +employId+" . Thank you.").setPositiveButton("Ok", null).show();

            }
        });

    }
}
