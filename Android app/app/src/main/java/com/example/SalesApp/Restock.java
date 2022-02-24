package com.example.SalesApp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mockAmazonApp.inventory.EmployeeInterface;
import com.mockAmazonApp.inventory.Inventory;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.store.ItemImpl;
import com.mockAmazonApp.users.Employee;
import com.example.SalesApp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Restock extends AppCompatActivity {

    Employee currentEmployee;
    EmployeeInterface currentInterface;
    Inventory currentInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        Button requestSellBtn = (Button) findViewById(R.id.restockBtn);
        requestSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(Restock.this);
                Intent intent = getIntent();
                currentEmployee = (Employee) intent.getSerializableExtra("Employee");
                currentInterface = (EmployeeInterface) intent.getSerializableExtra("Interface");
                currentInventory = (Inventory) intent.getSerializableExtra("Inventory");

                EditText ItemName = (EditText)findViewById(R.id.RestockItem);
                String ItemNameCleaned = ItemName.getText().toString();

                EditText ItemQuantity = (EditText)findViewById(R.id.RestockQty);
                int ItemQuantityCleaned = Math.toIntExact(Integer.parseInt(ItemQuantity.getText().toString()));

                //Cursor to Items table
                Cursor allItems = mydb.getAllItems();
                allItems.moveToFirst();

                Item temp = null;
                Boolean isIn = false;
                while (!allItems.isAfterLast()) {
                    if (ItemNameCleaned.equals(allItems.getString(1))) {
                        temp = new ItemImpl(allItems.getInt(0), allItems.getString(1), new BigDecimal (""+allItems.getString(2)));
                    }
                    allItems.moveToNext();
                }

                if (temp!=null) {
                    boolean succeed = false;
                    boolean succeed2 = false;
                    try {
                        succeed = currentInterface.restockInventory(temp, ItemQuantityCleaned, mydb);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (succeed) {
                        succeed2 = currentInventory.updateMap(temp, ItemQuantityCleaned);
                    }
                    if (succeed && succeed2) {
                        new MaterialAlertDialogBuilder(Restock.this).setMessage("Restock succeeded.").setPositiveButton("Ok", null).show();
                    } else {
                        new MaterialAlertDialogBuilder(Restock.this).setMessage("Restock failed. Entered a negative value greater than amount available in store.").setPositiveButton("Ok", null).show();
                    }
                } else {
                    new MaterialAlertDialogBuilder(Restock.this).setMessage("Restock failed. Item does not exist in store.").setPositiveButton("Ok", null).show();
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
