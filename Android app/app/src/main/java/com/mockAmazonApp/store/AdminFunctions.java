package com.mockAmazonApp.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.mockAmazonApp.exceptions.ConnectionFailedException;
import com.mockAmazonApp.exceptions.DatabaseInsertException;
import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.exceptions.UnauthorizedException;
import com.mockAmazonApp.inventory.CurrentInventory;
import com.mockAmazonApp.inventory.Item;

import com.mockAmazonApp.store.frontend.UserInput;
import com.mockAmazonApp.users.Admin;
import com.mockAmazonApp.users.Employee;
import com.mockAmazonApp.users.ItemTypes;
import com.mockAmazonApp.users.User;
import com.example.SalesApp.DatabaseDriverAndroid;

public class AdminFunctions {
  
  static HashMap<Item, Integer> ItemMap;
  static CurrentInventory inventory = new CurrentInventory(ItemMap);
  Connection connection = null;

  static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  String input = "";
  
  /**
   * Sets up the Admin currently using the interface
   * 
   * @throws DatabaseInsertException
   * @throws NumberFormatException
   * @throws NotEnoughResourcesException 
   * @throws UnauthorizedException 
   * @throws ConnectionFailedException 
   */
  public static DatabaseDriverAndroid setCurrentAdmin(int adminId, DatabaseDriverAndroid mydb)
      throws IOException, SQLException, NumberFormatException, DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException, ConnectionFailedException {
 
    User currentUser = (User) mydb.getUserDetails(adminId);
    Admin currentAdmin = new Admin(adminId, currentUser.getName(), currentUser.getAge(), currentUser.getAddress());
    AdminMenu.adminMenuChoices(currentAdmin, mydb.getUserRole(adminId), mydb);
    return mydb;
  }
  
  public static void getActiveAccounts(DatabaseDriverAndroid mydb) throws SQLException, NumberFormatException, IOException {
    System.out.println("Please enter a User Id:");
    int userId = UserInput.getInputUserId(mydb);
    List<Integer> activeAccounts = (List<Integer>) mydb.getUserActiveAccounts(userId);
    if (activeAccounts.isEmpty()) {
      System.out.println("There are no active accounts associated with the given user");
    } else {
      System.out.println("These are the active account Ids associated with user id: " + userId);
      for (int account : activeAccounts) {
        System.out.println(account);
      }
    }
  }

  public static void getInactiveAccounts(DatabaseDriverAndroid mydb) throws SQLException, NumberFormatException, IOException {
    System.out.println("Please enter a User Id:");
    int userId = UserInput.getInputUserId(mydb);
    List<Integer> activeAccounts = (List<Integer>) mydb.getUserInactiveAccounts(userId);
    if (activeAccounts.isEmpty()) {
      System.out.println("There are no inactive accounts associated with the given user");
    } else {
      System.out.println("These are the inactive account Ids associated with user id: " + userId);
      for (int account : activeAccounts) {
        System.out.println(account);
      }
    }
  }
  
  public static void promoteEmployee(Admin currentAdmin, DatabaseDriverAndroid mydb) throws SQLException, NumberFormatException, IOException {
    System.out.println("What is the Id of the employee you would like to promote?");
    int employId = UserInput.getInputUserId(mydb);
    int userRoleId = mydb.getUserRole(employId);
    String userRoleName = mydb.getRole(userRoleId);
    boolean promoted = false;
    if (userRoleName.equalsIgnoreCase("EMPLOYEE")) {
      User usertoPromote = (User) mydb.getUserDetails(employId);
      Employee employeeToPromote = new Employee(employId, usertoPromote.getName(),
          usertoPromote.getAge(), usertoPromote.getAddress());
      mydb.updateUserRole(currentAdmin.getRoleId(), employId);
      promoted = currentAdmin.promoteEmployee(employeeToPromote);
    } else {
      System.out.println("Invalid employee ID");
    }
    if (promoted) {
      System.out.println("Employee promoted.");
    } else {
      System.out.println("Promotion Failed.");
    }
  }
  
  /**
   * Prints all Itemized sales for Admin.
   * 
   * @throws SQLException
   */
  public static void viewBooks(DatabaseDriverAndroid mydb) throws SQLException {
    
    SalesLog allItemSales = (SalesLog) mydb.getItemizedSales();
    ArrayList<Sale> allSales = allItemSales.getallSales();
    BigDecimal totalRevenue = new BigDecimal("0.00");

    HashMap<String, Integer> itemNameToTotal = new HashMap<String, Integer>();
    for (ItemTypes item : ItemTypes.values()) {
      itemNameToTotal.put(item.toString(), 0);
    }

    for (int i = 0; i < allSales.size(); i++) { 
      Sale itemizedSale = allSales.get(i);
      totalRevenue = totalRevenue.add(itemizedSale.getTotalPrice());
      HashMap<Item, Integer> ItemToQuantity = itemizedSale.getItemMap();
      Iterator<Entry<Item, Integer>> ItemMapsToQuantity = ItemToQuantity.entrySet().iterator();
      Entry<Item, Integer> entry = ItemMapsToQuantity.next();
      System.out.println("Customer: " + itemizedSale.getUser().getName());
      System.out.println("Purchase Number: " + itemizedSale.getId());
      System.out.println("Total Purchase Price: " + itemizedSale.getTotalPrice());

      
      System.out
          .println("Itemized Breakdown: " + entry.getKey().getName() + ": " + entry.getValue());
      itemNameToTotal.put(entry.getKey().getName(),
          itemNameToTotal.get(entry.getKey().getName()) + entry.getValue());
      while (ItemMapsToQuantity.hasNext()) {
        Entry<Item, Integer> mapElement = ItemMapsToQuantity.next();
        System.out.println("                    " + mapElement.getKey().getName() + ": " + mapElement.getValue());
        itemNameToTotal.put(mapElement.getKey().getName(),
            itemNameToTotal.get(mapElement.getKey().getName()) + mapElement.getValue());
      }
      System.out.println("----------------------------------------------------");
    }
    for (Entry<String, Integer> currentItem : itemNameToTotal.entrySet()) {
      System.out.println("Number " + currentItem.getKey() + " Sold: " + currentItem.getValue());
    }
    System.out.println("TOTAL SALES: " + totalRevenue);

  }
  
/*  public static void Serialize() {
    Serializable database = new Serializable();
    Serializer.serialize(database);
  }

  public static Connection DeSerialize(Connection connection) throws SQLException, ConnectionFailedException, DatabaseInsertException, IOException {
    System.out.println("Please enter file path to ser folder");
    String filePath = bufferedReader.readLine();
    connection.close();
    boolean cleared = DatabaseDriver.clearDatabase();
    if (cleared) {
      connection = DatabaseDriverExtender.connectOrCreateDataBase();
      DatabaseDriverExtender.initialize(connection);
      Serializable database1 = (Serializable) Serializer.deserialize(filePath);
      AfterDeserialization.insertBackSerialization(database1);
    }
    return connection;
  }*/

}

