package com.b07.store;

import java.util.ArrayList;

/**
 * This class keeps logs of different types of sales.
 *
 */
public class SalesLog {

  ArrayList<Sale> allSales = new ArrayList<Sale>();

  /**
   * This constructor creates a log of sales.
   * 
   * @param allSales
   */
  public SalesLog(ArrayList<Sale> allSales) {
    this.allSales = allSales;
  }

  /**
   * Returns a list of all sales.
   * 
   * @return allSales
   */
  public ArrayList<Sale> getallSales() {
    return allSales;
  }
}

