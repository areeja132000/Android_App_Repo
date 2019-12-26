package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.Item;
import com.b07.inventory.ShoppingCart;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckCart extends AppCompatActivity implements Serializable {

    ShoppingCart currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_cart);
        Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customerId", 0);
        final int accountId = intent.getIntExtra("accountId", 0);

        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
        try {
            currentCart = new ShoppingCart(new Customer(0,"", 0, ""));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }

        HashMap<Item, Integer> items = currentCart.getItemMap();

        String addOn = "\n";

        int value = 0;
        Item key;

        for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
            value = entry.getValue();
            key = entry.getKey();
            addOn = addOn + (key.getName() + ": " + value + " " + "Price: " + key.getPrice() + "$ * "
                    + value + " = " + key.getPrice().multiply(new BigDecimal(value)) + "$");
        }

        TextView bookPrinting = (TextView) findViewById(R.id.displayCart);
        bookPrinting.setText(addOn);

        Button buttonBack = (Button) findViewById(R.id.backBtn);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckCart.this, CustomerMenu.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }

}
