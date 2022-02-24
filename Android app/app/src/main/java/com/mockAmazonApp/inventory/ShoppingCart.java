package com.mockAmazonApp.inventory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mockAmazonApp.exceptions.DatabaseInsertException;
import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.exceptions.UnauthorizedException;
import com.mockAmazonApp.users.Customer;
import com.example.SalesApp.DatabaseDriverAndroid;
import java.io.Serializable;

/**
 * Tasks customers can carry out.
 *
 */
public class ShoppingCart implements Serializable {

  private HashMap<Item, Integer> items;
  private Customer customer;
  private BigDecimal total = new BigDecimal("0.00");
  private static final BigDecimal TAXRATE = new BigDecimal("1.13");

  public ShoppingCart(Customer customer) throws SQLException, UnauthorizedException {
    this.items = new HashMap<Item, Integer>();
    this.customer = customer;
  }

  /**
   * Adds an item to the user cart.
   * 
   * @param item quantity
   */
  public void addItem(Item item, int quantity) throws SQLException, NotEnoughResourcesException {
    boolean exist = false;
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      if (item.getName().equals(entry.getKey().getName())) {
        int value = entry.getValue();
        items.put(entry.getKey(), value + quantity);
        exist = true;
      }
    }
    if (exist == false) {
      items.put(item, quantity);
    }
  }

  /**
   * Remove an item to the user cart.
   * 
   * @param item quantity
   */
  public void removeItem(Item item, int quantity) throws SQLException, NotEnoughResourcesException {
    boolean exist = false;
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      if (item.getName().equals(entry.getKey().getName())) {
        int value = entry.getValue();
        if (value > quantity) {
          items.remove(entry.getKey());
          items.put(entry.getKey(), value - quantity);
        } else if (value == quantity) {
          items.remove(entry.getKey(), entry.getValue());
        } else if (value < quantity) {
          throw new NotEnoughResourcesException();
        }
        exist = true;
      }
    }
    if (exist == false) {
      throw new NotEnoughResourcesException();
    }
  }

  /**
   * Lists items in cart.
   * 
   * @return keyItems
   */
  public List<Item> getItems() {
    List<Item> keyItems = new ArrayList<Item>();
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      keyItems.add(entry.getKey());
    }
    return keyItems;
  }

  /**
   * Gets customer.
   * 
   * @return customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Gets HashMap of items and integers
   * 
   * @return items
   */
  public HashMap<Item, Integer> getItemMap() {
    return items;
  }

  /**
   * Gets total.
   * 
   * @return total
   */
  public BigDecimal getTotal() {
    total = new BigDecimal("0.00");
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      total = total.add((entry.getKey()).getPrice().multiply(new BigDecimal(entry.getValue())));
    }
    return total;
  }

  /**
   * Gets the constant tax rate.
   * 
   * @return TAXRATE
   */
  public BigDecimal getTaxRate() {
    return TAXRATE;
  }

  /**
   * Checks user out by giving them their total with tax and clearing their cart. Updates inventory
   * accordingly.
   * 
   * @return true is checkout is successful
   */
  public boolean checkOut(DatabaseDriverAndroid mydb)
      throws DatabaseInsertException, SQLException, NotEnoughResourcesException {
    BigDecimal afterTax = new BigDecimal("0");
    if (customer != null) {
      getTotal();
      afterTax = total.multiply(TAXRATE);
      afterTax = afterTax.setScale(2, RoundingMode.HALF_UP);
    }
    int value = 0;
    Item key;
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      value = entry.getValue();
      key = entry.getKey();
      if (mydb.getInventoryQuantity(key.getId()) < value) {
        return false;
      }
    }

    int saleId = (int) mydb.insertSale(customer.getId(), afterTax);
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      value = entry.getValue();
      key = entry.getKey();
      mydb.insertItemizedSale(saleId, key.getId(), value);
      mydb.updateInventoryQuantity(
              mydb.getInventoryQuantity(key.getId()) - value, key.getId());
    }

    clearCart();
    return true;
  }

  /**
   * Clears chart.
   */
  public void clearCart() {
    items.clear();
  }

  public String displayCart() throws SQLException, NumberFormatException, IOException {
    int value = 0;
    Item key;
    String Items = "";
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      value = entry.getValue();
      key = entry.getKey();
      Items = Items + (key.getName() + ": " + value + " " + "Price: $" + key.getPrice() +" * "+ value + " = $" + key.getPrice().multiply(new BigDecimal(value)) + "\n");
    }
    return Items;
  }
}
