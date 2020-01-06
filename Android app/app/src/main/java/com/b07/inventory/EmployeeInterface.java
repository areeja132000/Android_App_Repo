package com.b07.inventory;

import android.database.Cursor;

import java.io.Serializable;
import java.sql.SQLException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

/**
 * Tasks that employees carry out and need to be carried out for employee.
 *
 */
public class EmployeeInterface implements Serializable {
  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Creates a new employee interface for employee use.
   * 
   * @param inventory
   */
  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
  }

  /**
   * Sets the current employee using the employee interface.
   * 
   * @param employee
   */
  public void setCurrentEmployee(Employee employee, DatabaseDriverAndroid mydb) throws SQLException {
    this.currentEmployee = employee;
  }

  /**
   * Checks if there is a current employee using the employee interface.
   */
  public boolean hasCurrentEmployee() {
    return (currentEmployee != null);
  }

  /**
   * Stocks an item in the inventory with the quantity given.
   * 
   * @param item quantity
   * @return if the item was restocked
   */
  public boolean restockInventory(Item item, int quantity, DatabaseDriverAndroid mydb) throws SQLException {
    int itemId = item.getId();
    int oldQuantity;
    boolean complete = false;
    try {
      oldQuantity = mydb.getInventoryQuantity(item.getId());
      complete = mydb.updateInventoryQuantity((oldQuantity + quantity), itemId);
    } catch (Exception SQLException) {
      mydb.insertInventory(item.getId(), quantity);
      return true;
    }
    return complete;
  }

  /**
   * Creates a new customer and returns their id.
   * 
   * @param name age address password
   * @return customerId
   */
  public int createCustomer(String name, int age, String address, String password, DatabaseDriverAndroid mydb)
      throws DatabaseInsertException, SQLException {
    int customerId = Math.toIntExact(mydb.insertNewUser(name, age, address, password));
    Cursor roles = mydb.getRoles();
    roles.moveToFirst();
    int roleId =0;
    while (!roles.isAfterLast()) {
      if (roles.getString(1).equals("CUSTOMER")) {
        roleId = roles.getInt(0);
      }
      roles.moveToNext();
    }
    roles.close();
    if (roleId != 0) {
      mydb.insertUserRole(customerId, roleId);
      return customerId;
    }
    return -1;
  }

  /**
   * Creates a new employee and returns their id.
   * 
   * @param name age address password
   * @return employeeId
   */
  public int createEmployee(String name, int age, String address, String password, DatabaseDriverAndroid mydb)
      throws DatabaseInsertException, SQLException {
    int employeeId = Math.toIntExact(mydb.insertNewUser(name, age, address, password));
    Cursor roles = mydb.getRoles();
    roles.moveToFirst();
    int roleId =0;
    while (!roles.isAfterLast()) {
      if (roles.getString(1).equals("EMPLOYEE")) {
        roleId = roles.getInt(0);
      }
      roles.moveToNext();
    }
    roles.close();
    if (roleId != 0) {
      mydb.insertUserRole(employeeId, roleId);
      return employeeId;
    }
    return -1;
  }
}
