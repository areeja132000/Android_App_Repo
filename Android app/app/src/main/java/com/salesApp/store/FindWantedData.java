package com.salesApp.store;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import com.salesApp.inventory.Item;
import com.salesApp.users.ItemTypes;
import com.salesApp.users.User;
import com.example.pages.DatabaseDriverAndroid;

/**
 * Helps with finding wanted data from database.
 * 
 */
public class FindWantedData {

  /**
   * Find userId with information.
   * 
   * @param name age address
   * @return userId
   */
  public static int findUser(String name, int age, String address, DatabaseDriverAndroid mydb) throws SQLException {
    List<User> users = (List<User>) mydb.getUsersDetails();
    for (User user : users) {
      if (user.getName() == name && user.getAge() == age && user.getAddress() == address) {
        return user.getId();
      }
    }
    return -1;
  }

  /**
   * Return role ID given the name of the role.
   * 
   * @param role
   * @return roleId
   * @throws SQLException
   */
  public static int findRoleId(String role, DatabaseDriverAndroid mydb) throws SQLException {
    List<Integer> roleIds = (List<Integer>) mydb.getRoles();
    for (Integer roleId : roleIds) {
      if (mydb.getRole(roleId).equals(role)) {
        return roleId;
      }
    }
    return -1;
  }

  /**
   * Find item id.
   * 
   * @param name
   * @return itemId
   */
  public static int itemId(String name, DatabaseDriverAndroid mydb) throws SQLException {
    List<Item> items = (List<Item>) mydb.getAllItems();
    for (Item item : items) {
      if (item.getName().equals(name)) {
        return item.getId();
      }
    }
    return -1;
  }

  /**
   * Find quantity of item.
   * 
   * @param item items
   * @return the quantity of the item
   */
  public static int findQuantity(Item item, HashMap<Item, Integer> items) {
    for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
      if (entry.getKey() == item) {
        return entry.getValue();
      }
    }
    return 0;
  }

  /**
   * Return true if item is sold at this store, false otherwise.
   * 
   * @param name
   * @throws SQLException
   */
  public static boolean itemIsSold(String name) throws SQLException {
    for (ItemTypes c : ItemTypes.values()) {
      if (c.name().equals(name)) {
        return true;
      }
    }
    return false;
  }
}
