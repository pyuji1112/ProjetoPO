package ggc.core;

import java.io.Serializable;

public class DerivedProduct extends Product implements Serializable {
    private Recipe _recipe;
    private double _aggravation;

    public DerivedProduct(String productId) {
        super(productId);
    }

    public Recipe getRecipe() {
        return _recipe;
    }

    public double getAggravation() {
        return _aggravation;
    }

    @Override
    public String showProduct() {
        String display = "" + getAggravation() + "|";
        int i = 0;

        Recipe recipe = getRecipe();

        for (i = 0; i < recipe.getComponents().size(); i++) {
            display += recipe.getComponents().get(i).getComponentId() + "-" +
                        recipe.getComponents().get(i).getQuantity() + "#";
        }

        return display;

    }
}
