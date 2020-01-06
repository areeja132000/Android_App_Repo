package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.EmployeeInterface;
import com.b07.inventory.Inventory;
import com.b07.users.Employee;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.SQLException;

public class MakeCustomer extends AppCompatActivity {

    Employee currentEmployee;
    EmployeeInterface currentInterface;
    Inventory currentInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_customer);
        Intent intent = getIntent();
        currentEmployee = (Employee) intent.getSerializableExtra("Employee");
        currentInterface = (EmployeeInterface) intent.getSerializableExtra("Interface");
        currentInventory = (Inventory) intent.getSerializableExtra("Inventory");

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

                int customerId = -1;
                try {
                     customerId = currentInterface.createCustomer(nameFinal, ageFinal, addressFinal, passwordFinal, mydb);
                } catch (DatabaseInsertException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (customerId != -1) {
                    new MaterialAlertDialogBuilder(MakeCustomer.this).setMessage("You have created a Customer with id " +customerId+" . Thank you.").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(MakeCustomer.this).setMessage("Customer creation failed").setPositiveButton("Ok", null).show();
                }
            }
        });

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Employee", currentEmployee);
                intent.putExtra("Interface", currentInterface);
                intent.putExtra("Inventory", currentInventory);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
