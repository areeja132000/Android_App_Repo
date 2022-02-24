package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.inventory.ItemImpl;
import com.mockAmazonApp.inventory.ShoppingCart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class ViewInventory extends AppCompatActivity implements Serializable {

    int customerId;
    int accountId;
    ShoppingCart currentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Intent intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);
        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(ViewInventory.this);
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

        String addOn = "";
        for (HashMap.Entry<Item, Integer> entry : InventoryItems.entrySet()) {
            int quantity = entry.getValue();
            Item key = entry.getKey();
            addOn = addOn + ("# of " + key.getName() + " in stock: " + quantity + " " + "\t\tPrice: $" + key.getPrice()+"\n");
        }

        TextView bookPrinting = (TextView) findViewById(R.id.displayCart);
        bookPrinting.setText(addOn);

        Button buttonBack = (Button) findViewById(R.id.backBtn);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", currentCart);
                intent.putExtra("accountId", accountId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
