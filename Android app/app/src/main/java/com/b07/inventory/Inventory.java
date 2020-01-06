package com.b07.inventory;

import java.io.Serializable;
import java.util.HashMap;

public interface Inventory extends Serializable {

  /**
   * Returns the item map from item to quantity.
   * 
   * @return itemMap
   */
  public HashMap<Item, Integer> getItemMap();

  /**
   * Set the item map from item to quantity.
   * 
   * @param itemMap
   */
  public void setItemMap(HashMap<Item, Integer> itemMap);

  /**
   * Updates the map with a new item and value.
   * 
   * @param item, value
   */
  public boolean updateMap(Item item, Integer value);

  /**
   * Returns total amount of items.
   * 
   * @return totalItems
   */
  public int getTotalItems();

  /**
   * Sets total amount of items.
   * 
   * @param total
   */
  public void setTotalItems(int total);
}
