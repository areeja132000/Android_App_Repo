package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.teambriancanweswitchourname.R;
import com.salesApp.exceptions.DatabaseInsertException;
import com.salesApp.inventory.EmployeeInterface;
import com.salesApp.inventory.Inventory;
import com.salesApp.users.Employee;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.SQLException;

public class MakeEmployee extends AppCompatActivity {

    Employee currentEmployee;
    EmployeeInterface currentInterface;
    Inventory currentInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_employee);

        Button buttonOne = (Button) findViewById(R.id.registerButton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(MakeEmployee.this);
                Intent intent = getIntent();
                currentEmployee = (Employee) intent.getSerializableExtra("Employee");
                currentInterface = (EmployeeInterface) intent.getSerializableExtra("Interface");
                currentInventory = (Inventory) intent.getSerializableExtra("Inventory");

                EditText name  = (EditText)findViewById(R.id.nameInput);
                String nameFinal = name.getText().toString();
                EditText age = (EditText)findViewById(R.id.ageInput);
                int ageFinal = Math.toIntExact(Integer.parseInt(age.getText().toString()));
                EditText address = (EditText)findViewById(R.id.addressInput);
                String addressFinal = address.getText().toString();
                EditText password = (EditText)findViewById(R.id.passInput);
                String passwordFinal = password.getText().toString();

                int employeeId = -1;
                try {
                    employeeId = currentInterface.createEmployee(nameFinal, ageFinal, addressFinal, passwordFinal, mydb);
                } catch (DatabaseInsertException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (employeeId != -1) {
                    new MaterialAlertDialogBuilder(MakeEmployee.this).setMessage("You have created an Employee with id " +employeeId+" . Thank you.").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(MakeEmployee.this).setMessage("Employee creation failed.").setPositiveButton("Ok", null).show();
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
