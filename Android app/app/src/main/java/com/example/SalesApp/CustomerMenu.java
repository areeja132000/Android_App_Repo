package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mockAmazonApp.inventory.ShoppingCart;

import java.io.Serializable;

public class CustomerMenu extends AppCompatActivity implements Serializable{

    ShoppingCart superCart;
    int customerId;
    int accountId;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                superCart = (ShoppingCart) data.getSerializableExtra("currentCart");
                customerId = data.getIntExtra("customerId", 0);
                accountId = data.getIntExtra("accountId", 0);
                loadActivity();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstTimeNeededFunctions();
        loadActivity();
    }

    private void loadActivity() {
        Button buttonAdd = (Button) findViewById(R.id.customerAddBtn);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, AddToCart.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
            }
        });

        Button buttonCheck = (Button) findViewById(R.id.customerViewBtn);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, ViewInventory.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
            }
        });

        Button buttonRemove = (Button) findViewById(R.id.customerRemoveBtn);
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, RemoveFromCart.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
            }
        });

        Button buttonCheckOut = (Button) findViewById(R.id.customerCheckoutBtn);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, CheckOut.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
            }
        });

        Button buttonRequestToSell = (Button) findViewById(R.id.customerRequestToSell);
        buttonRequestToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, RequestToSell.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
            }
        });

        Button buttonRequestAccount = (Button) findViewById(R.id.customerRequestAccountBtn);
        buttonRequestAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerMenu.this, RequestAccount.class);
                i.putExtra("customerId", customerId);
                i.putExtra("currentCart", (Serializable) superCart);
                i.putExtra("accountId", accountId);
                startActivityForResult(i, 1);
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

    private void firstTimeNeededFunctions() {
        setContentView(R.layout.activity_customer_menu);
        Intent intent = getIntent();

        superCart = (ShoppingCart) intent.getSerializableExtra("currentCart");
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);

        TextView title = (TextView) findViewById(R.id.customerMain);
        title.setText("Welcome to the store, "+ superCart.getCustomer().getName());
    }
}
