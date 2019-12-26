package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.b07.inventory.CurrentInventory;
import com.b07.inventory.EmployeeInterface;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeePanel extends AppCompatActivity implements Serializable {

    Employee currentEmployee;
    EmployeeInterface currentInterface;
    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(EmployeePanel.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel);
        Intent intent = getIntent();
        Integer employeeId = intent.getIntExtra("employee", 0);
        Cursor employeeDetails = mydb.getUserDetails(employeeId);
        employeeDetails.moveToFirst();
        //Set current employee
        currentEmployee = new Employee(employeeId, employeeDetails.getString(1), employeeDetails.getInt(2), employeeDetails.getString(3));
        //Set empty hashmap
        HashMap<Item, Integer> emptyHashmap = new HashMap<Item, Integer>();
        //Make new inventory
        Inventory currentInventory = new CurrentInventory(emptyHashmap);
        //Set employee interface
        try {
            currentInterface = new EmployeeInterface(currentEmployee, currentInventory, mydb);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Button buttonAdd = (Button) findViewById(R.id.authenticateBtn);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePanel.this, Authenticate.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonCheck = (Button) findViewById(R.id.restockBtn);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePanel.this, Restock.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.makeEmployeeBtn);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePanel.this, MakeEmployee.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonPrice = (Button) findViewById(R.id.makeCustomerBtn);
        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePanel.this, MakeCustomer.class);
                startActivity(intent);
                finish();
            }
        });

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePanel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
