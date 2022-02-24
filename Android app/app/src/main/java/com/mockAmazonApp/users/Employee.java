package com.mockAmazonApp.users;

import com.mockAmazonApp.exceptions.UnauthorizedException;

import java.io.Serializable;

public class Employee extends User implements Serializable {

  /**
   * Sets the values id, name, age and address for Employee.
   *
   * @param id name age address
   */
  public Employee(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.setAddress(address);
  }

  /**
   * Sets the values id, name, age and address for Employee if authenticated is true.
   *
   * @param id name age address authenticated
   * @throws UnauthorizedException
   */
  public Employee(int id, String name, int age, String address, boolean authenticated)
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

}