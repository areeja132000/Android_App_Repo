package com.example.SalesApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.inventory.ItemImpl;
import com.mockAmazonApp.store.Sale;
import com.mockAmazonApp.store.SaleImpl;
import com.mockAmazonApp.users.Admin;
import com.mockAmazonApp.users.Customer;
import com.mockAmazonApp.users.ItemTypes;
import com.mockAmazonApp.users.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewBooksAdmin extends AppCompatActivity {

    Admin currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(ViewBooksAdmin.this);
        Intent intent = getIntent();
        currentAdmin = (Admin) intent.getSerializableExtra("adminUser");

        //Cursor to Itemized Sales table
        Cursor allItemSales = mydb.getItemizedSales();
        allItemSales.moveToFirst();

        //Array list of all Itemized sales
        ArrayList<Sale> allSales = new ArrayList<Sale>();
        while (!allItemSales.isAfterLast()) {
            Sale temp = new SaleImpl(allItemSales.getInt(0), allItemSales.getInt(1), allItemSales.getInt(2));
            allSales.add(temp);
            allItemSales.moveToNext();
        }
        allItemSales.close();

        //Cursor to Sales table
        Cursor sales = mydb.getSales();
        sales.moveToFirst();

        //Array list of all sales
        ArrayList<Sale> everySale = new ArrayList<Sale>();
        while (!sales.isAfterLast()) {
            Cursor toUserDetails = mydb.getUserDetails(sales.getInt(1));
            toUserDetails.moveToFirst();
            User currentUser = new Customer(toUserDetails.getInt(0), toUserDetails.getString(1), toUserDetails.getInt(2), toUserDetails.getString(3));
            toUserDetails.close();
            Sale temp = new SaleImpl(sales.getInt(0), currentUser, new BigDecimal(sales.getString(2)));
            everySale.add(temp);
            sales.moveToNext();
        }

        sales.close();

        BigDecimal totalRevenue = new BigDecimal("0.00");

        //Just add to this specific string
        String addOn="";

        // Create hashmap that will store total number of each item sold
        HashMap<String, Integer> itemNameToTotal = new HashMap<String, Integer>();
        for (ItemTypes item : ItemTypes.values()) {
            itemNameToTotal.put(item.toString(), 0);
        }



        for (int i = 0; i < everySale.size(); i++) {
            // Get both the items version and non-item version of the sale
            Sale itemizedSale = everySale.get(i);

            // Print out requirements
            addOn = addOn + ("Customer: " + itemizedSale.getUser().getName() + "\n");
            addOn = addOn + ("Purchase Number: " + itemizedSale.getId()+"\n");
            addOn = addOn + ("Total Purchase Price: " + itemizedSale.getTotalPrice()+"\n");
            // Update total revenue
            totalRevenue = totalRevenue.add(itemizedSale.getTotalPrice());

            // Get the item map of the sale
            for (int m = 0; m < allSales.size(); m++) {
                int x=0;
                if (allSales.get(m).getId() == itemizedSale.getId()) {
                    Sale temp = allSales.get(m);

                    Cursor pointerAtItem = mydb.getItem(temp.getItemId());
                    pointerAtItem.moveToFirst();
                    Item currentItem = new ItemImpl(pointerAtItem.getInt(0), pointerAtItem.getString(1), new BigDecimal(pointerAtItem.getString(2)));
                    pointerAtItem.close();

                    if (x == 0) {
                        addOn = addOn + ("Itemized Breakdown: " + currentItem.getName() + ": " + temp.getQuantity() +"\n");
                        itemNameToTotal.put(currentItem.getName(), itemNameToTotal.get(currentItem.getName()) + temp.getQuantity());
                        x++;
                    } else {
                        addOn = addOn + ("                    " + currentItem.getName() + ": " + temp.getQuantity() + "\n");
                        itemNameToTotal.put(currentItem.getName(), itemNameToTotal.get(currentItem.getName()) + temp.getQuantity());
                    }
                }

            }
            addOn = addOn + ("----------------------------------------------------" +"\n");
        }

        // Loop through every item in store and print how much of it was sold
        for (Map.Entry<String, Integer> currentItem : itemNameToTotal.entrySet()) {
            addOn = addOn + ("Number " + currentItem.getKey() + " Sold: " + currentItem.getValue()+"\n");
        }
        addOn = addOn + ("TOTAL SALES: " + totalRevenue + "\n");

        TextView bookPrinting = (TextView) findViewById(R.id.printBooksOutput);
        bookPrinting.setText(addOn);

        Button buttonBack = (Button) findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBooksAdmin.this, AdminPanel.class);
                intent.putExtra("admin", currentAdmin.getId());
                startActivity(intent);
                finish();
            }
        });

    }
}
