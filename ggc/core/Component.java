package ggc.core;

import java.io.Serializable;

public class Component extends Product implements Serializable {
    private int _quantity;

    public Component(String productId, double price, int quantity) {
        super(productId, price);
        _quantity = quantity;
    }

    public Component(String productId, int quantity) {
        super(productId);
        _quantity = quantity;
    }

    public int getQuantity() {
        return _quantity;
    }

    @Override
    public Recipe getRecipe() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String showProduct() {
        // TODO Auto-generated method stub
        return null;
    }
}
