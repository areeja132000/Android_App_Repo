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

public class RemoveFromCart extends AppCompatActivity implements Serializable {

    ShoppingCart currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_from_cart);
        final Intent intent = getIntent();
        final int customerId = intent.getIntExtra("customerId", 0);
        final int accountId = intent.getIntExtra("accountId", 0);


        Button buttonOne = (Button) findViewById(R.id.removeBtn);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RemoveFromCart.this);

                EditText removeNameInput = (EditText) findViewById(R.id.removeItemName);
                String removeName = removeNameInput.getText().toString();

                EditText removeQuantityInput = (EditText) findViewById(R.id.removeQuantity);
                int removeQuantity = Integer.parseInt(removeQuantityInput.getText().toString());

                ShoppingCart currentCart = (ShoppingCart)intent.getSerializableExtra("currentCart");
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
                    if (removeName.equals(allItems.getString(1))) {
                        temp = new ItemImpl(allItems.getInt(0), allItems.getString(1), new BigDecimal (""+allItems.getString(2)));
                    }
                    allItems.moveToNext();
                }
                try {
                    currentCart.removeItem(temp, removeQuantity);
                    new MaterialAlertDialogBuilder(RemoveFromCart.this).setMessage("Now have \n"+ currentCart.displayCart()).setPositiveButton("Ok", null).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotEnoughResourcesException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Button buttonBack = (Button) findViewById(R.id.backBtn);
                final ShoppingCart finalCurrentCart = currentCart;
                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RemoveFromCart.this, CustomerMenu.class);
                        intent.putExtra("customerId", customerId);
                        intent.putExtra("currentCart", finalCurrentCart);
                        intent.putExtra("accountId", accountId);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });
    }


}
/*            Intent intent = getIntent();
            currentCart = (ShoppingCart)intent.getSerializableExtra("cartForAdd");
            Spinner spinner = (Spinner)findViewById(R.id.spinnerOfItems);
            DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(RemoveFromCart.this);
            populateSpinnerFromDatabase(mydb, spinner);
        }

    public void populateSpinnerFromDatabase(DatabaseDriverAndroid mydb, Spinner spinner){

        Cursor items = mydb.getAllItems();
        List<String> itemList= new ArrayList<String>();
        while (items.moveToNext()){
            itemList.add(items.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.text, itemList);
        adapter.setDropDownViewResource(android.R.layout.);
        spinner.setAdapter(adapter);
    }
}*/

