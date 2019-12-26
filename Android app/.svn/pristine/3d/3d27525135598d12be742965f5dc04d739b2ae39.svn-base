package com.example.teambriancanweswitchourname;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.inventory.Item;
import com.b07.security.PasswordHelpers;
import com.b07.store.ItemImpl;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;

public class Restock extends AppCompatActivity {

    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(Restock.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        Button requestSellBtn = (Button) findViewById(R.id.restockBtn);
        requestSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    mydb.insertInventory(temp.getId(), ItemQuantityCleaned);
                    new MaterialAlertDialogBuilder(Restock.this).setMessage("Restock succeeded.").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(Restock.this).setMessage("Restock failed. Item does not exist in store.").setPositiveButton("Ok", null).show();
                }
            }
        });
    }
}
