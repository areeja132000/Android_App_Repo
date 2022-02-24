package com.mockAmazonApp.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.users.ItemTypes;
import com.example.SalesApp.DatabaseDriverAndroid;

/**
 * This class prints all itemized sales for an admin user.
 *
 */
public class ViewBooks {
  /**
   * Prints all Itemized sales for Admin.
   * 
   * @throws SQLException
   */
  public static void PrintPurchases(DatabaseDriverAndroid mydb) throws SQLException {
    SalesLog allItemSales = (SalesLog) mydb.getItemizedSales();

    ArrayList<Sale> allSales = allItemSales.getallSales();

    BigDecimal totalRevenue = new BigDecimal("0.00");

    // Create hashmap that will store total number of each item sold
    HashMap<String, Integer> itemNameToTotal = new HashMap<String, Integer>();
    for (ItemTypes item : ItemTypes.values()) {
      itemNameToTotal.put(item.toString(), 0);
    }

    for (int i = 0; i < allSales.size(); i++) {
      // Get both the items version and non-item version of the sale
      Sale itemizedSale = allSales.get(i);

      // Print out requirements
      System.out.println("Customer: " + itemizedSale.getUser().getName());
      System.out.println("Purchase Number: " + itemizedSale.getId());
      System.out.println("Total Purchase Price: " + itemizedSale.getTotalPrice());
      // Update total revenue
      totalRevenue = totalRevenue.add(itemizedSale.getTotalPrice());

      // Get the item map of the sale
      HashMap<Item, Integer> ItemToQuantity = itemizedSale.getItemMap();

      Iterator<Entry<Item, Integer>> ItemMapsToQuantity = ItemToQuantity.entrySet().iterator();
      // Get the first item in sale and print it out
      Entry<Item, Integer> entry = ItemMapsToQuantity.next();
      System.out
          .println("Itemized Breakdown: " + entry.getKey().getName() + ": " + entry.getValue());
      // Update hashmap that stores total number of item sold
      itemNameToTotal.put(entry.getKey().getName(),
          itemNameToTotal.get(entry.getKey().getName()) + entry.getValue());
      // Print out the rest of the items
      while (ItemMapsToQuantity.hasNext()) {
        Entry<Item, Integer> mapElement = ItemMapsToQuantity.next();
        System.out.println("                    " + mapElement.getKey().getName() + ": " + mapElement.getValue());
        // Update hashmap that stores total number of item sold
        itemNameToTotal.put(mapElement.getKey().getName(),
            itemNameToTotal.get(mapElement.getKey().getName()) + mapElement.getValue());
      }
      System.out.println("----------------------------------------------------");
    }

    // Loop through every item in store and print how much of it was sold
    for (Entry<String, Integer> currentItem : itemNameToTotal.entrySet()) {
      System.out.println("Number " + currentItem.getKey() + " Sold: " + currentItem.getValue());
    }
    System.out.println("TOTAL SALES: " + totalRevenue);
  }
}
