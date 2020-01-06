package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.Item;
import com.b07.inventory.ShoppingCart;
import com.b07.security.PasswordHelpers;
import com.b07.store.ItemImpl;
import com.b07.users.Customer;
import com.example.teambriancanweswitchourname.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

public class RequestAccount extends AppCompatActivity {

    ShoppingCart currentCart;
    int customerId;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_account);
        Intent intent = getIntent();
        customerId = intent.getIntExtra("customerId", 0);
        accountId = intent.getIntExtra("accountId", 0);
        currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");

        Button requestSellBtn = (Button) findViewById(R.id.ReqAcctBtn);
        requestSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RequestAccount.this);
                long acctId = mydb.insertAccount(customerId, true);

                for (Map.Entry mapElement : currentCart.getItemMap().entrySet()) {
                    Item currentItem = (ItemImpl)mapElement.getKey();
                    int quantity = (int)mapElement.getValue();
                    mydb.insertAccountLine((int)acctId, currentItem.getId(), quantity);
                }
                accountId = (int)acctId;
                TextView credentialsPrinting = (TextView) findViewById(R.id.credentials);
                credentialsPrinting.setText("Account Number: " + Long.toString(acctId));

            }
        });

        Button buttonBack = (Button) findViewById(R.id.backBtn);
        final ShoppingCart finalCurrentCart = currentCart;
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
