package com.b07.store;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Roles;
import com.example.teambriancanweswitchourname.DatabaseDriverAndroid;

/**
 * This class sets up the database during the first run.
 *
 */
public class StoreInitializer {

  /** Fully initializes the starting users and database. Should only be called if no database currently exists
   * @throws DatabaseInsertException
   * @throws SQLException
   * @throws NumberFormatException
   * @throws IOException
   */
  public static void fullInitialize(DatabaseDriverAndroid mydb) throws DatabaseInsertException, SQLException, NumberFormatException, IOException {
    addRoleDatabase(mydb);
    addItemDatabase(mydb);
  }
  
  /**
   * Sets up the required roles in database at the initial run.
   * 
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static void addRoleDatabase(DatabaseDriverAndroid mydb) throws DatabaseInsertException, SQLException {
    for (Roles role : Roles.values()) {
      mydb.insertRole(role.name());
    }
  }

  /**
   * Sets up required items (that will be sold in the store) in database at the initial run.
   * 
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static void addItemDatabase(DatabaseDriverAndroid mydb) throws DatabaseInsertException, SQLException {
    int fishId = Math.toIntExact(mydb.insertItem("FISHING_ROD", new BigDecimal("50.00")));
    int hockeyId = Math.toIntExact(mydb.insertItem("HOCKEY_STICK", new BigDecimal("60.00")));
    int proteinId = Math.toIntExact(mydb.insertItem("PROTEIN_BAR", new BigDecimal("5.00")));
    int runningId = Math.toIntExact(mydb.insertItem("RUNNING_SHOES", new BigDecimal("80.00")));
    int skatesId = Math.toIntExact(mydb.insertItem("SKATES", new BigDecimal("75.00")));
    mydb.insertInventory(fishId, 0);
    mydb.insertInventory(hockeyId, 0);
    mydb.insertInventory(proteinId, 0);
    mydb.insertInventory(runningId, 0);
    mydb.insertInventory(skatesId, 0);
  }

  /**
   * Sets up the first users in the database at the initial run.
   * 
   * @throws NumberFormatException
   * @throws IOException
   * @throws DatabaseInsertException
   * @throws SQLException
   */
  public static void initializeStartingUsers(DatabaseDriverAndroid mydb)
      throws NumberFormatException, IOException, DatabaseInsertException, SQLException {
    CreateUser.insertUser("ADMIN", mydb);
    CreateUser.insertUser("EMPLOYEE", mydb);
  }

}
