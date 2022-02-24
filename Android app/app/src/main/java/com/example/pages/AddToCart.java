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
import com.salesApp.inventory.Item;
import com.salesApp.inventory.ShoppingCart;
import com.salesApp.store.ItemImpl;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

public class AddToCart extends AppCompatActivity implements Serializable {

    int customerId;
    int accountId;
    ShoppingCart currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        final Intent intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);
        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");

        Button buttonOne = (Button) findViewById(R.id.addBtn);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(AddToCart.this);

                EditText addNameInput = (EditText) findViewById(R.id.addItemName);
                String addName = addNameInput.getText().toString();

                EditText addQuantityInput = (EditText) findViewById(R.id.addQuantity);
                int addQuantity = Integer.parseInt(addQuantityInput.getText().toString());

                //Cursor to Items table
                Cursor allItems = mydb.getAllItems();
                allItems.moveToFirst();

                Item temp = null;
                while (!allItems.isAfterLast()) {
                    if (addName.equals(allItems.getString(1))) {
                        temp = new ItemImpl(allItems.getInt(0), allItems.getString(1), new BigDecimal (""+allItems.getString(2)));
                    }
                    allItems.moveToNext();
                }
                allItems.close();

                try {
                    currentCart.addItem(temp, addQuantity);
                    new MaterialAlertDialogBuilder(AddToCart.this).setMessage("Your cart now:\n"+ currentCart.displayCart()).setPositiveButton("Ok", null).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotEnoughResourcesException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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
