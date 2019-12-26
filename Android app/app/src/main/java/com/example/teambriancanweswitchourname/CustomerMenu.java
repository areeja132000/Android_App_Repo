package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.ShoppingCart;
import com.b07.users.Customer;
import com.b07.users.User;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.SQLException;

public class CustomerMenu extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menu);
        Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customer", 0);
        final ShoppingCart currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
        final int accountId = intent.getIntExtra("accountId", 0);
        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(CustomerMenu.this);
        Customer currentCustomer = new Customer(customerId, "Name", 60, "address");

        if (customerId != 0) {
            Cursor customerDetails = mydb.getUserDetails(customerId);
            customerDetails.moveToFirst();
            currentCustomer = new Customer(customerId, customerDetails.getString(1), customerDetails.getInt(2), customerDetails.getString(3));
            customerDetails.close();
        }

        TextView title = (TextView) findViewById(R.id.customerMain);
        title.setText("Welcome to Ivy's Exotic Pet Store\nWelcome, "+ currentCustomer.getName());


        Button buttonAdd = (Button) findViewById(R.id.customerAddBtn);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, AddToCart.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("cartForAdd", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonCheck = (Button) findViewById(R.id.customerViewBtn);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, CheckCart.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.customerRemoveBtn);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, RemoveFromCart.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonPrice = (Button) findViewById(R.id.customerTotalPriceBtn);
        buttonPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, CheckPrice.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonCheckOut = (Button) findViewById(R.id.customerCheckoutBtn);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, CheckOut.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRequestToSell = (Button) findViewById(R.id.customerRequestToSell);
        buttonRequestToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, RequestToSell.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRequestAccount = (Button) findViewById(R.id.customerRequestAccountBtn);
        buttonRequestAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, RequestAccount.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", (Serializable) currentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
