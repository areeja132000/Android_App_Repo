package com.salesApp.store.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import com.salesApp.exceptions.UnauthorizedException;
import com.salesApp.store.DatabasedValidHelper;
import com.example.pages.DatabaseDriverAndroid;

public class UserInput {


  private static int characterLimit = 100;
  private static int ageLowerLimit = 15;
  private static int ageUpperLimit = 120;
  private static int restockQuantityLimit = 100;
  static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

  /**
   * Get's user choice at login menu
   * 
   * @return input
   * @throws IOException
   */
  public static String getInputStartLoginMenu() throws IOException {
    String input = "";
    while (!input.contentEquals("0") && !input.contentEquals("1")) {
      System.out.println("1 - Login\n0 - Exit\nEnter Selection:");
      input = bufferedReader.readLine();
      if (!input.contentEquals("0") && !input.contentEquals("1")) {
        System.out.println("Please select one of the given options");
      }
    }
    return input;
  }

  /**
   * Gets user input for any entry of UserId
   * 
   * @return
   * @throws SQLException
   */
  public static int getInputUserId(DatabaseDriverAndroid mydb) throws SQLException {
    boolean valid = false;
    int userId = -1;
    boolean error = true;
    do {
      try {
        System.out.println("Enter User Id:");
        userId = Integer.parseInt(bufferedReader.readLine());
        error = false;
      } catch (Exception e) {
        System.out.println("You did not enter an integer, please enter an integer value.");
        continue;
      }
      valid = DatabasedValidHelper.checkUserId(userId, mydb);
      if (!valid) {
        System.out.println("You did not enter a valid ID, please enter a valid ID.");
      }
    } while (error || !valid);
    return userId;
  }

  /**
   * Gets user input for any entry of user password
   * 
   * @return
   * @throws IOException
   */
  public static String getInputUserPassword() throws IOException {
    System.out.println("Enter Password:");
    String userPassword = bufferedReader.readLine();
    while (userPassword.isEmpty()) {
      System.out.println("Enter Password (Required):");
      userPassword = bufferedReader.readLine();
    }
    return userPassword;
  }

  /**
   * Displays the menu and asks for the user's choice for any type of User
   * 
   * @param menuText The menu options
   * @param numberOfChoices The max number of choices
   * @return
   */
  public static String getInputGenericMenuChoice(String menuText, int numberOfChoices) {

    boolean valid = false;
    int choice = -1;
    boolean error = true;
    do {
      try {
        System.out.print(menuText);
        choice = Integer.parseInt(bufferedReader.readLine());
        error = false;
      } catch (Exception e) {
        System.out.println("Please enter a number");
        continue;
      }
      valid = choice <= numberOfChoices && choice >= 0;
      if (!valid) {
        System.out.println("Please enter one of the given choices");
      }
    } while (error || !valid);
    return Integer.toString(choice);

  }

  // MAKE THE BONUS FEATURE LUL

  /**
   * Gets the User's name at time of creation
   * 
   * @return
   * @throws IOException
   */
  public static String getInputUserName() throws IOException {
    String name = bufferedReader.readLine();
    while (name.length() >= characterLimit || name.length() == 0) {
      if (name.length() == 0) {
        System.out.println("*This is a required field");
      } else {
        System.out.println("*Name exceeds character Limit");
      } 
      name = bufferedReader.readLine();
    }
    return name;
  }

  /**
   * Get's the User's age at time of creation
   * 
   * @return
   */
  public static String getInputUserAge() {
    boolean valid = false;
    int age = -1;
    boolean error = true;

    do {
      try {
        age = Integer.parseInt(bufferedReader.readLine());
        error = false;
      } catch (Exception e) {
        System.out.println("Please enter a number");
        continue;
      }
      valid = age >= ageLowerLimit && age <= ageUpperLimit;
      if (!valid) {
        System.out.println("*Age outside of age requirement bounds");
      }
    } while (error || !valid);
    return Integer.toString(age);
  }

  /**
   * Gets the User's address at time of creation
   * 
   * @return
   * @throws IOException
   */
  public static String getInputUserAddress() throws IOException {
    String address = bufferedReader.readLine();
    while (address.length() >= characterLimit || address.length() == 0) {
      if (address.length() == 0) {
        System.out.println("*This is a required field");
      } else {
        System.out.println("*Address exceeds character Limit");
      }
      address = bufferedReader.readLine();
    }
    return address;
  }

  /** Gets the name of the item to be restocked
   * @return
   * @throws IOException 
   */
  public static String getUserInputRestockedItemName() throws IOException {
    String itemName = bufferedReader.readLine();
    while (!DatabasedValidHelper.checkItemName(itemName) || itemName.length() == 0) {
      if (itemName.length() == 0) {
        System.out.println("*This is a required field");
      } else {
        System.out.println("*The item you entered does not exist");
      }
      itemName = bufferedReader.readLine();
    }
    return itemName;
  }
  
  /** Gets the quantity to be restocked 
   * @return
   */
  public static int getUserInputRestockedItemQuantity() {
    boolean valid = false;
    int quantity = -1;
    boolean error = true;

    do {
      try {
        quantity = Integer.parseInt(bufferedReader.readLine());
        error = false;
      } catch (Exception e) {
        System.out.println("Please enter a number");
        continue;
      }
      valid = quantity >= 0 && quantity <= restockQuantityLimit;
      if (!valid) {
        System.out.println("*Quantity outside required bounds");
      }
    } while (error || !valid);
    return quantity;
  }
  
  /**
   * Prompts the user to enter an Account Id
   * 
   * @param userId
   * @return accountId the id of the account the user wants to use or 0 if they do
   *         not want to restore anymore
   * @throws SQLException
   * @throws UnauthorizedException
   */
  public static int getAccountIdFromUser(int userId, DatabaseDriverAndroid mydb) throws SQLException, UnauthorizedException {

    boolean valid = false;
    boolean error = true;
    int accountId = -1;
    do {
      try {
        System.out.println("Please enter an Account Id or enter 0 to quit:");
        accountId = Integer.parseInt(bufferedReader.readLine());
        error = false;
      } catch (Exception e) {
        System.out.println("*Account Ids are (numbers) only");
        continue;
      }
      if (accountId != 0) {
        valid = DatabasedValidHelper.checkActiveAccountId(accountId, userId, mydb);
      } else {
        valid = true;
      }
      if (!valid) {
        System.out.println(
            "You did not enter a valid id.\n *Please note all accounts that are checked out are no longer considered valid*");
      }
    } while (error || !valid);
    return accountId;

  }

}
