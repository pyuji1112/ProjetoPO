package ggc.core;

import java.io.Serializable;

public class SimpleProduct extends Product implements Serializable {

    public SimpleProduct(String productId, double price) {
        super(productId, price);
    }

    public String toString() {
        return "";
    }

    public Recipe getRecipe() {
        return null;
    }
}
