package ggc.core;

import java.io.Serializable;

public class AggregateProduct extends Product implements Serializable {

    public AggregateProduct(String productId, double price) {
        super(productId, price);
    }

    public String toString() {
        return "";
    }

    public Recipe getRecipe() {
        return null;
    }
}
