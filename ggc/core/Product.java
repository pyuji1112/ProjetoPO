package ggc.core;

public abstract class Product {
    private String _productId;

    public Product(String productId) {
        _productId = productId;
    }

    public String getProductId() {
        return _productId;
    }

    public abstract String showProduct();

}
