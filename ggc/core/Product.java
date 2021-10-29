package ggc.core;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private String _productId;
    private double _price;


    /**
     * public Product - description
     *
     * @param  {type} String productId description
     * @return {type}                  description
     */
    public Product(String productId) {
        _productId = productId;
    }

    public String getProductId() {
        return _productId;
    }

    public double getPrice() {
      return _price;
    }
    
    public abstract String showProduct();

}
