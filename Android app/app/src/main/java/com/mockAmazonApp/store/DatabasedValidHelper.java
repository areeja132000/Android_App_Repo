package com.mockAmazonApp.store;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.users.ItemTypes;
import com.mockAmazonApp.users.Roles;
import com.mockAmazonApp.users.User;
import com.example.SalesApp.DatabaseDriverAndroid;

public class DatabasedValidHelper {

  /**
   * Returns true if user information is valid.
   * 
   * @param name age address
   * @return true if parameters are correct
   */
  public static boolean checkUser(String name, int age, String address) {
    boolean validName = (name != null);
    boolean validAge = (age > 0);
    boolean validAddress = (address != null && (address.length() > 0 && address.length() <= 100));
    return (validName && validAge && validAddress);
  }

  /**
   * Returns true if role is valid.
   * 
   * @param name
   * @return true if parameters are correct
   */
  public static boolean checkRoleName(String name) {
    for (Roles i : Roles.values()) {
      if (i.name().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if item name is valid.
   * 
   * @param name age address
   * @return true if parameters are correct
   */
  public static boolean checkItemName(String name) {
    boolean isInEnumerator = false;
    for (ItemTypes item : ItemTypes.values()) {
      if (item.name().equals(name)) {
        isInEnumerator = true;
      }
    }
    return (name != null && (name.length() >= 1 && name.length() < 64) && isInEnumerator);
  }

  /**
   * Returns true if item price is valid.
   * 
   * @param price
   * @return true if parameter is correct
   */
  public static boolean checkItemPrice(BigDecimal price, DatabaseDriverAndroid mydb) {
    String plainTextPrice = price.toPlainString();
    int decimalPlace = plainTextPrice.length() - plainTextPrice.indexOf(".") - 1;
    boolean validDecimal = (decimalPlace == 2);
    return validDecimal;
  }

  /**
   * Returns true if inventory is valid.
   * 
   * @param itemId quantity
   * @return true if parameters are correct
   */
  public static boolean checkInventory(int itemId, int quantity, DatabaseDriverAndroid mydb) throws SQLException {
    boolean validItemId = checkItemId(itemId, mydb);
    return (validItemId && (quantity >= 0));
  }

  /**
   * Returns true if userId is valid.
   * 
   * @param userId quantity
   * @return true if parameter is correct
   */
  public static boolean checkUserId(int userId, DatabaseDriverAndroid mydb) throws SQLException {
    List<User> validUsers = (List<User>) mydb.getUsersDetails();
    for (User validUser : validUsers) {
      if (userId == validUser.getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check for value id from the ITEMS table.
   * 
   * @param itemId quantity
   * @return true if parameter is correct
   */
  public static boolean checkItemId(int itemId, DatabaseDriverAndroid mydb) throws SQLException {
    List<Item> validItems = (List<Item>) mydb.getAllItems();
    for (Item validItem : validItems) {
      if (itemId == validItem.getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks to make sure the given accountId is associated with the given userId and is active
   * 
   * @param accountId
   * @param userId
   * @return
   * @throws SQLException
   */
  public static boolean checkAccountId(int accountId, int userId, DatabaseDriverAndroid mydb) throws SQLException {
    List<Integer> validIds = (List<Integer>) mydb.getUserAccounts(userId);
    for (int validId : validIds) {
      if (accountId == validId) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Checks to make sure the given accountId is associated with the given userId
   * 
   * @param accountId
   * @param userId
   * @return
   * @throws SQLException
   */
  public static boolean checkActiveAccountId(int accountId, int userId, DatabaseDriverAndroid mydb) throws SQLException {
    List<Integer> validIds = (List<Integer>) mydb.getUserActiveAccounts(userId);
    for (int validId : validIds) {
      if (accountId == validId) {
        return true;
      }
    }
    return false;
  }


}
