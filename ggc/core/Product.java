package ggc.core;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private String _productId;
    private double _price;

    /**
     * @param {type} String productId
     */
    public Product(String productId, double price) {
        _productId = productId;
        _price = price;
    }

    public Product(String productId) {  //Construtor dos componentes
        _productId = productId;
    }

    public String getProductId() {
        return _productId;
    }

    public double getPrice() {
        return _price;
    }

    public abstract Recipe getRecipe();

}
