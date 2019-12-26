package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.ShoppingCart;
import com.b07.users.Customer;

import java.io.Serializable;
import java.sql.SQLException;

public class RestoreAccount extends AppCompatActivity {

    ShoppingCart currentCart;
    Customer currentCustomer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_account);
        Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customer", 0);



        final DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RestoreAccount.this);

        Button buttonCheckOut = (Button) findViewById(R.id.restoreButton);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText restoreInput = (EditText) findViewById(R.id.restoreAccountInput);
                int accountId = Integer.parseInt(restoreInput.getText().toString());

                Intent intent = new Intent(RestoreAccount.this, CustomerMenu.class);
                intent.putExtra("customerCart", currentCart);
                intent.putExtra("customer", customerId);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });

        Button buttonRequestToSell = (Button) findViewById(R.id.NoThankButton);
        buttonRequestToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestoreAccount.this, CustomerMenu.class);
                int accountId = 0;
                if (customerId != 0) {
                    Cursor customerDetails = mydb.getUserDetails(customerId);
                    customerDetails.moveToFirst();
                    currentCustomer = new Customer(customerId, customerDetails.getString(1), customerDetails.getInt(2), customerDetails.getString(3));
                    customerDetails.close();
                }
                try {
                    currentCart = new ShoppingCart(currentCustomer);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (UnauthorizedException e) {
                    e.printStackTrace();
                }
                intent.putExtra("customerCart", currentCart);
                intent.putExtra("customer", customerId);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }
}
