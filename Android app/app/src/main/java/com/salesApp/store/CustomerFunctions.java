package com.salesApp.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.salesApp.exceptions.DatabaseInsertException;
import com.salesApp.exceptions.NotEnoughResourcesException;
import com.salesApp.exceptions.UnauthorizedException;
import com.salesApp.inventory.Item;
import com.salesApp.inventory.ShoppingCart;
import com.salesApp.store.frontend.UserInput;
import com.salesApp.users.Customer;
import com.salesApp.users.User;
import com.example.pages.DatabaseDriverAndroid;

public class CustomerFunctions {
  
  static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  String input = "";
  
  /**
   * Checks to see if the current user has any associated accounts
   * 
   * @param userId
   * @return returns true if the user has any associated accounts
   * @throws SQLException
   */
  public static boolean checkForActiveAccounts(int userId, DatabaseDriverAndroid mydb) throws SQLException {
//    if (mydb.getUserActiveAccounts(userId).isEmpty()) { NEED TO FIX THIS ASK JOSEPH HE MADE METHOD
//      return false;
//    }
    return true;
  }

  /**
   * Restores the ShoppingCart associated with accountId. Adjusts for inventory
   * values and empty accounts
   * 
   * @param accountId
   * @param customer
   * @return restoredCart The adjusted state of the previous shopping cart
   * @throws SQLException
   * @throws UnauthorizedException
   * @throws NotEnoughResourcesException
   * @throws NumberFormatException
   * @throws IOException
   */
  public static ShoppingCart restoreCart(int accountId, Customer customer, DatabaseDriverAndroid mydb) throws SQLException,
      UnauthorizedException, NotEnoughResourcesException, NumberFormatException, IOException {

    ShoppingCart restoredCart = new ShoppingCart(customer);

    HashMap<Integer, Integer> savedCart = (HashMap<Integer, Integer>) mydb.getAccountDetails(accountId);
    if (savedCart.isEmpty()) {
      System.out.println("Your previous cart was empty");

    } else {
      boolean changedCart = false;
      for (Map.Entry mapElement : savedCart.entrySet()) {
        int itemId = (int) mapElement.getKey();
        int quantity = (int) mapElement.getValue();
        if (mydb.getInventoryQuantity(itemId) >= quantity) {
          restoredCart.addItem((Item) mydb.getItem(itemId), quantity);
        } else {
          changedCart = true;
          int adjustedQuantity = mydb.getInventoryQuantity(itemId);
          if (adjustedQuantity != 0) {
            restoredCart.addItem((Item) mydb.getItem(itemId), adjustedQuantity);
          }
        }
      }
      System.out.println("Your previous cart has been restored");
      if (changedCart) {
        System.out.println(" *Your cart was changed due to changes in store stock");
      }
      restoredCart.displayCart();
    }
    return restoredCart;
  }

  /**
   * Sets up the current Customer using the program with the userId passed from
   * the login screen. If they choose to, they attempt to get a restored cart.
   * Otherwise they are given an empty one
   * 
   * @param userId
   * @throws SQLException
   * @throws UnauthorizedException
   * @throws NumberFormatException
   * @throws IOException
   * @throws DatabaseInsertException
   * @throws NotEnoughResourcesException
   */
  public static void setCurrentCustomer(int userId, DatabaseDriverAndroid mydb) throws SQLException, UnauthorizedException,
      NumberFormatException, IOException, DatabaseInsertException, NotEnoughResourcesException {
    User temp = (User) mydb.getUserDetails(userId);
    Customer customer = new Customer(userId, temp.getName(), temp.getAge(), temp.getAddress());

    if (checkForActiveAccounts(userId, mydb)) {
      String input = "";
      while (!input.contentEquals("1") && !input.contentEquals("0")) {
        String menuText = "Would you like to restore cart contents?\n 1. Restore\n 0. Cancel";
        input = UserInput.getInputGenericMenuChoice(menuText, 1);
        if (input.contentEquals("1")) {
          int accountId = UserInput.getAccountIdFromUser(userId, mydb);
          if (accountId == 0) {
            ShoppingCart newCart = new ShoppingCart(customer);
            CustomerMenu.customerMenuChoices(customer, accountId, newCart, mydb);
          } else {
            ShoppingCart restoredCart = restoreCart(accountId, customer, mydb);
            CustomerMenu.customerMenuChoices(customer, accountId, restoredCart, mydb);
          }

        } else if (input.contentEquals("0")) {
          ShoppingCart newCart = new ShoppingCart(customer);
          CustomerMenu.customerMenuChoices(customer, 0, newCart, mydb);
        } 
      }
    } else {
      ShoppingCart newCart = new ShoppingCart(customer);
      CustomerMenu.customerMenuChoices(customer, 0, newCart, mydb);
    }
  }
  
  /**
   * Adds a quantity of an item to the cart.
   * 
   * @param currentCart
   * @throws SQLException
   * @throws IOException
   * @throws NumberFormatException
   * @throws NotEnoughResourcesException
   */
  public static void addQuantityOfItem(ShoppingCart currentCart, DatabaseDriverAndroid mydb)
      throws SQLException, NumberFormatException, IOException, NotEnoughResourcesException {

    boolean noItem = false;
    System.out.println("Enter name of Item:");
    String item = UserInput.getUserInputRestockedItemName();
    if (!(FindWantedData.itemIsSold(item))) {
      System.out.println("That item is not sold at this store.");
      noItem = true;
    }

    if (!noItem) {
      System.out.println("Enter quantity you would like to add:");
      int itemId = FindWantedData.itemId(item, mydb);
      int quantity = UserInput.getUserInputRestockedItemQuantity();
      if (mydb.getInventoryQuantity(itemId) >= quantity) {
        currentCart.addItem((Item) mydb.getItem(itemId), quantity);
      } else {
        System.out.println("Not enough items in stock to sell.");
      }
    }
  }

  /**
   * Check total price of items in the cart.
   * 
   * @param currentCart
   * @throws SQLException
   * @throws IOException
   * @throws NumberFormatException
   */
  public static void checkTotal(ShoppingCart currentCart)
      throws SQLException, NumberFormatException, IOException {
    BigDecimal total = currentCart.getTotal();
    System.out.println(total);
  }

  /**
   * Removes a quantity of an item from the cart.
   * 
   * @param currentCart
   * @throws SQLException
   * @throws IOException
   * @throws NumberFormatException
   */
  public static void removeQuantityFromItem(ShoppingCart currentCart, DatabaseDriverAndroid mydb)
      throws SQLException, NumberFormatException, IOException {

    boolean noItem = false;
    System.out.println("Enter name of Item:");
    String item = UserInput.getUserInputRestockedItemName();

    if (!(FindWantedData.itemIsSold(item))) {
      System.out.println("That item is not sold at this store.");
      noItem = true;
    }

    if (!noItem) {
      System.out.println("Enter quantity you would like to remove:");
      int quantity = UserInput.getUserInputRestockedItemQuantity();
      
      try {
        currentCart.removeItem((Item) mydb.getItem(FindWantedData.itemId(item, mydb)), quantity);
      } catch (NotEnoughResourcesException e) {
        System.out.println(
            "You do not have that many of that item in your cart.\n" + "None will be removed.");
        currentCart.displayCart();
      }
    }
  }

  /**
   * Gives a user an accountId according to their current Cart. It is printed.
   * 
   * @param currentCart The cart to be saved
   * @param userId      The user associated with the cart
   * @throws NumberFormatException
   * @throws IOException
   * @throws SQLException
   * @throws DatabaseInsertException
   * @throws UnauthorizedException
   * @throws NotEnoughResourcesException
   */
  public static int requestAccount(ShoppingCart currentCart, int userId, int accountId, DatabaseDriverAndroid mydb)
      throws NumberFormatException, IOException, SQLException, DatabaseInsertException,
      UnauthorizedException, NotEnoughResourcesException {

    System.out.println("Please Ask an Employee for Assistance\n");
    Boolean employeeFound = EmployeeFunctions.employeeVerification(mydb);

    if (employeeFound) {
      int newAccountId = EmployeeFunctions.giveAccount(currentCart, userId, mydb);
      if (newAccountId == 0) {
        System.out.println("Account setup failed, please try again.");
        return accountId;
      } else {
        System.out.println("Your Account id is: " + newAccountId);
        return newAccountId;
      }
    } else {
      System.out.println("Incorrect Login info: Exiting to Menu");
      return accountId;
    }
  }

  /**
   * Sets an account to inactive after checkout has taken place
   * 
   * @param userId    The current user
   * @param accountId The account that will be set inactive
   * @return The new working accountId, unchanged if update failed
   * @throws SQLException
   */
  public static int setAccountInactive(int userId, int accountId, DatabaseDriverAndroid mydb) throws SQLException {
    boolean stateUpdated = mydb.updateAccountStatus(accountId, false);
    if (stateUpdated) {
      return 0;
    }
    if (accountId != 0) {
      System.out.println("Failed to set account Inactive");
    }
    return accountId;
  }
}
