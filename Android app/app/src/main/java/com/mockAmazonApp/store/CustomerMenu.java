package com.mockAmazonApp.store;

import java.io.IOException;
import java.sql.SQLException;
import com.mockAmazonApp.exceptions.DatabaseInsertException;
import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.exceptions.UnauthorizedException;
import com.mockAmazonApp.inventory.ShoppingCart;
import com.mockAmazonApp.store.frontend.UserInput;
import com.mockAmazonApp.users.Customer;
import com.example.SalesApp.DatabaseDriverAndroid;

public class CustomerMenu {
  
  private static int maxChoices = 6;
  
  private static String menuText = "Customer Options:\n\n" + "1. List current items in cart\n"
      + "2. Add a quantity of an item to the cart\n"
      + "3. Check total price of items in the cart\n"
      + "4. Remove a quantity of an item from the cart\n" + "5. Check out\n"
      + "6. Request Account\n" + "0. Exit";
  
  /**
   * Shows shopping cart menu.
   */
  public static void customerMenuChoices(Customer customer, int accountId, ShoppingCart currentCart, DatabaseDriverAndroid mydb)
      throws IOException, SQLException, NumberFormatException, DatabaseInsertException,
      UnauthorizedException, NotEnoughResourcesException {

    String choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
    while (!choice.equals("0")) {
      if (choice.equals("1")) {
        currentCart.displayCart();
      } else if (choice.equals("2")) {
        CustomerFunctions.addQuantityOfItem(currentCart, mydb);
      } else if (choice.equals("3")) {
        CustomerFunctions.checkTotal(currentCart);
      } else if (choice.equals("4")) {
        CustomerFunctions.removeQuantityFromItem(currentCart, mydb);
      } else if (choice.equals("5")) {
        currentCart.displayCart();
        boolean checkOut = currentCart.checkOut(mydb);
        if (checkOut) {
          System.out.println("You have checked out");
          accountId = CustomerFunctions.setAccountInactive(customer.getId(), accountId, mydb);
        } else {
          System.out.println("Checked out failed.");
        }
      } else if (choice.equals("6")) {
        accountId = CustomerFunctions.requestAccount(currentCart, customer.getId(), accountId, mydb);
      } 
      if (!choice.contentEquals("0")) {
        choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
      }
    }
  }
}