package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MakeCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_customer);

        Button buttonOne = (Button) findViewById(R.id.custRegisterButton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(MakeCustomer.this);

                EditText name  = (EditText)findViewById(R.id.custNameInput);
                String nameFinal = name.getText().toString();
                EditText age = (EditText)findViewById(R.id.custAgeInput);
                int ageFinal = Math.toIntExact(Integer.parseInt(age.getText().toString()));
                EditText address = (EditText)findViewById(R.id.custAddressInput);
                String addressFinal = address.getText().toString();
                EditText password = (EditText)findViewById(R.id.custPassInput);
                String passwordFinal = password.getText().toString();

                int customerId = Math.toIntExact(mydb.insertNewUser(nameFinal, ageFinal, addressFinal, passwordFinal));
                mydb.insertUserRole(customerId, 3);

                new MaterialAlertDialogBuilder(MakeCustomer.this).setMessage("You have create an Customer with id " +customerId+" . Thank you.").setPositiveButton("Ok", null).show();
            }
        });

    }
}
