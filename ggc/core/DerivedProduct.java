package ggc.core;

import java.io.Serializable;

public class DerivedProduct extends Product implements Serializable {
    private Recipe _recipe;
    private double _alpha;

    public DerivedProduct(String productId, double price, Recipe recipe, double alpha) {
        super(productId, price);
        _recipe = recipe;
        _alpha = alpha;
    }

    public Recipe getRecipe() {
        return _recipe;
    }

    public double getAlpha() {
        return _alpha;
    }

    /**
     * public showProduct - Returns a string with all product attributes, including
     * the attributes of the recipe.
     * 
     * @return {type} String
     */

    @Override
    public String showProduct() {
        String display = "" + getAlpha() + "|";
        int i = 0;

        Recipe recipe = getRecipe();

        for (i = 0; i < recipe.getComponents().size(); i++) {
            display += recipe.getComponents().get(i).getProductId() + "-"
                    + recipe.getComponents().get(i).getQuantity() + "#";
        }

        return display;

    }
}
