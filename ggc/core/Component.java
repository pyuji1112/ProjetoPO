package ggc.core;

import java.io.Serializable;

public class Component implements Serializable {
    private int _price;
    private int _quantity;
    private String _id;

    public Component(int price, int quantity, String id) {
        _price = price;
        _quantity = quantity;
        _id = id;
    }

    public int getprice() {
        return _price;
    }

    public int getQuantity() {
        return _quantity;
    }

    public String getComponentId() {
        return _id;
    }
}
