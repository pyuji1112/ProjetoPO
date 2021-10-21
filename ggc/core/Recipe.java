package ggc.core;

public class Recipe {
    private String _componentsId;
    private int _quantity;
    private int _componentsPrice;

    public Recipe (String componentsId, int quantity, int componentsPrice) {
        _componentsId = componentsId;
        _quantity = quantity;
        _componentsPrice = componentsPrice;
    }

    public String getComponentsId() {
        return _componentsId;
    }

    public int getQuantity() {
        return _quantity;
    }

    public int getComponentsPrice() {
        return _componentsPrice;
    }
}
