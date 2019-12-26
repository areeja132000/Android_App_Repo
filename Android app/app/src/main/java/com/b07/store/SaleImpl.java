package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

/**
 * This class allows for instantiation of a Sale object.
 *
 */
public class SaleImpl implements Sale {

    int id;
    int saleId;
    int userId;
    int quantity;
    User user;
    BigDecimal totalPrice;
    HashMap<Item, Integer> itemMap;

    /**
     * This constructor created a sale that has an ID, user, and totalPrice associated with it.
     *
     * @param id
     * @param user
     * @param totalPrice
     */
    public SaleImpl(int id, User user, BigDecimal totalPrice) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    /**
     * his constructor created a sale that has an ID, user ID, and quantity of item sold associated
     * with it.
     *
     * @param saleId
     * @param userId
     * @param quantity
     */
    public SaleImpl(int saleId, int userId, int quantity) {
        this.saleId = saleId;
        this.userId = userId;
        this.quantity = quantity;
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
}
