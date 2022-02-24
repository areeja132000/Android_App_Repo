package com.salesApp.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Item extends Serializable {

  /**
   * Returns the items Id.
   * 
   * @return id
   */
  public int getId();

  /**
   * Set the items Id.
   * 
   * @param id
   */
  public void setId(int id);

  /**
   * Return the items name.
   * 
   * @return name
   */
  public String getName();

  /**
   * Set the items name.
   * 
   * @param name
   */
  public void setName(String name);

  /**
   * Set the items price.
   * 
   * @return price
   */
  public BigDecimal getPrice();

  /**
   * Set the items price.
   * 
   * @return id
   */
  public void setPrice(BigDecimal price);

}
