package com.b07.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.NotEnoughResourcesException;
import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.CurrentInventory;
import com.b07.inventory.EmployeeInterface;
import com.b07.inventory.Item;
import com.b07.inventory.ShoppingCart;
import com.b07.security.PasswordHelpers;
import com.b07.store.frontend.GeneralLogIn;
import com.b07.store.frontend.UserInput;
import com.b07.users.Employee;
import com.b07.users.User;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

public class EmployeeFunctions {

  static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
  static HashMap<Item, Integer> ItemMap;
  static CurrentInventory inventory = new CurrentInventory(ItemMap);

  /**
   * Sets up the current Customer using the program with the userId passed from the login screen.
   * 
   * @param userId
   * @throws SQLException
   * @throws UnauthorizedException
   * @throws NumberFormatException
   * @throws IOException
   * @throws DatabaseInsertException
   * @throws NotEnoughResourcesException
   */
  public static void setCurrentEmployee(int userId, DatabaseDriverAndroid mydb) throws SQLException, UnauthorizedException,
      NumberFormatException, IOException, DatabaseInsertException, NotEnoughResourcesException {
    User temp = (User) mydb.getUserDetails(userId);
    Employee currentEmployee =
        new Employee(userId, temp.getName(), temp.getAge(), temp.getAddress());
    EmployeeMenu.employeeMenuChoices(mydb);

  }

  /**
   * Authenticate new employee.
   * 
   * @throws IOException
   * @throws NumberFormatException
   * @throws SQLException
   * @throws NotEnoughResourcesException
   * @throws UnauthorizedException
   * @throws DatabaseInsertException
   */
  public static void authenticateNewEmployee(DatabaseDriverAndroid mydb) throws NumberFormatException, IOException,
      SQLException, DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException {
    boolean authenticated = employeeVerification(mydb);
    if (authenticated) {
      System.out.println("Employee authenticated");
    } else {
      System.out.println("Failed to authenticate employee");
    }
  }

  /**
   * Restock an item in the inventory.
   * 
   * @throws SQLException
   * @throws IOException
   * @throws NumberFormatException
   */
  public static void restock(EmployeeInterface employeeLogin, DatabaseDriverAndroid mydb)
      throws SQLException, NumberFormatException, IOException {
    System.out.println("Enter name of item to restock:");
    String item = UserInput.getUserInputRestockedItemName();
    int itemId = FindWantedData.itemId(item, mydb);
    System.out.println("Enter quantity of item to add:");
    int quantity = UserInput.getUserInputRestockedItemQuantity();
    boolean restock =
        employeeLogin.restockInventory((Item) mydb.getItem(itemId), quantity, mydb);
    if (restock) {
      System.out.println("Item restocked.");
    } else {
      System.out.println("Item not restocked.");
    }
  }

  /**
   * Checks to see if valid Employee Information was entered
   * 
   * @return
   * @throws NumberFormatException
   * @throws IOException
   * @throws SQLException
   * @throws DatabaseInsertException
   * @throws UnauthorizedException
   * @throws NotEnoughResourcesException
   */
  public static boolean employeeVerification(DatabaseDriverAndroid mydb) throws NumberFormatException, IOException,
      SQLException, DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException {
    int employeeId = mydb.getUserRole(GeneralLogIn.checkUserIdAndPassword(mydb));
    int roleId = FindWantedData.findRoleId("EMPLOYEE", mydb);
    return employeeId == roleId;
  }

  /**
   * Takes the user's Id and Cart and gives them an according account Id associated with that id.
   * Can give multiple account Ids to one User
   * 
   * @param currentCart The ShoppingCart that will be restored with given account Id
   * @param userId The User to be associated with the given Id
   * @return the account Id
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static int giveAccount(ShoppingCart currentCart, int userId, DatabaseDriverAndroid mydb)
      throws SQLException, DatabaseInsertException {

    int accountId = Math.toIntExact(mydb.insertAccount(userId, true));
    if (accountId != 0) {
      HashMap<Item, Integer> items = currentCart.getItemMap();
      for (HashMap.Entry<Item, Integer> entry : items.entrySet()) {
        int quantity = entry.getValue();
        Item item = entry.getKey();
        accountId = Math.toIntExact(mydb.insertAccountLine(accountId, item.getId(), quantity));
      }

    }
    return accountId;
  }
}
