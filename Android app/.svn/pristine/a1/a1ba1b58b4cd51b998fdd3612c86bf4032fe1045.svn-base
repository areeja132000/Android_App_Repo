package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.ShoppingCart;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;

import java.sql.SQLException;

public class CheckOut extends AppCompatActivity {

    ShoppingCart currentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        final Intent intent = getIntent();
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

        Button buttonBack = (Button) findViewById(R.id.backBtn);
        final ShoppingCart finalCurrentCart = currentCart;
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOut.this, CustomerMenu.class);
                intent.putExtra("customerId", customerId);
                intent.putExtra("currentCart", finalCurrentCart);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }


}
