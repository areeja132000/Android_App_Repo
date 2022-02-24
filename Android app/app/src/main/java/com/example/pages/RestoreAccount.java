package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.teambriancanweswitchourname.R;
import com.salesApp.exceptions.NotEnoughResourcesException;
import com.salesApp.exceptions.UnauthorizedException;
import com.salesApp.inventory.Item;
import com.salesApp.inventory.ItemImpl;
import com.salesApp.inventory.ShoppingCart;
import com.salesApp.users.Customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

public class RestoreAccount extends AppCompatActivity {

    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RestoreAccount.this);
    int customerId;
    ShoppingCart currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_account);
        Intent intent = getIntent();
        customerId = intent.getIntExtra("customer", 0);

        Cursor customerDetails = mydb.getUserDetails(customerId);
        customerDetails.moveToFirst();
        Customer currentCustomer = new Customer(customerId, customerDetails.getString(1), customerDetails.getInt(2), customerDetails.getString(3));
        customerDetails.close();

        try {
            currentCart = new ShoppingCart(currentCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        Button buttonCheckOut = (Button) findViewById(R.id.restoreButton);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText restoreInput = (EditText) findViewById(R.id.restoreAccountInput);
                int accountId = Integer.parseInt(restoreInput.getText().toString());

                boolean possible = false;
                Cursor allAccts = mydb.getUserActiveAccounts(customerId);
                allAccts.moveToFirst();
                while (!allAccts.isAfterLast()) {
                    if (allAccts.getInt(0) == accountId) {
                        possible = true;
                    }
                    allAccts.moveToNext();
                }
                allAccts.close();

                if (possible) {
                    Cursor allAcctDetails = mydb.getAccountDetails(accountId);
                    allAcctDetails.moveToFirst();
                    while (!allAcctDetails.isAfterLast()) {
                        Cursor itemId = mydb.getItem(allAcctDetails.getInt(1));
                        itemId.moveToFirst();
                        Item temp = new ItemImpl(itemId.getInt(0), itemId.getString(1), new BigDecimal(itemId.getString(2)));
                        try {
                            currentCart.addItem(temp, allAcctDetails.getInt(2));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NotEnoughResourcesException e) {
                            e.printStackTrace();
                        }
                        itemId.close();
                        allAcctDetails.moveToNext();
                    }
                    allAcctDetails.close();

                    Intent intent = new Intent(RestoreAccount.this, CustomerMenu.class);
                    intent.putExtra("currentCart", (Serializable) currentCart);
                    intent.putExtra("customerId", customerId);
                    intent.putExtra("accountId", accountId);
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button buttonRequestToSell = (Button) findViewById(R.id.NoThankButton);
        buttonRequestToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestoreAccount.this, CustomerMenu.class);
                int accountId = 0;
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("customerId", customerId);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }
}
