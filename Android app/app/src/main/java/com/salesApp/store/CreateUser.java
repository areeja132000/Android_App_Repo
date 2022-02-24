package com.salesApp.store;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.salesApp.exceptions.DatabaseInsertException;
import com.salesApp.store.frontend.UserInput;
import com.example.pages.DatabaseDriverAndroid;

/**
 * This class is responsible for giving collecting information from user and creating a user.
 *
 */
public class CreateUser {

  /**
   * Prompts user for information and returns a list with that information in it.
   * 
   * @param role
   * @return userGivenDetails
   * @throws IOException
   */
  public static List<String> askInfoUser(String role) throws IOException {
    List<String> userGivenDetails = new ArrayList<>();
    System.out.println("Enter name of " + role + ":");
    String name = UserInput.getInputUserName();
    userGivenDetails.add(name);
    System.out.println("Enter age of " + role + ":");
    String age = UserInput.getInputUserAge();
    userGivenDetails.add(age);
    System.out.println("Enter address of " + role + ":");
    String address = UserInput.getInputUserAddress();
    userGivenDetails.add(address);
    String password = UserInput.getInputUserPassword();
    userGivenDetails.add(password);
    return userGivenDetails;
  }

  /**
   * Inserts user into database and prints user's ID.
   * 
   * @param role
   * @throws IOException
   * @throws NumberFormatException
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static void insertUser(String role, DatabaseDriverAndroid mydb)
      throws IOException, NumberFormatException, DatabaseInsertException, SQLException {
    List<String> userToAdd = askInfoUser(role);
    int userId = (int) mydb.insertNewUser(userToAdd.get(0),
        Integer.parseInt(userToAdd.get(1)), userToAdd.get(2), userToAdd.get(3));
    mydb.insertUserRole(userId, FindWantedData.findRoleId(role, mydb));
    System.out.println("Created User with Id: " + userId);
  }

}
