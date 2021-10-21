package ggc.core;

public class DerivedProduct extends Product{
    private Recipe _recipe;

    public DerivedProduct(String productId) {
        super(productId);
    }

    public Recipe getRecipe() {
        return _recipe;
    }
}