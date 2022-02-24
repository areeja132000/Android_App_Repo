package com.mockAmazonApp.store;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import com.mockAmazonApp.exceptions.DatabaseInsertException;
import com.mockAmazonApp.exceptions.NotEnoughResourcesException;
import com.mockAmazonApp.exceptions.UnauthorizedException;
import com.mockAmazonApp.inventory.CurrentInventory;
import com.mockAmazonApp.inventory.EmployeeInterface;
import com.mockAmazonApp.inventory.Item;
import com.mockAmazonApp.store.frontend.UserInput;
import com.example.SalesApp.DatabaseDriverAndroid;

public class EmployeeMenu {

  static HashMap<Item, Integer> ItemMap;
  static CurrentInventory inventory = new CurrentInventory(ItemMap);
  private static int maxChoices = 4;
  static String menuText = "Employee Options:\n\n" + "1. Authenticate new employee\n"
      + "2. Make new Customer\n" + "3. Make new Employee\n" + "4. Restock Inventory\n" + "0. Exit";


  /**
   * Connects the User's menu choice to the correct function in the backend
   * 
   * @throws IOException
   * @throws SQLException
   * @throws NumberFormatException
   * @throws DatabaseInsertException
   * @throws NotEnoughResourcesException
   * @throws UnauthorizedException
   */
  public static void employeeMenuChoices(DatabaseDriverAndroid mydb) throws IOException, SQLException, NumberFormatException,
      DatabaseInsertException, UnauthorizedException, NotEnoughResourcesException {

    EmployeeInterface employeeInterface = new EmployeeInterface(inventory);
    String choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
    while (!choice.contentEquals("0")) {
      if (choice.equals("1")) {
        EmployeeFunctions.authenticateNewEmployee(mydb);
      } else if (choice.equals("2")) {
        CreateUser.insertUser("CUSTOMER", mydb);
      } else if (choice.equals("3")) {
        CreateUser.insertUser("EMPLOYEE", mydb);
      } else if (choice.equals("4")) {
        EmployeeFunctions.restock(employeeInterface, mydb);
      }
      if (!choice.contentEquals("0")) {
        choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
      }
    }
  }
}
