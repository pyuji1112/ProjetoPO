package ggc.core;

public abstract class Product {
    private String _productId;
    private double _price;

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
