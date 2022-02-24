package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mockAmazonApp.exceptions.DatabaseInsertException;
import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.inventory.ShoppingCart;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

public class CheckOut extends AppCompatActivity {

    ShoppingCart currentCart;
    int customerId;
    int accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Intent intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);
        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
        HashMap <Item, Integer> itemToInteger = currentCart.getItemMap();

        //Add to this specific string and total price
        String addOn= null;
        try {
            addOn = currentCart.displayCart();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BigDecimal totalRevenue = new BigDecimal("0.00");

        // Iterate through hashmap
        for (HashMap.Entry mapElement : itemToInteger.entrySet()) {
            Item currentItem = (Item)mapElement.getKey();
            int quantity = (int)mapElement.getValue();
            BigDecimal totalPriceItem = currentItem.getPrice().multiply(new BigDecimal(quantity+""));
            totalRevenue = totalRevenue.add(totalPriceItem);
        }

        addOn = addOn + "\nTotal Price: " + totalRevenue;
        TextView cartPrinting = (TextView) findViewById(R.id.displayCart);
        cartPrinting.setText(addOn);

        Button buttonCheckout = (Button) findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(CheckOut.this);
                boolean success = false;
                try {
                    success = currentCart.checkOut(mydb);
                } catch (DatabaseInsertException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotEnoughResourcesException e) {
                    e.printStackTrace();
                }
                if (success) {
                    if (accountId != 0) {
                        mydb.updateAccountStatus(accountId, false);
                    }
                    new MaterialAlertDialogBuilder(CheckOut.this).setMessage("Checkout successfull :) Your cart has been cleared, and if you had an account, it has been set to inactive.").setPositiveButton("Ok", null).show();
                } else {
                    new MaterialAlertDialogBuilder(CheckOut.this).setMessage("Checkout failed. Check store stock to see if enough items are available.").setPositiveButton("Ok", null).show();
                }
            }
        });

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
