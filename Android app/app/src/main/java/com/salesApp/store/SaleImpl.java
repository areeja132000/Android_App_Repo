package com.salesApp.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import com.salesApp.inventory.Item;
import com.salesApp.users.User;

/**
 * This class allows for instantiation of a Sale object.
 *
 */
public class SaleImpl implements Sale, Serializable {

    int saleId;
    int itemId;
    int quantity;
    User user;
    BigDecimal totalPrice;
    HashMap<Item, Integer> itemMap;

    /**
     * This constructor created a sale that has an ID, user, and totalPrice associated with it.
     *
     * @param saleId
     * @param user
     * @param totalPrice
     */
    public SaleImpl(int saleId, User user, BigDecimal totalPrice) {
        this.saleId = saleId;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    /**
     * his constructor created a sale that has an ID, item ID, and quantity of item sold associated
     * with it.
     *
     * @param saleId
     * @param itemId
     * @param quantity
     */
    public SaleImpl(int saleId, int itemId, int quantity) {
        this.saleId = saleId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return saleId;
    }

    @Override
    public void setId(int id) {
        this.saleId = id;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void setTotalPrice(BigDecimal price) {
        this.totalPrice = price;
    }

    @Override
    public HashMap<Item, Integer> getItemMap() {
        return itemMap;
    }

    @Override
    public void setItemMap(HashMap<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    @Override
    public int getItemId() { return itemId; }

    @Override
    public int getQuantity () { return quantity; }
}
