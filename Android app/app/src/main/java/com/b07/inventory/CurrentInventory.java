package com.b07.inventory;

import java.util.HashMap;

/**
 * Inventory of store.
 *
 */
public class CurrentInventory implements Inventory {

  HashMap<Item, Integer> ItemMap;
  int totalItems;

  /**
   * Constructor that creates current inventory.
   * 
   * @param ItemMap
   */
  public CurrentInventory(HashMap<Item, Integer> ItemMap) {
    setItemMap(ItemMap);
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    return ItemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.ItemMap = itemMap;
  }

  @Override
  public void updateMap(Item item, Integer value) {

  }

  @Override
  public int getTotalItems() {
    return totalItems;
  }

  @Override
  public void setTotalItems(int total) {
    this.totalItems = total;
  }

}
