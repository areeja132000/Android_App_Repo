package com.b07.store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.NotEnoughResourcesException;
import com.b07.exceptions.UnauthorizedException;
import com.b07.inventory.CurrentInventory;
import com.b07.inventory.EmployeeInterface;
import com.b07.inventory.Item;
import com.b07.store.frontend.UserInput;
import com.b07.users.Admin;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

public class AdminMenu {

  static HashMap<Item, Integer> ItemMap;
  static CurrentInventory inventory = new CurrentInventory(ItemMap);
  static int maxChoices = 10;

  private static String menuText =
      "Admin Options:\n\n1. Authenticate new employee\n2. Make new Customer\n3. Make new Employee\n4. Restock Inventory\n5. Promote Employee\n6."
          + " View Books\n7. List Customer Active Accounts\n8. List Customer Inactive Accounts\n9. Serialize Database\n10. Deserialize Database\n0. Exit\n";

  public static DatabaseDriverAndroid adminMenuChoices(Admin currentAdmin, int adminRoleId, DatabaseDriverAndroid mydb)
      throws IOException, SQLException, NumberFormatException, DatabaseInsertException,
      UnauthorizedException, NotEnoughResourcesException, ConnectionFailedException {
    String choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
    while (!choice.equals("0")) {
      if (choice.contentEquals("1")) {
        EmployeeFunctions.authenticateNewEmployee(mydb);
      } else if (choice.contentEquals("2")) {
        CreateUser.insertUser("CUSTOMER", mydb);
      } else if (choice.contentEquals("3")) {
        CreateUser.insertUser("EMPLOYEE", mydb);
      } else if (choice.contentEquals("4")) {
        EmployeeInterface employeeInterface = new EmployeeInterface(inventory);
        EmployeeFunctions.restock(employeeInterface, mydb);
      } else if (choice.contentEquals("5")) {
        AdminFunctions.promoteEmployee(currentAdmin, mydb);
      } else if (choice.contentEquals("6")) {
        AdminFunctions.viewBooks(mydb);
      }else if (choice.contentEquals("7")) {
        AdminFunctions.getActiveAccounts(mydb);
      } else if (choice.contentEquals("8")) {
        AdminFunctions.getInactiveAccounts(mydb);
      } else if (choice.contentEquals("9")) {
        //AdminFunctions.Serialize();-------NEED TO BE FIXEDDDDD
      } else if (choice.contentEquals("10")) {
        //connection = AdminFunctions.DeSerialize(connection);-------NEED TO BE FIXEDDDDD
      }
      if (!choice.contentEquals("0")) {
        choice = UserInput.getInputGenericMenuChoice(menuText, maxChoices);
      }
    }
    return mydb;
  }

}
