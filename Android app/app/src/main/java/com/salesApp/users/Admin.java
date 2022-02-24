package com.salesApp.users;

import com.salesApp.exceptions.UnauthorizedException;

import java.io.Serializable;

public class Admin extends User implements Serializable {

  /**
   * Sets the values id, name, age and address for Admin.
   *
   * @param id name age address
   */
  public Admin(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.setAddress(address);
  }

  /**
   * Sets the values id, name, age and address for Admin if authenticated is true.
   *
   * @param id name age address authenticated
   * @throws UnauthorizedException
   */
  public Admin(int id, String name, int age, String address, boolean authenticated)
          throws UnauthorizedException {
    if (this.getAuthenticated()) {
      this.setId(id);
      this.setName(name);
      this.setAge(age);
      this.setAddress(address);
    } else {
      throw new UnauthorizedException();
    }

  }

  /**
   * Promotes employee to administrator.
   *
   * @param employee
   * @return if the employee has the role id of an administrator
   */
  public boolean promoteEmployee(Employee employee) {
    return (employee.getRoleId() == this.getRoleId());
  }
}
