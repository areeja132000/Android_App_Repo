package com.b07.users;

import android.content.Context;

import java.io.Serializable;
import java.sql.SQLException;
import com.b07.security.PasswordHelpers;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

public abstract class User implements Serializable {

  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  protected boolean authenticated;

  /**
   * Returns the users Id.
   * 
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the users Id.
   * 
   * @param id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the users name.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the users name.
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the users age.
   * 
   * @return age
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the users age.
   * 
   * @param age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Returns the users address.
   * 
   * @return address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the users address.
   * 
   * @param address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Returns the users role Id.
   * 
   * @return roleId
   */
  public int getRoleId() {
    return roleId;
  }

  public boolean getAuthenticated() {
    return authenticated;
  }

  /**
   * Using the password in plain text, validates if it matches the password found in the database
   * for the given user.
   * 
   * @param password
   * @return authenticated
   */
  public final boolean authenticate(String password, DatabaseDriverAndroid mydb) throws SQLException {
    String passwordInDatabase = mydb.getPassword(id);
    authenticated = PasswordHelpers.comparePassword(passwordInDatabase, password);
    return authenticated;
  }

}
