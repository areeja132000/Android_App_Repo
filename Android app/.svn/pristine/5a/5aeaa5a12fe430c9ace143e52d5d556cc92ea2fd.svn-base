package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.b07.exceptions.NotEnoughResourcesException;
import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.Item;
import com.b07.inventory.ShoppingCart;
import com.b07.store.FindWantedData;
import com.b07.store.ItemImpl;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddToCart extends AppCompatActivity implements Serializable {

    ShoppingCart currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        final Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customerId", 0);
        final int accountId = intent.getIntExtra("accountId", 0);


        Button buttonOne = (Button) findViewById(R.id.addBtn);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(AddToCart.this);

                EditText addNameInput = (EditText) findViewById(R.id.addItemName);
                String addName = addNameInput.getText().toString();

                EditText addQuantityInput = (EditText) findViewById(R.id.addQuantity);
                int addQuantity = Integer.parseInt(addQuantityInput.getText().toString());
                ShoppingCart currentCart = (ShoppingCart)intent.getSerializableExtra("cartToAdd");
                try {
                    currentCart = new ShoppingCart(new Customer(0,"", 0, ""));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (UnauthorizedException e) {
                    e.printStackTrace();
                }

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
                    new MaterialAlertDialogBuilder(AddToCart.this).setMessage("Before you had \n"+ currentCart.displayCart()).setPositiveButton("Ok", null).show();
                    currentCart.addItem(temp, addQuantity);
                    new MaterialAlertDialogBuilder(AddToCart.this).setMessage("Now have \n"+ currentCart.displayCart()).setPositiveButton("Ok", null).show();
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
                Intent intent = new Intent(AddToCart.this, CustomerMenu.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }
}
