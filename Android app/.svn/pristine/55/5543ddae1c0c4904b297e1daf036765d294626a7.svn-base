package com.b07.inventory;

import java.sql.SQLException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

/**
 * Tasks that employees carry out and need to be carried out for employee.
 *
 */
public class EmployeeInterface {
  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Creates a new employee interface for employee use. If password is authenticated.
   * 
   * @param employee inventory.
   */
  public EmployeeInterface(Employee employee, Inventory inventory, DatabaseDriverAndroid mydb) throws SQLException {
    if (employee != null && inventory != null) {
      String password = mydb.getPassword(employee.getId());
      boolean authenicated = employee.authenticate(password, mydb);
      if (authenicated) {
        this.inventory = inventory;
      }
    }
  }

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
    if (employee != null) {
      String password = mydb.getPassword(employee.getId());
      boolean authenicated = employee.authenticate(password, mydb);
      if (authenicated) {
        currentEmployee = employee;
      }
    }
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
    try {
      mydb.getInventoryQuantity(item.getId());
    } catch (Exception SQLException) {
      System.out.println("This item does not exist in this store.");
      return false;
    }
    int newQuantity = mydb.getInventoryQuantity(item.getId()) + quantity;
    boolean complete = mydb.updateInventoryQuantity(newQuantity, itemId);
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
    int customerId = (int) mydb.insertNewUser(name, age, address, password);
    new Customer(customerId, name, age, address);
    return customerId;
  }

  /**
   * Creates a new employee and returns their id.
   * 
   * @param name age address password
   * @return employeeId
   */
  public int createEmployee(String name, int age, String address, String password, DatabaseDriverAndroid mydb)
      throws DatabaseInsertException, SQLException {
    int employeeId = (int) mydb.insertNewUser(name, age, address, password);
    new Customer(employeeId, name, age, address);
    return employeeId;
  }
}
