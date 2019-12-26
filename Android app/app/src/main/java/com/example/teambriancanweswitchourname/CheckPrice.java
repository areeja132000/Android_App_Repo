package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.inventory.ShoppingCart;
import com.b07.security.PasswordHelpers;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CheckPrice extends AppCompatActivity {

    ShoppingCart currentCart;
    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(CheckPrice.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_price);
        final Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customerId", 0);
        final int accountId = intent.getIntExtra("accountId", 0);

        ShoppingCart currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
        try {
            currentCart = new ShoppingCart(new Customer(0,"", 0, ""));
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        BigDecimal totalPrice = currentCart.getTotal();
        TextView outputPrice = (TextView) findViewById(R.id.priceOutputText);
        outputPrice.setText(totalPrice + "");


        Button buttonBack = (Button) findViewById(R.id.backBtn);
        final ShoppingCart finalCurrentCart = currentCart;
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckPrice.this, CustomerMenu.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", finalCurrentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }



}
