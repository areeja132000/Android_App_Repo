package com.example.teambriancanweswitchourname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.users.ItemTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ViewBooksAdmin extends AppCompatActivity {

    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(ViewBooksAdmin.this);

    /*    Cursor adminDetails = mydb.getUserDetails(adminId);
            adminDetails.moveToFirst();
        currentAdmin = new Admin(adminId, adminDetails.getString(1),
                    adminDetails.getInt(2), adminDetails.getString(3));*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        //Cursor to Itemized Sales table
        Cursor allItemSales = mydb.getItemizedSales();
        allItemSales.moveToFirst();

        //Array list of all sales
        ArrayList<Sale> allSales = new ArrayList<Sale>();
        while (!allItemSales.isAfterLast()) {
            Sale temp = new SaleImpl(allItemSales.getInt(1), allItemSales.getInt(2), allItemSales.getInt(3));
            allSales.add(temp);
            allItemSales.moveToNext();
        }

        BigDecimal totalRevenue = new BigDecimal("0.00");

        //Just add to this specific string
        String addOn="";

        // Create hashmap that will store total number of each item sold
        HashMap<String, Integer> itemNameToTotal = new HashMap<String, Integer>();
        for (ItemTypes item : ItemTypes.values()) {
            itemNameToTotal.put(item.toString(), 0);
        }

        for (int i = 0; i < allSales.size(); i++) {
            // Get both the items version and non-item version of the sale
            Sale itemizedSale = allSales.get(i);

            // Print out requirements
            addOn = addOn + ("Customer: " + itemizedSale.getUser().getName() + "\n");
            addOn = addOn + ("Purchase Number: " + itemizedSale.getId()+"\n");
            addOn = addOn + ("Total Purchase Price: " + itemizedSale.getTotalPrice()+"\n");
            // Update total revenue
            totalRevenue = totalRevenue.add(itemizedSale.getTotalPrice());

            // Get the item map of the sale
            HashMap<Item, Integer> ItemToQuantity = itemizedSale.getItemMap();

            Iterator<Map.Entry<Item, Integer>> ItemMapsToQuantity = ItemToQuantity.entrySet().iterator();
            // Get the first item in sale and print it out
            Map.Entry<Item, Integer> entry = ItemMapsToQuantity.next();
            addOn = addOn + ("Itemized Breakdown: " + entry.getKey().getName() + ": " + entry.getValue() +"\n");
            // Update hashmap that stores total number of item sold
            itemNameToTotal.put(entry.getKey().getName(),
                    itemNameToTotal.get(entry.getKey().getName()) + entry.getValue());
            // Print out the rest of the items
            while (ItemMapsToQuantity.hasNext()) {
                Map.Entry<Item, Integer> mapElement = ItemMapsToQuantity.next();
                System.out.println("                    " + mapElement.getKey().getName() + ": " + mapElement.getValue());
                // Update hashmap that stores total number of item sold
                itemNameToTotal.put(mapElement.getKey().getName(),
                        itemNameToTotal.get(mapElement.getKey().getName()) + mapElement.getValue());
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
                startActivity(intent);
                finish();
            }
        });
    }
}
