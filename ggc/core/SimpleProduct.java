package ggc.core;

import java.io.Serializable;

public class SimpleProduct extends Product implements Serializable {

    public SimpleProduct(String productId) {
        super(productId);
    }

    public String showProduct() {
        return "";
    }
}
