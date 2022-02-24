package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mockAmazonApp.inventory.CurrentInventory;
import com.mockAmazonApp.inventory.EmployeeInterface;
import com.mockAmazonApp.inventory.Inventory;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.inventory.ItemImpl;
import com.mockAmazonApp.users.Employee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeePanel extends AppCompatActivity implements Serializable {

    Employee currentEmployee;
    EmployeeInterface currentInterface;
    Inventory currentInventory;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                currentEmployee = (Employee) data.getSerializableExtra("Employee");
                currentInterface = (EmployeeInterface) data.getSerializableExtra("Interface");
                currentInventory = (Inventory) data.getSerializableExtra("Inventory");
                loadActivity();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_panel);

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(EmployeePanel.this);
        Intent intent = getIntent();
        Integer employeeId = intent.getIntExtra("employee", 0);

        //Set current employee
        Cursor employeeDetails = mydb.getUserDetails(employeeId);
        employeeDetails.moveToFirst();
        currentEmployee = new Employee(employeeId, employeeDetails.getString(1), employeeDetails.getInt(2), employeeDetails.getString(3));

        //Cursor to Inventory table
        Cursor allInventoryInput = mydb.getInventory();
        allInventoryInput.moveToFirst();

        //Hashmap of all Inventory Items
        HashMap<Item, Integer> InventoryItems = new HashMap<Item, Integer>();
        while (!allInventoryInput.isAfterLast()) {
            int itemId = allInventoryInput.getInt(0);
            int quantity = allInventoryInput.getInt(1);
            Cursor itemRow = mydb.getItem(itemId);
            itemRow.moveToFirst();
            Item temp = new ItemImpl(itemId, itemRow.getString(1), new BigDecimal(itemRow.getString(2)));
            itemRow.close();
            InventoryItems.put(temp, quantity);
            allInventoryInput.moveToNext();
        }
        allInventoryInput.close();

        //Make new inventory
        currentInventory = new CurrentInventory(InventoryItems);

        //Set employee interface

        currentInterface = new EmployeeInterface(currentInventory);
        try {
            currentInterface.setCurrentEmployee(currentEmployee, mydb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadActivity();
    }

    private void loadActivity() {
        Button buttonCheck = (Button) findViewById(R.id.restockBtn);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeePanel.this, Restock.class);
                i.putExtra("Employee", currentEmployee);
                i.putExtra("Interface", currentInterface);
                i.putExtra("Inventory", currentInventory);
                startActivityForResult(i, 1);
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.makeEmployeeBtn);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeePanel.this, MakeEmployee.class);
                i.putExtra("Employee", currentEmployee);
                i.putExtra("Interface", currentInterface);
                i.putExtra("Inventory", currentInventory);
                startActivityForResult(i, 1);
            }
        });

        Button buttonPrice = (Button) findViewById(R.id.makeCustomerBtn);
        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeePanel.this, MakeCustomer.class);
                i.putExtra("Employee", currentEmployee);
                i.putExtra("Interface", currentInterface);
                i.putExtra("Inventory", currentInventory);
                startActivityForResult(i, 1);
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
