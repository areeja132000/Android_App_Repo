package com.mockAmazonApp.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Allows object of type ItemImpl to be instantiated.
 *
 */
public class ItemImpl implements Item, Serializable {

  int id;
  String name;
  BigDecimal price;

  /**
   * Constructor creates item that has a name, ID, and price associated with it.
   * 
   * @param id
   * @param name
   * @param price
   */
  public ItemImpl(int id, String name, BigDecimal price) {
    setId(id);
    setName(name);
    setPrice(price);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
