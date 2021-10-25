package ggc.core;

import java.util.List;
import java.util.ArrayList;

public class DerivedProduct extends Product{
    private Recipe _recipe;
    private double _aggravation;

    public DerivedProduct(String productId) {
        super(productId);
    }

    public Recipe getRecipe() {
        return _recipe;
    }

    public double getAggravation(){
        return _aggravation;
    }

    @Override
    public String showProduct(){
        String display = "";
        int i = 0;

        Recipe recipe = this.getRecipe();

        for (i = 0; i < recipe.getComponentsPrice().size(); i++) {
            display += recipe.getComponentsId().get(i) + "-" + recipe.getComponentsPrice().get(i) + "#";
        }

        return display;



    }
}