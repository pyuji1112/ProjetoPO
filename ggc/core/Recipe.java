package ggc.core;

import java.util.List;
import java.util.ArrayList;


public class Recipe {
    private List<String> _componentsId;
    private int _quantity;
    private List<Double> _componentsPrice;

    public Recipe (int quantity) {
        _componentsId = new ArrayList<>();
        _quantity = quantity;
        _componentsPrice = new ArrayList<>();
    }

    public List<String> getComponentsId() {
        return _componentsId;
    }

    public int getQuantity() {
        return _quantity;
    }

    public List<Double> getComponentsPrice() {
        return _componentsPrice;
    }
}
