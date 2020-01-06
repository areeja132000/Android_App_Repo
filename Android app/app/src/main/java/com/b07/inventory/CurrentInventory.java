package com.b07.inventory;

import java.util.HashMap;
import java.util.Map;
import static java.lang.Math.abs;

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
  public boolean updateMap(Item item, Integer value) {
    boolean success = false;
    if (!this.ItemMap.containsKey(item)) {
      this.ItemMap.put(item, value);
      return true;
    } else {
      for (Map.Entry<Item, Integer> mapElement : this.ItemMap.entrySet()) {
        Item currentItem = mapElement.getKey();
        if (item == currentItem) {
          int quantity = (int) mapElement.getValue();
          if (value < 0 && abs(value) < quantity) {
            this.ItemMap.replace(item, value + quantity);
            return true;
          } else if (value >= 0) {
            this.ItemMap.replace(item, value + quantity);
            return true;
          }
        }
      }
    }
    return success;
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
